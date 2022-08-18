package br.ufu.succotash.controller.order.request;

import br.ufu.succotash.domain.model.Item;

import javax.validation.constraints.NotNull;

public record ItemQuantity(

        String orderItemId,

        Item item,

        @NotNull
        int quantity

) {
}