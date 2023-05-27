package com.hormigo.david.parkingmanager.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.service.UserService;
import com.hormigo.david.parkingmanager.user.service.UserServiceImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Optional;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;


    @Test
    public void testSingleUserRead() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("dhorram948@g.educaand.es","David","Hormigo","Ramírez",Role.PROFESSOR);
        String json = mapper.writeValueAsString(user);
        when(userService.getUser(2)).thenReturn(Optional.of(user));
        this.mockMvc.perform(get("/api/users/2"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));
                    

    }

    @Test
    public void testSomeUserRead() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new User("dhorram948@g.educaand.es","David  ","Hormigo","Ramírez",Role.PROFESSOR));
        listaUsuarios.add(new User("jorge@correo.com","Jorge", "Reina", "Romero", Role.PROFESSOR));
        String json = mapper.writeValueAsString(listaUsuarios);
        json = "{ \"_embedded\": {\"userList\":" + json + "}}";
        when(userService.getAll()).thenReturn(listaUsuarios);
        this.mockMvc.perform(get("/api/users"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().json(json));

    }

    @Test
    public void testUserDelete() throws Exception{

        User user = new User("jorge@correo.com","Jorge", "Reina", "Romero", Role.PROFESSOR);
        this.mockMvc.perform(get("/api/users"))
                    .andDo(print())
                    .andExpect(status().isNoContent());

    }

}
