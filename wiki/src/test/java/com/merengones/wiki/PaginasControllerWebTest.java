package com.merengones.wiki;

import com.merengones.wiki.controllers.PaginasController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaginasController.class)
public class PaginasControllerWebTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void root_ok() throws Exception {
        mvc.perform(get("/"))
           .andExpect(status().isOk())
           .andExpect(view().name("index"));
    }

    @Test
    void equipo_ok() throws Exception {
        mvc.perform(get("/equipo"))
           .andExpect(status().isOk())
           .andExpect(view().name("equipo"));
    }

    @Test
    void desarrollo_ok() throws Exception {
        mvc.perform(get("/desarrollo"))
           .andExpect(status().isOk())
           .andExpect(view().name("desarrollo"));
    }

    @Test
    void documentacion_ok() throws Exception {
        mvc.perform(get("/documentacion"))
           .andExpect(status().isOk())
           .andExpect(view().name("documentacion"));
    }

    @Test
    void pruebas_ok() throws Exception {
        mvc.perform(get("/pruebas"))
           .andExpect(status().isOk())
           .andExpect(view().name("pruebas"));
    }
}
