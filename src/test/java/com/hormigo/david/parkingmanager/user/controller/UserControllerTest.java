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
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;
import com.hormigo.david.parkingmanager.user.domain.Role;
import com.hormigo.david.parkingmanager.user.domain.User;
import com.hormigo.david.parkingmanager.user.domain.UserDao;
import com.hormigo.david.parkingmanager.user.service.UserService;
import com.hormigo.david.parkingmanager.user.service.UserServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void testPositive() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDao dao = new UserDao("da@correo.es", "David", "Hormigo", "Ramírez", Role.PROFESSOR);
        String json = mapper.writeValueAsString(dao);
        when(this.userService.register(any(UserDao.class))).thenReturn(new User("da@correo.es","David","Hormigo","Ramírez",Role.PROFESSOR));
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().isCreated());
    }

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
        when(userService.getUser(3)).thenReturn(Optional.of(user));
        this.mockMvc.perform(delete("/api/users/3"))
                    .andDo(print())
                    .andExpect(status().is(204));

    }

    @Test
    public void createUserEmailEmpty() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("","Jorge", "Reina", "Romero", Role.PROFESSOR);        
        String json = mapper.writeValueAsString(user);
        when(userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("El correo es obligatorio\n"));
    }

    @Test
    public void createRepeatedEmailUser() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("jorge@correo.com","Jorge", "Reina", "Romero", Role.PROFESSOR);        
        String json = mapper.writeValueAsString(user);
        when(userService.register(any(UserDao.class))).thenThrow(UserExistsException.class);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(406))
                    .andExpect(content().string("Ya existe un usuario con el correo"));
    }               

    @Test
    public void createUserNameEmpty() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("jorge@correo.com","", "Reina", "Romero", Role.PROFESSOR);        
        String json = mapper.writeValueAsString(user);
        when(this.userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("El nombre es obligatorio\n"));
    }

    @Test
    public void createUserLastNameEmpty() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        User user = new User("jorge@correo.com","Jorge", "", "Romero", Role.PROFESSOR);        
        String json = mapper.writeValueAsString(user);
        when(this.userService.register(any(UserDao.class))).thenReturn(null);
        this.mockMvc.perform(post("/api/users")
                    .contentType("application/json").content(json))
                    .andDo(print())
                    .andExpect(status().is(422))
                    .andExpect(content().string("El primer apellido es obligatorio\n"));
    }

    @Test
    public void testModifyUser() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> hashMap = new HashMap<>();
        User user = new User("jorge@correo.com","Jorge", "Reina", "Romero", Role.PROFESSOR);

        hashMap.put("name", "David");
        hashMap.put("lastName1", "Reina");
        hashMap.put("lastName2", "Romero");

        String json = mapper.writeValueAsString(user);
        String updateJson = mapper.writeValueAsString(hashMap);
        when(userService.updateUser(2,hashMap)).thenReturn(user);
        this.mockMvc.perform(patch("/api/users/2").content(updateJson))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(json));
    }

}
