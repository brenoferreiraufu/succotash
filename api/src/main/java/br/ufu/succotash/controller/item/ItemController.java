package br.ufu.succotash.controller.item;


import br.ufu.succotash.controller.item.response.ItemResponse;
import br.ufu.succotash.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findItemByRestaurant() {
        return ResponseEntity.ok(itemService.findItemByRestaurant().stream().map(ItemResponse::build).collect(Collectors.toList()));
    }


}
