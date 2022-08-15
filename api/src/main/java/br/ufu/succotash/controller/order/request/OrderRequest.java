package br.ufu.succotash.controller.order.request;

import br.ufu.succotash.domain.model.Item;
import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public record OrderRequest(

        @NotNull
        Order order,

        @NotNull
        List<Item> items,

        @NotNull
        List<Integer> quantities

) {

    public List<OrderItem> toModel() {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Item item: items) {
            orderItems.add(new OrderItem(order, item, quantities.get(items.indexOf(item))));
        }
        return orderItems;
    }

}