package com.zapataeria.zapeteria.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zapataeria.zapeteria.model.Zapato;
import com.zapataeria.zapeteria.model.Usuario;
import com.zapataeria.zapeteria.service.ZapatoService;
@Controller
@RequestMapping("/zapatos")
public class ZapatoController {
    private final Logger LOGGER = LoggerFactory.getLogger(ZapatoController.class);

    @Autowired
    private ZapatoService zapatoService;

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
    public String save(Zapato zapato) {
        LOGGER.info("Este es el objeto zapato {}", zapato);
        Usuario u= new Usuario(1, "", "", "", "","", "", "");
        zapato.setUsuario(u);
        zapatoService.save(zapato);
        return "redirect:/zapatos";
    }
}
