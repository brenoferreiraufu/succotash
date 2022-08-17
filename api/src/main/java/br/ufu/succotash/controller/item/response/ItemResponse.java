package br.ufu.succotash.controller.item.response;

import br.ufu.succotash.domain.model.Item;

import java.math.BigDecimal;

public record ItemResponse(String id, String image, String name, String description, BigDecimal price) {

    public static ItemResponse build(Item item) {
        return new ItemResponse(item.getId(), item.getImage(), item.getName(), item.getDescription(), item.getPrice());
    }
}
