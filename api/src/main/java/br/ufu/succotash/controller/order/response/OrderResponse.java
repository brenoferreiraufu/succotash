package br.ufu.succotash.controller.order.response;

import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;

import java.util.List;

public record OrderResponse(String id, String userId, String userName, String tableId, String tableName, String restaurantName, OrderStatus status, List<OrderItemResponse> items) {

    public static OrderResponse build(Order order, List<OrderItem> items) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getFullName(),
                order.getTable().getId(),
                order.getTable().getName(),
                order.getTable().getRestaurant().getName(),
                order.getStatus(),
                OrderItemResponse.buildList(items));
    }

}
