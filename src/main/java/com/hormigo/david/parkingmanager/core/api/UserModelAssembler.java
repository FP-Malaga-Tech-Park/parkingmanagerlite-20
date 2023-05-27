package com.hormigo.david.parkingmanager.core.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hormigo.david.parkingmanager.core.exceptions.UserDoesNotExistsException;
import com.hormigo.david.parkingmanager.user.adapter.UserRestController;
import com.hormigo.david.parkingmanager.user.domain.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User,EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        try {
            return EntityModel.of(user,
                    linkTo(methodOn(UserRestController.class).getById(user.getId())).withSelfRel(),
                    linkTo(methodOn(UserRestController.class).all()).withRel("users"));
        } catch (UserDoesNotExistsException e) {
            return null;
        }
    }
    
}
