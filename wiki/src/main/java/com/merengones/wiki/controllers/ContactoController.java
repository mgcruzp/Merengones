package com.merengones.wiki.controllers;

import com.merengones.wiki.models.Contacto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactoController {

    @GetMapping("/contacto")
    public String mostrarFormulario(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "contacto";
    }

    @PostMapping("/contacto")
    public String procesarFormulario(@ModelAttribute Contacto contacto, Model model) {
        System.out.println("");
        System.out.println("---- Formulario Recibido ----");
        System.out.println("Nombre: " + contacto.getNombre());
        System.out.println("Apellido: " + contacto.getApellido());
        System.out.println("Correo: " + contacto.getCorreo());
        System.out.println("Semestre: " + contacto.getSemestre());
        System.out.println("Descripción: " + contacto.getDescripcion());
        System.out.println("-----------------------------");

        model.addAttribute("mensaje", "Formulario enviado con éxito.");
        model.addAttribute("contacto", contacto);
        return "contacto";
    }

    

}
