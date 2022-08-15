package br.ufu.succotash.controller.order.request;

import br.ufu.succotash.domain.model.Item;

import javax.validation.constraints.NotNull;

public record ItemQuantity(

        @NotNull
        Item item,

        @NotNull
        int quantity

) {
}