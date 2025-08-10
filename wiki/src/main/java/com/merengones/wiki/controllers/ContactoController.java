package com.merengones.wiki.controllers;

import com.merengones.wiki.models.Contacto;
import com.merengones.wiki.services.ContactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/contacto")
public class ContactoController {

    private final ContactoService contactoService;

    @GetMapping
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("contacto")) {
            model.addAttribute("contacto", new Contacto());
        }
        return "contacto";
    }

    @PostMapping
    public String procesarFormulario(@Valid @ModelAttribute("contacto") Contacto contacto,
                                     BindingResult br,
                                     RedirectAttributes ra) {

        if (br.hasErrors()) {
            // Volver al formulario con errores
            return "contacto";
        }

        contactoService.guardar(contacto);
        ra.addFlashAttribute("ok", "¡Mensaje enviado!");
        return "redirect:/contacto";
    }
}
