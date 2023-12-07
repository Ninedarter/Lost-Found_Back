package com.example.LostAndFoundApp.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "admin:create")
    void testPostEndpoint() throws Exception {
        mockMvc.perform(post("/api/v1/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("POST:: admin controller"));
    }

    @Test
    void testPutEndpoint() throws Exception {
        mockMvc.perform(put("/api/v1/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testDeleteEndpoint() throws Exception {
        mockMvc.perform(delete("/api/v1/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testHiddenPostEndpoint() throws Exception {
        mockMvc.perform(post("/api/v1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"key\":\"value\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testHiddenPutEndpoint() throws Exception {
        mockMvc.perform(put("/api/v1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"key\":\"value\"}"))
                .andExpect(status().isForbidden()); // Update if needed
    }

    @Test
    void testHiddenDeleteEndpoint() throws Exception {
        mockMvc.perform(delete("/api/v1/admin"))
                .andExpect(status().isForbidden()); // Update if needed
    }

    @Test
    @WithMockUser(authorities = "user:read")
    void testInvalidRoleAccess() throws Exception {
        mockMvc.perform(get("/api/v1/admin"))
                .andExpect(status().isForbidden());
    }


}
