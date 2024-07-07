package com.zapateria.zapateria.rest;

import com.zapateria.zapateria.model.Orden;
import com.zapateria.zapateria.model.Usuario;
import com.zapateria.zapateria.service.IOrdenServices;
import com.zapateria.zapateria.service.IUsuarioServices;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioServices usuarioServices;

    @Autowired
    private IOrdenServices ordenServices;

    @GetMapping("/registro")
    public String registro() {
        return "usuario/registro";
    }

    @PostMapping("/guardarregistro")
    public String save(Usuario usuario) {
        logger.info("Usuario registrado: {}", usuario);
        usuario.setTipo("USUARIO");
        usuarioServices.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @PostMapping("/acceso")
    public String acceso(Usuario usuario, HttpSession session) {
        logger.info("Acceso: {}", usuario);
        Optional<Usuario> user = usuarioServices.findByEmail(usuario.getEmail());
        //logger.info("Usuario: {}", user.get());

        if (user.isPresent()) {
            session.setAttribute("usuario", user.get().getId());
            if(user.get().getTipo().equals("ADMIN")) {
                return "redirect:/admin";
            }else{
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/compras")
    public String historialCompras(Model model, HttpSession session) {
        model.addAttribute("sesion", session.getAttribute("usuario"));
        Usuario usuario = usuarioServices.findById(Integer.parseInt(session.getAttribute("usuario").toString())).get();
        List<Orden> ordenes = ordenServices.findByUsuario(usuario);
        model.addAttribute("ordenes", ordenes);
        return "usuario/historial_compras";
    }

    @GetMapping("detalle/{id}")
    public String detalle(@PathVariable Integer id, Model model, HttpSession session) {
        Optional<Orden> orden = ordenServices.findById(id);
        model.addAttribute("detalles", orden.get().getDetalle());
        model.addAttribute("sesion", session.getAttribute("usuario"));
        return "usuario/detalle_compra";
    }

    @GetMapping("/cerrarsesion")
    public String cerrarSesion(HttpSession session) {
        session.removeAttribute("usuario");
        return "redirect:/";
    }
}
