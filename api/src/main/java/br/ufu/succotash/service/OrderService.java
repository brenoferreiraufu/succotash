package br.ufu.succotash.service;

import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.controller.order.response.OrderResponse;
import br.ufu.succotash.domain.enumeration.OrderStatus;
import br.ufu.succotash.domain.model.Order;
import br.ufu.succotash.domain.model.OrderItem;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.mock.MockPaymentClient;
import br.ufu.succotash.mock.MockPaymentRequest;
import br.ufu.succotash.repository.OrderItemRepository;
import br.ufu.succotash.repository.OrderRepository;
import br.ufu.succotash.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MockPaymentClient paymentClient;
    private final TableRepository tableRepository;

    public void editOrder(String orderId, OrderRequest orderRequest) {
        var order = findOrder(orderId);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        for (OrderItem orderItem : orderRequest.toModel(order)) {
            orderItemRepository.save(orderItem);
        }
    }

    public Order findOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    public Order findLastOrderByTableIdAndStatus(OrderStatus status, String tableId) {
        return orderRepository.findFirstByStatusAndTableIdOrderByCreatedAt(status, tableId)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }

    public List<OrderItem> findOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    public void payOrder(String orderId) {
        var order = findOrder(orderId);

        if (order.isStatusCompleted()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var orderItems = orderItemRepository.findByOrder(order);

        var total = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            var price = orderItem.getItem().getPrice();
            var quantity = new BigDecimal(orderItem.getQuantity());
            total = total.add(price.multiply(quantity));
        }

        var response = paymentClient.pay(new MockPaymentRequest(total, order.getId()));

        if (response == HttpStatus.BAD_REQUEST) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    public OrderResponse newOrder(String tableId, User user) {
        var retrivedOrder = orderRepository.findFirstByStatusAndTableIdOrderByCreatedAt(OrderStatus.IN_PREPARATION, tableId);

        if (retrivedOrder.isPresent()) {
            var items = findOrderItemsByOrder(retrivedOrder.get());
            return OrderResponse.build(retrivedOrder.get(), items);
        }

        var table = tableRepository.findById(tableId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED);
        });

        var order = orderRepository.save(new Order(user, table));

        return OrderResponse.build(order, List.of());
    }
}