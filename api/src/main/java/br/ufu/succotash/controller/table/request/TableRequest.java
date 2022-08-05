package br.ufu.succotash.controller.table.request;

import br.ufu.succotash.domain.enumeration.TableStatus;
import br.ufu.succotash.domain.model.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record TableRequest(

        @NotBlank
        String name,

        @NotBlank
        String urlQrCode,

        @NotNull
        Restaurant restaurant,

        @NotNull
        TableStatus status
) {

    public Table toModel() {
        return new Table(name, urlQrCode, restaurant, status);
    }

}
