package com.zapateria.zapateria.rest;

import com.zapateria.zapateria.model.DetalleOrden;
import com.zapateria.zapateria.model.Orden;
import com.zapateria.zapateria.model.Usuario;
import com.zapateria.zapateria.model.Zapato;
import com.zapateria.zapateria.service.IDetalleOrdenServices;
import com.zapateria.zapateria.service.IOrdenServices;
import com.zapateria.zapateria.service.IUsuarioServices;
import com.zapateria.zapateria.service.IZapatoServices;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IZapatoServices zapatoServices;

    @Autowired
    private IUsuarioServices usuarioServices;

    @Autowired
    private IOrdenServices ordenServices;

    @Autowired
    private IDetalleOrdenServices detalleOrdenServices;

    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    Orden orden = new Orden();

    @GetMapping
    public String home(Model model, HttpSession session) {
        logger.info("Sesión iniciada como: {}", session.getAttribute("usuario"));
        model.addAttribute("zapatos", zapatoServices.findAll());
        model.addAttribute("sesion", session.getAttribute("usuario"));
        return "usuario/home";
    }

    @GetMapping("zapatohome/{id}")
    public String zapatoHome(@PathVariable Integer id, Model model) {
        logger.info("Id zapato enviado como parámetro {}", id);
        Zapato zapato = new Zapato();
        Optional<Zapato> zapatoOptional = zapatoServices.get(id);
        zapato = zapatoOptional.get();
        model.addAttribute("zapato", zapato);
        return "usuario/zapatohome";
    }

    @PostMapping("/carrito")
    public String addCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Zapato zapato = new Zapato();
        double sumaTotal = 0;

        Optional<Zapato> optionalZapato = zapatoServices.get(id);
        logger.info("Producto añadido: {}", optionalZapato.get());
        logger.info("Cantidad: {}", cantidad);
        zapato = optionalZapato.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(zapato.getPrecio());
        detalleOrden.setNombre(zapato.getNombre());
        detalleOrden.setTotal(zapato.getPrecio() * cantidad);
        detalleOrden.setZapato(zapato);

        Integer idZapato = zapato.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getZapato().getId() == idZapato);

        if (!ingresado) {
            detalles.add(detalleOrden);
        }
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("carrito", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/delete/carrito/{id}")
    public String deleteZapatoCarrito(@PathVariable Integer id, Model model) {

        List<DetalleOrden> ordenesNuevo = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getZapato().getId() != id) {
                ordenesNuevo.add(detalleOrden);
            }
        }
        detalles = ordenesNuevo;

        double sumaTotal = 0;
        sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("carrito", detalles);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }

    @GetMapping("/getCarrito")
    public String getCarrito(Model model, HttpSession session) {
        model.addAttribute("carrito", detalles);
        model.addAttribute("orden", orden);

        model.addAttribute("sesion", session.getAttribute("usuario"));
        return "usuario/carrito";
    }

    @GetMapping("/orden")
    public String orden(Model model, HttpSession session) {
        Usuario usuario = usuarioServices.findById(Integer.parseInt(session.getAttribute("usuario").toString())).get();
        model.addAttribute("carrito", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);
        return "usuario/listarorden";
    }

    @GetMapping("/saveOrden")
    public String saveOrden(HttpSession session) {
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenServices.generarNumeroOrden());
        Usuario usuario = usuarioServices.findById(Integer.parseInt(session.getAttribute("usuario").toString())).get();
        orden.setUsuario(usuario);
        ordenServices.save(orden);

        for (DetalleOrden dt : detalles) {
            dt.setOrden(orden);
            detalleOrdenServices.save(dt);
        }

        orden = new Orden();
        detalles.clear();

        return "redirect:/";
    }
}
