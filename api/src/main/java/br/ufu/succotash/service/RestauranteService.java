package br.ufu.succotash.service;

import br.ufu.succotash.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestaurantRepository restaurantRepository;


}
