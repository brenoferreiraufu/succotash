package br.ufu.succotash.controller.order.response;

import br.ufu.succotash.controller.item.response.ItemResponse;
import br.ufu.succotash.domain.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public record OrderItemResponse(Integer quantity, ItemResponse item) {

    public static List<OrderItemResponse> buildList(List<OrderItem> items) {
        return items.stream().map(OrderItemResponse::build)
                .collect(Collectors.toList());
    }

    private static OrderItemResponse build(OrderItem item) {
        return new OrderItemResponse(item.getQuantity(), ItemResponse.build(item.getItem()));
    }
}
