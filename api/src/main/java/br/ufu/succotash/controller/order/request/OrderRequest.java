package br.ufu.succotash.controller.order.request;

import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public record OrderRequest(

        @NotNull
        Order order,

        @NotNull
        List<ItemQuantity> items

) {

    public List<OrderItem> toModel() {
        List<OrderItem> orderItems = new ArrayList<>();
        for (ItemQuantity item: items) {
            orderItems.add(new OrderItem(order, item.item(), item.quantity()));
        }
        return orderItems;
    }

}