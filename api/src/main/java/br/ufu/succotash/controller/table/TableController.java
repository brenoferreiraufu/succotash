package br.ufu.succotash.controller.table;

import br.ufu.succotash.controller.order.response.OrderResponse;
import br.ufu.succotash.controller.table.response.TableResponse;
import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.service.OrderService;
import br.ufu.succotash.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/table")
@RequiredArgsConstructor
@Transactional
public class TableController {

    private final TableService tableService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<TableResponse>> findTableByRestaurant() {
        return ResponseEntity.ok(tableService.findTableByRestaurant().stream().map(TableResponse::toTableResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{tableId}/order")
    public ResponseEntity<OrderResponse> getTableLastOrder(@PathVariable String tableId) {
        var order = orderService.findLastOrderByTableIdAndStatus(OrderStatus.IN_PREPARATION, tableId);
        var items = orderService.findOrderItemsByOrder(order);
        return ResponseEntity.ok(OrderResponse.build(order, items));
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<TableResponse> findTableById(@PathVariable String tableId) {
        return ResponseEntity.ok(TableResponse.toTableResponse(tableService.findTableById(tableId)));
    }

}
