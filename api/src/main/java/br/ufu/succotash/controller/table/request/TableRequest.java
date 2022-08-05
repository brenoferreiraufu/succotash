package br.ufu.succotash.controller.table.request;

import br.ufu.succotash.domain.model.Item;
import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.Table;
import br.ufu.succotash.domain.model.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public record TableRequest(

        @NotNull
        User user,

        @NotNull
        Table table,

        @NotNull
        List<Item> items) {

    public Order toModel() {
        return new Order(user, table, items);
    }

}
