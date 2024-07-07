package com.zapateria.zapateria.rest;

import com.zapateria.zapateria.model.Zapato;
import com.zapateria.zapateria.service.IZapatoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IZapatoServices zapatoServices;

    @GetMapping("")
    public String home(Model model) {

        List<Zapato> zapatos = zapatoServices.findAll();
        model.addAttribute("zapatos", zapatos);
        return "admin/home";
    }


}
