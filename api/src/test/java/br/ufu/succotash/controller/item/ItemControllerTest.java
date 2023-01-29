package br.ufu.succotash.controller.item;

import br.ufu.succotash.controller.item.response.ItemResponse;
import br.ufu.succotash.domain.model.Item;
import br.ufu.succotash.domain.model.Restaurant;
import br.ufu.succotash.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Test
    public void findItemByRestaurant_shouldReturnOkWithItems() {
        Item item1 = new Item("1", "image1.png", "Item 1", "Description 1", new BigDecimal(10), new Restaurant());
        Item item2 = new Item("2", "image2.png", "Item 2", "Description 2", new BigDecimal(15), new Restaurant());
        when(itemService.findItemByRestaurant()).thenReturn(Arrays.asList(item1, item2));

        ResponseEntity<List<ItemResponse>> response = itemController.findItemByRestaurant();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(ItemResponse.build(item1), ItemResponse.build(item2)), response.getBody());
    }

}
