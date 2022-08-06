package br.ufu.succotash.controller.order.request;

import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.Table;
import br.ufu.succotash.domain.model.User;

import javax.validation.constraints.NotNull;

public record OrderRequest(

        @NotNull
        User user,

        @NotNull
        Table table

       ) {

    public Order toModel() {
        return new Order(user, table);
    }

}
