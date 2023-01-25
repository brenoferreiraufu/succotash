package br.ufu.succotash.controller.order;

import br.ufu.succotash.controller.order.request.ItemQuantity;
import br.ufu.succotash.controller.order.request.NewOrderRequest;
import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.controller.order.response.OrderResponse;
import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.enumeration.TableStatus;
import br.ufu.succotash.domain.model.*;
import br.ufu.succotash.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void editOrder_shouldReturnOkWithLocation() {
        OrderRequest orderItem = new OrderRequest(List.of(new ItemQuantity("item1", new Item(), 10)));
        doNothing().when(orderService).editOrder("1", orderItem);

        ResponseEntity<?> response = orderController.editOrder("1", orderItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(URI.create("/api/v1/order/1"), response.getHeaders().getLocation());
    }

    @Test
    public void newOrder_shouldReturnOkWithOrderId() {
        NewOrderRequest orderRequest = new NewOrderRequest("1");
        User user = new User("123", "Pedro", "usuario1", Role.WORKER);
        Table table = new Table("1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Order order = new Order(user, table);
        when(orderService.newOrder("1", user)).thenReturn(OrderResponse.build(order, List.of()));

        ResponseEntity<?> response = orderController.newOrder(orderRequest, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(OrderResponse.build(order, List.of()), response.getBody());
    }

    @Test
    public void findOrder_shouldReturnOkWithOrder() {
        User user = new User("123", "Pedro", "usuario1", Role.WORKER);
        Table table = new Table("1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Order order = new Order(user, table);
        List<OrderItem> items = Arrays.asList(
                new OrderItem("1", order, new Item(), 10),
                new OrderItem("2", order, new Item(), 15)
        );
        when(orderService.findOrder("1")).thenReturn(order);
        when(orderService.findOrderItemsByOrder(order)).thenReturn(items);

        ResponseEntity<?> response = orderController.findOrder("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(OrderResponse.build(order, items), response.getBody());
    }

    @Test
    public void cancelOrderItem_shouldReturnOk() {
        orderController.cancelOrderItem("1");

        verify(orderService, times(1)).cancelOrderItem("1");
    }

}
