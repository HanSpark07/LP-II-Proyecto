package com.curso.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Zapato;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IDetalleOrdenService;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.ZapatoService;

@Controller
@RequestMapping("/")
public class HomeController {

	private final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private |ZapatoService zapatoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@Autowired
	private IOrdenService ordenService;
	
	@Autowired
	private IDetalleOrdenService detalleOrdenService;

	// para almacenar los detalles de la orden
	List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

	// datos de la orden
	Orden orden = new Orden();

	@GetMapping("")
	public String home(Model model, HttpSession session) {
		
		log.info("Sesion del usuario: {}", session.getAttribute("idusuario"));
		
		model.addAttribute("zapatos", zapatoService.findAll());
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));

		return "usuario/home";
	}

	@GetMapping("zapatohome/{id}")
	public String zapatoHome(@PathVariable Integer id, Model model) {
		log.info("Id zapato enviado como parámetro {}", id);
		Zapato zapato = new Zapato();
		Optional<Zapato> zapatoOptional = zapatoService.get(id);
		zapato = zapatoOptional.get();

		model.addAttribute("zapato", zapato);

		return "usuario/zapatohome";
	}

	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
		DetalleOrden detalleOrden = new DetalleOrden();
		Zapato zapato = new Zapato();
		double sumaTotal = 0;

		Optional<Zapato> optionalZapato = ZapatoService.get(id);
		log.info("Zapato añadido: {}", optionalZapato.get());
		log.info("Cantidad: {}", cantidad);
		zapato = optionalZapato.get();

		detalleOrden.setCantidad(cantidad);
		detalleOrden.setPrecio(zapato.getPrecio());
		detalleOrden.setNombre(zapato.getNombre());
		detalleOrden.setTotal(zapato.getPrecio() * cantidad);
		detalleOrden.setZapato(zapato);
		
		//validar que le zapato no se añada 2 veces
		Integer idZapato=zapato.getId();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getZapato().getId()==idZapato);
		
		if (!ingresado) {
			detalles.add(detalleOrden);
		}
		
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);

		return "usuario/carrito";
	}

	// quitar un zapato del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteZapatoCart(@PathVariable Integer id, Model model) {

		// lista nueva de zapato
		List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

		for (DetalleOrden detalleOrden : detalles) {
			if (detalleOrden.getZapato().getId() != id) {
				ordenesNueva.add(detalleOrden);
			}
		}

		// poner la nueva lista con los restantes
		detalles = ordenesNueva;

		double sumaTotal = 0;
		sumaTotal = detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

		orden.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);

		return "usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model model, HttpSession session) {
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		
		//sesion
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "/usuario/carrito";
	}
	
	@GetMapping("/order")
	public String order(Model model, HttpSession session) {
		
		Usuario usuario =usuarioService.findById( Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		model.addAttribute("cart", detalles);
		model.addAttribute("orden", orden);
		model.addAttribute("usuario", usuario);
		
		return "usuario/resumenorden";
	}
	
	// guardar la orden
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session ) {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.generarNumeroOrden());
		
		//usuario
		Usuario usuario =usuarioService.findById( Integer.parseInt(session.getAttribute("idusuario").toString())  ).get();
		
		orden.setUsuario(usuario);
		ordenService.save(orden);
		
		//guardar detalles
		for (DetalleOrden dt:detalles) {
			dt.setOrden(orden);
			detalleOrdenService.save(dt);
		}
		
		///limpiar lista y orden
		orden = new Orden();
		detalles.clear();
		
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchZapat(@RequestParam String nombre, Model model) {
		log.info("Nombre del zapato: {}", nombre);
		List<Zapato> zapatos= zapatoService.findAll().stream().filter( p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		model.addAttribute("zapatos", zapatos);
		return "usuario/home";
	}

}
