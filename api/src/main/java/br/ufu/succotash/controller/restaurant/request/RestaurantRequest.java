package br.ufu.succotash.controller.restaurant.request;

import br.ufu.succotash.domain.model.*;

import javax.validation.constraints.NotBlank;
public record RestaurantRequest(

       @NotBlank
       String name
) {

    public Restaurant toModel() {
        return new Restaurant(name);
    }

}
