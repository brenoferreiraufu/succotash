package br.ufu.succotash.controller.restaurant.request;

import br.ufu.succotash.domain.model.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record RestaurantRequest(

       @NotBlank
       String name,

       @NotNull
       List<Table> tables
) {

    public Restaurant toModel() {
        return new Restaurant(name, tables);
    }

}
