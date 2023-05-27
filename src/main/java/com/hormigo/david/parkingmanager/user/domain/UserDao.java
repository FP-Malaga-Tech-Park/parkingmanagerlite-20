package com.hormigo.david.parkingmanager.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class UserDao {
    @Email(message = "El formato del corrreo no es aducado")
    @NotBlank(message = "El correo es obligatorio")
    private String email;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El primer apellido es obligatorio")
    private String lastName1;
    private String lastName2;
    private Role role;
    public UserDao(){
        this("","","","",null);
    }
    public UserDao(String email, String name, String lastName1, String lastName2, Role role) {
        this.email = email;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName1() {
        return lastName1;
    }
    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }
    public String getLastName2() {
        return lastName2;
    }
    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
