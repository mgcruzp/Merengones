package com.merengones.wiki.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/")
    public String index() {
        return "index"; 
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

    @GetMapping("/documentacion")
    public String documentacion() {
        return "documentacion";
    }

    @GetMapping("/pruebas")
    public String pruebas() {
        return "pruebas";
    }
}
