package br.ufu.succotash.controller.table;

import br.ufu.succotash.controller.table.response.TableResponse;
import br.ufu.succotash.controller.user.response.UserResponse;
import br.ufu.succotash.domain.model.Table;
import br.ufu.succotash.service.TableService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/table")
@RequiredArgsConstructor
@Transactional
public class TableController {

    private final TableService tableService;

/*
    @PostMapping
    public ResponseEntity<?> newOrder(@Valid @RequestBody OrderRequest order) {
        String orderId = orderService.newOrder(order);
        URI location = URI.create("/api/v1/order/".concat(orderId));
        return ResponseEntity.ok().location(location).build();
    }
*/
    @GetMapping("")
    public ResponseEntity<List<TableResponse>> findTableByRestaurant() {
        return ResponseEntity.ok(tableService.findTableByRestaurant().stream().map(TableResponse::toTableResponse).collect(Collectors.toList()));
    }


}
