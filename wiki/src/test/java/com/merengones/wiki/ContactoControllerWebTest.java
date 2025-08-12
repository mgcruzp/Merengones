package com.merengones.wiki;

import com.merengones.wiki.controllers.ContactoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactoController.class)
public class ContactoControllerWebTest {

    @Autowired
    private MockMvc mvc;

    private static final String MODEL = "contacto"; // cambia si tu modelo tiene otro nombre

    @Test
    void get_contacto_ok() throws Exception {
        mvc.perform(get("/contacto"))
           .andExpect(status().isOk())
           .andExpect(view().name("contacto"));
    }

    @Test
    void post_rechaza_vacios() throws Exception {
        mvc.perform(post("/contacto")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
           .andExpect(status().isOk())
           .andExpect(view().name("contacto"))
           .andExpect(model().attributeHasFieldErrors(
                   MODEL, "nombres", "apellidos", "correo", "semestre"));
    }

    @Test
    void post_rechaza_email_invalido() throws Exception {
        mvc.perform(post("/contacto")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nombres","Sebas")
                .param("apellidos","Vargas")
                .param("correo","abc")
                .param("semestre","7")
                .param("descripcion","hola"))
           .andExpect(status().isOk())
           .andExpect(view().name("contacto"))
           .andExpect(model().attributeHasFieldErrors(MODEL,"correo"));
    }

    @Test
    void post_rechaza_semestre_fuera() throws Exception {
        mvc.perform(post("/contacto")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nombres","Sebas")
                .param("apellidos","Vargas")
                .param("correo","sebas@mail.com")
                .param("semestre","17"))
           .andExpect(status().isOk())
           .andExpect(view().name("contacto"))
           .andExpect(model().attributeHasFieldErrors(MODEL,"semestre"));
    }

    @Test
    void post_acepta_valido() throws Exception {
        mvc.perform(post("/contacto")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("nombres","Sebas")
                .param("apellidos","Vargas")
                .param("correo","Juan1239252@outlook.com")
                .param("semestre","7")
                .param("descripcion","Holaaa :)"))
           .andExpect(status().isOk()); // cambia a is3xxRedirection() si tu flujo redirige
    }
}
