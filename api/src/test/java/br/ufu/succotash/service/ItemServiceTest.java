package br.ufu.succotash.service;

import br.ufu.succotash.controller.item.response.ItemResponse;
import br.ufu.succotash.domain.model.Item;
import br.ufu.succotash.domain.model.Restaurant;
import br.ufu.succotash.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void findItemByRestaurant_shouldReturnItems() {
        Item item1 = new Item("1", "image1.png", "Item 1", "Description 1", new BigDecimal(10), new Restaurant());
        Item item2 = new Item("2", "image2.png", "Item 2", "Description 2", new BigDecimal(15), new Restaurant());
        when(itemRepository.findByRestaurantName("Succotash")).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemService.findItemByRestaurant();

        verify(itemRepository).findByRestaurantName("Succotash");
        assertEquals(Arrays.asList(item1, item2), result);
    }
}
