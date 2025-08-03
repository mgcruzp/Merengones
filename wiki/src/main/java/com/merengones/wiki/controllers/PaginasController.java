package com.merengones.wiki.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/")
    public String index() {
        return "index"; // Busca index.html en /resources/templates/
    }

    @GetMapping("/equipo")
    public String equipo() {
        return "equipo";
    }

    /*@GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }*/

    @GetMapping("/desarrollo")
    public String desarrollo() {
        return "desarrollo";
    }

    @GetMapping("/pruebas")
    public String pruebas() {
        return "pruebas";
    }
}
