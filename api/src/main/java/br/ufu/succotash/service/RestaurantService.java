package br.ufu.succotash.service;

import br.ufu.succotash.controller.restaurant.request.RestaurantRequest;
import br.ufu.succotash.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public String newRestaurant(RestaurantRequest restaurant) {
       return restaurantRepository.save(restaurant.toModel()).getId();
    }

    //public Optional<User> findRestaurant(String id) {
    //    return null;
    ///}

}
