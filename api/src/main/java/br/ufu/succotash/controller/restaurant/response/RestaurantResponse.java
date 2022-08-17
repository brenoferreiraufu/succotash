package br.ufu.succotash.controller.restaurant.response;

import br.ufu.succotash.domain.model.Restaurant;

public record RestaurantResponse(String id, String name) {

    public static RestaurantResponse build(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName());
    }
}
