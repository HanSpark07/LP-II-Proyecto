package com.curso.ecommerce.controller;

import java.io.IOException;
import java.util.Optional;

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
import org.springframework.web.multipart.MultipartFile;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.model.Zapato;
import com.curso.ecommerce.service.ZapatoService;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.UploadFileService;

@Controller
@RequestMapping("/zapatos")
public class ZapatoController {

	private final Logger LOGGER = LoggerFactory.getLogger(ZapatoController.class);

	@Autowired
	private ZapatoService zapatoService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private UploadFileService upload;

	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("zapatos", zapatoService.findAll());
		return "zapatos/show";
	}

	@GetMapping("/create")
	public String create() {
		return "zapatos/create";
	}

	@PostMapping("/save")
	public String save(Zapato zapato, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
		LOGGER.info("Este es el objeto zapato {}", zapato);

		Usuario u = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		zapato.setUsuario(u);

		// imagen
		if (zapato.getId() == null) { // cuando se crea un zapato
			String nombreImagen = upload.saveImage(file);
			zapato.setImagen(nombreImagen);
		}

		zapatoService.save(zapato);
		return "redirect:/zapatos";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Zapato zapato = new Zapato();
		Optional<Zapato> optionalZapato = zapatoService.get(id);
		zapato = optionalZapato.get();

		LOGGER.info("Zapato buscado: {}", zapato);
		model.addAttribute("zapato", zapato);

		return "zapatos/edit";
	}

	@PostMapping("/update")
	public String update(Zapato zapato, @RequestParam("img") MultipartFile file) throws IOException {
		Zapato z = new Zapato();
		z = zapatoService.get(zapato.getId()).get();

		if (file.isEmpty()) { // editamos el zapato pero no cambiamos la imagen
			zapato.setImagen(z.getImagen());
		} else { // cuando se edita tbn la imagen
			// eliminar cuando no sea la imagen por defecto
			if (!z.getImagen().equals("default.jpg")) {
				upload.deleteImage(z.getImagen());
			}
			String nombreImagen = upload.saveImage(file);
			zapato.setImagen(nombreImagen);
		}
		zapato.setUsuario(z.getUsuario());
		zapatoService.update(zapato);
		return "redirect:/zapatos";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		Zapato z = new Zapato();
		z = zapatoService.get(id).get();

		// eliminar cuando no sea la imagen por defecto
		if (!z.getImagen().equals("default.jpg")) {
			upload.deleteImage(z.getImagen());
		}

		zapatoService.delete(id);
		return "redirect:/zapatos";
	}
}

