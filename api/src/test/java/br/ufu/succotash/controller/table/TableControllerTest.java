package br.ufu.succotash.controller.table;

import br.ufu.succotash.controller.order.response.OrderResponse;
import br.ufu.succotash.controller.table.response.TableResponse;
import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.enumeration.TableStatus;
import br.ufu.succotash.domain.model.*;
import br.ufu.succotash.service.OrderService;
import br.ufu.succotash.service.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TableControllerTest {

    @Mock
    private TableService tableService;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private TableController tableController;

    @Test
    public void findTableByRestaurant_shouldReturnOkWithTables() {
        Table table1 = new Table("1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Table table2 = new Table("2", "Table 2", new Restaurant(), TableStatus.PAYED);
        when(tableService.findTableByRestaurant()).thenReturn(Arrays.asList(table1, table2));

        ResponseEntity<List<TableResponse>> response = tableController.findTableByRestaurant();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList(TableResponse.toTableResponse(table1), TableResponse.toTableResponse(table2)), response.getBody());
    }

    @Test
    public void getTableLastOrder_shouldReturnOkWithOrder() {
        Table table = new Table("1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Order order = new Order(new User(), table);
        List<OrderItem> items = Arrays.asList(
                new OrderItem("1", order, new Item(), 10),
                new OrderItem("2", order, new Item(), 15)
        );
        when(orderService.findLastOrderByTableIdAndStatus(OrderStatus.IN_PREPARATION, "1")).thenReturn(order);
        when(orderService.findOrderItemsByOrder(order)).thenReturn(items);

        ResponseEntity<OrderResponse> response = tableController.getTableLastOrder("1");

        assertEquals(HttpStatus.OK,
                response.getStatusCode());
        assertEquals(OrderResponse.build(order, items), response.getBody());
    }

    @Test
    public void findTableById_shouldReturnOkWithTable() {
        Table table = new Table("1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        when(tableService.findTableById("1")).thenReturn(table);

        ResponseEntity<TableResponse> response = tableController.findTableById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TableResponse.toTableResponse(table), response.getBody());
    }


}

