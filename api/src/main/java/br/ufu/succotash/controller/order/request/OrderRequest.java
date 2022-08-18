package br.ufu.succotash.controller.order.request;

import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public record OrderRequest(
        @NotNull
        List<ItemQuantity> items
) {
}