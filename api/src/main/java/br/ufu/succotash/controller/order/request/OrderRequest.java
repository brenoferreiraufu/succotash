package br.ufu.succotash.controller.order.request;

import br.ufu.succotash.domain.model.*;

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