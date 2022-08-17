package br.ufu.succotash.controller.order.response;

import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;

import java.util.List;

public record OrderResponse(String id, String userId, String tableId, OrderStatus status, List<OrderItemResponse> items) {

    public static OrderResponse build(Order order, List<OrderItem> items) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getTable().getId(),
                order.getStatus(),
                OrderItemResponse.buildList(items));
    }

}
