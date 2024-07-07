package com.zapateria.zapateria.rest;

import com.zapateria.zapateria.model.Usuario;
import com.zapateria.zapateria.model.Zapato;
import com.zapateria.zapateria.service.IUsuarioServices;
import com.zapateria.zapateria.service.IZapatoServices;
import com.zapateria.zapateria.service.SubirImagenServices;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/zapatos")
public class ZapatoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ZapatoController.class);

    @Autowired
    private IZapatoServices zapatoServices;

    @Autowired
    private SubirImagenServices upload;

    @Autowired
    private IUsuarioServices usuarioServices;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("zapatos", zapatoServices.findAll());
        return "zapatos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "zapatos/create";
    }

    @PostMapping("/save")
    public String save(Zapato zapato, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        LOGGER.info("Este es el objeto zapato {}", zapato);
        Usuario usuario = usuarioServices.findById(Integer.parseInt(session.getAttribute("usuario").toString())).get();
        zapato.setUsuario(usuario);

        //guardando la imageeen
        if (zapato.getId() == null) { //nuevo producto
            String nombreImagen = upload.saveImage(file);
            zapato.setImagen(nombreImagen);
        }
        else{

        }

        zapatoServices.save(zapato);
        return "redirect:/zapatos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Zapato zapato = new Zapato();
        Optional<Zapato> optionalZapato = zapatoServices.get(id);
        zapato = optionalZapato.get();
        LOGGER.info("Zapato buscado: {}", zapato);
        model.addAttribute("zapato", zapato);
        return "zapatos/edit";
    }

    @PostMapping("/update")
    public String update(Zapato zapato, @RequestParam("img") MultipartFile file) throws IOException {
        Zapato z=new Zapato();
        z=zapatoServices.get(zapato.getId()).get();
        if(file.isEmpty()){ //editando el producto pero no la imagen

            zapato.setImagen(z.getImagen());
        }
        else{ //cuando se edita todo en edit
            //elimina la imagen por defecto pe
            if(!z.getImagen().equals("default.jpg")){
                upload.deleteImage(z.getImagen());
            }
            String nombreImagen = upload.saveImage(file);
            zapato.setImagen(nombreImagen);
        }
        zapato.setUsuario(z.getUsuario());
        zapatoServices.update(zapato);
        return "redirect:/zapatos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Zapato zapato = new Zapato();
        zapato=zapatoServices.get(id).get();

        //elimina la imagen por defecto pe
        if(!zapato.getImagen().equals("default.jpg")){
            upload.deleteImage(zapato.getImagen());
        }
        zapatoServices.delete(id);
        return "redirect:/zapatos";
    }
}
