package br.ufu.succotash.service;

import br.ufu.succotash.domain.model.Item;
import br.ufu.succotash.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findItemByRestaurant() {
        return itemRepository.findByRestaurantName("Succotash");
    }
}
