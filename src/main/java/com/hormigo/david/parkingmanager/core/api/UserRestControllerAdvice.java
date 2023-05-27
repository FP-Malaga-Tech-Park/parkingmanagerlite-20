package com.hormigo.david.parkingmanager.core.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.core.exceptions.UserExistsException;


@ControllerAdvice
public class UserRestControllerAdvice {
    
    @ResponseBody
    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String userExistsHandler(UserExistsException e)
    {
        return "Ya existe un usuario con el correo";
    }

    @ResponseBody
    @ExceptionHandler(UserDoesNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userDoesNotExists(UserDoesNotExistsException e){
        return "No existe el usuario con el id";
    }
}
