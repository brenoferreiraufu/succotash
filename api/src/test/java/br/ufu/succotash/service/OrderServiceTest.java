package br.ufu.succotash.service;

import br.ufu.succotash.controller.order.request.ItemQuantity;
import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.controller.order.response.OrderResponse;
import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.enumeration.Role;
import br.ufu.succotash.domain.enumeration.TableStatus;
import br.ufu.succotash.domain.model.*;
import br.ufu.succotash.mock.MockPaymentClient;
import br.ufu.succotash.repository.OrderItemRepository;
import br.ufu.succotash.repository.OrderRepository;
import br.ufu.succotash.repository.TableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private MockPaymentClient paymentClient;

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void toModel_shouldReturnOrderItems() {
        User user = new User("123", "Pedro", "usuario1", Role.WORKER);
        Table table = new Table("1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Order order = new Order(user, table);
        List<ItemQuantity> items = List.of(new ItemQuantity("1", new Item(), 1), new ItemQuantity("2", new Item(), 2));
        List<OrderItem> expectedOrderItems = List.of(new OrderItem("1", order, items.get(0).item(), items.get(0).quantity()), new OrderItem("2", order, items.get(1).item(), items.get(1).quantity()));

        expectedOrderItems.forEach(orderItem -> when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.of(orderItem)));

        List<OrderItem> result = orderService.toModel(order, items);

        assertEquals(expectedOrderItems, result);
    }

    @Test
    public void editOrder_shouldEditOrder() {
        User user = new User("123", "Pedro", "usuario1", Role.WORKER);
        Table table = new Table("1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Order order = new Order(user, table);
        order.setId("1");
        List<ItemQuantity> items = List.of(new ItemQuantity("1", new Item(), 1), new ItemQuantity("2", new Item(), 2));
        List<OrderItem> expectedOrderItems = List.of(new OrderItem("1", order, items.get(0).item(), items.get(0).quantity()), new OrderItem("2", order, items.get(1).item(), items.get(1).quantity()));
        OrderRequest orderRequest = new OrderRequest(items);

        when(orderRepository.findById("1")).thenReturn(Optional.of(order));
        expectedOrderItems.forEach(orderItem -> when(orderItemRepository.findById(orderItem.getId())).thenReturn(Optional.of(orderItem)));
        expectedOrderItems.forEach(orderItem -> when(orderItemRepository.save(orderItem)).thenReturn(orderItem));

        orderService.editOrder("1", orderRequest);

        verify(orderRepository).findById("1");
        verify(orderRepository).save(order);
        expectedOrderItems.forEach(orderItem -> verify(orderItemRepository).save(orderItem));
    }

    @Test
    public void editOrder_shouldThrowExceptionWhenOrderNotFound() {
        String orderId = "orderId";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        List<ItemQuantity> items = List.of(new ItemQuantity("1", new Item(), 1), new ItemQuantity("2", new Item(), 2));
        OrderRequest orderRequest = new OrderRequest(items);

        assertThrows(ResponseStatusException.class, () -> orderService.editOrder(orderId, orderRequest));
    }

    @Test
    public void findOrder_shouldFindOrder() {
        String orderId = "orderId";
        Order order = new Order();
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.findOrder(orderId);

        verify(orderRepository).findById(orderId);
        assertEquals(order, result);
    }

    @Test
    public void findLastOrderByTableIdAndStatus_shouldFindLastOrder() {
        String tableId = "tableId";
        OrderStatus status = OrderStatus.IN_PREPARATION;
        Order order = new Order();
        when(orderRepository.findFirstByStatusAndTableIdOrderByCreatedAt(status, tableId)).thenReturn(Optional.of(order));

        Order result = orderService.findLastOrderByTableIdAndStatus(status, tableId);

        verify(orderRepository).findFirstByStatusAndTableIdOrderByCreatedAt(status, tableId);
        assertEquals(order, result);
    }

    @Test
    public void findOrderItemsByOrder_shouldFindOrderItems() {
        Order order = new Order();
        List<OrderItem> orderItems = List.of(new OrderItem(), new OrderItem());
        when(orderItemRepository.findByOrder(order)).thenReturn(orderItems);

        List<OrderItem> result = orderService.findOrderItemsByOrder(order);

        verify(orderItemRepository).findByOrder(order);
    }

    @Test
    public void payOrder_shouldPayOrder() {
        String orderId = "orderId";
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.IN_PREPARATION);
        List<OrderItem> orderItems = List.of(
                new OrderItem(order, new Item("1", "item.png", "item1", "Item 1",  new BigDecimal(10), new Restaurant()), 1),
                new OrderItem(order, new Item("2", "item.png", "item2", "Item 2",  new BigDecimal(20), new Restaurant()), 2)
        );

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(orderItems);
        when(paymentClient.pay(any())).thenReturn(HttpStatus.OK);

        orderService.payOrder(orderId);

        verify(orderRepository).findById(orderId);
        verify(orderItemRepository).findByOrder(order);
        verify(paymentClient).pay(any());
        verify(orderRepository).save(order);
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    public void payOrder_shouldThrowExceptionWhenOrderIsCompleted() {
        String orderId = "orderId";
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.COMPLETED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(ResponseStatusException.class, () -> orderService.payOrder(orderId));
    }

    @Test
    public void payOrder_shouldThrowExceptionWhenPaymentFailed() {
        String orderId = "orderId";
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.IN_PREPARATION);
        List<OrderItem> orderItems = List.of(
                new OrderItem(order, new Item("1", "item.png", "item1", "Item 1",  new BigDecimal(10), new Restaurant()), 1),
                new OrderItem(order, new Item("2", "item.png", "item2", "Item 2",  new BigDecimal(20), new Restaurant()), 2)
        );

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(orderItems);
        when(paymentClient.pay(any())).thenReturn(HttpStatus.BAD_REQUEST);

        assertThrows(ResponseStatusException.class, () -> orderService.payOrder(orderId));
    }

    @Test
    public void newOrder_shouldReturnExistingOrder() {
        User user = new User("123", "Pedro", "usuario1", Role.WORKER);
        Table table = new Table("1", "1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Order order = new Order(user, table);
        List<OrderItem> orderItems = List.of(
                new OrderItem(order, new Item("1", "item.png", "item1", "Item 1",  new BigDecimal(10), new Restaurant()), 1),
                new OrderItem(order, new Item("2", "item.png", "item2", "Item 2",  new BigDecimal(20), new Restaurant()), 2)
        );

        when(orderRepository.findFirstByStatusAndTableIdOrderByCreatedAt(OrderStatus.IN_PREPARATION, "1")).thenReturn(Optional.of(order));
        when(orderItemRepository.findByOrder(order)).thenReturn(orderItems);


        OrderResponse result = orderService.newOrder("1", user);

        verify(orderRepository).findFirstByStatusAndTableIdOrderByCreatedAt(OrderStatus.IN_PREPARATION, "1");
        verify(orderItemRepository).findByOrder(order);
        assertEquals(OrderResponse.build(order, orderItems), result);
    }

    @Test
    public void newOrder_shouldReturnNewOrder() {
        User user = new User("123", "Pedro", "usuario1", Role.WORKER);
        Table table = new Table("1", "1", "Table 1", new Restaurant(), TableStatus.ORDERING);
        Order order = new Order(user, table);

        when(orderRepository.findFirstByStatusAndTableIdOrderByCreatedAt(OrderStatus.IN_PREPARATION, "1")).thenReturn(Optional.empty());
        when(tableRepository.findById("1")).thenReturn(Optional.of(table));
        when(orderRepository.save(any())).thenReturn(order);

        OrderResponse result = orderService.newOrder("1", user);

        verify(orderRepository).findFirstByStatusAndTableIdOrderByCreatedAt(OrderStatus.IN_PREPARATION, "1");
        verify(tableRepository).findById("1");
        assertEquals(OrderResponse.build(order, List.of()), result);
    }

    @Test
    public void newOrder_shouldThrowExceptionWhenTableNotFound() {
        String tableId = "tableId";
        User user = new User();
        when(tableRepository.findById(tableId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> orderService.newOrder(tableId, user));
    }

    @Test
    public void cancelOrderItem_shouldCancelOrderItem() {
        String orderItemId = "orderItemId";

        orderService.cancelOrderItem(orderItemId);

        verify(orderItemRepository).deleteById(orderItemId);
    }

}
