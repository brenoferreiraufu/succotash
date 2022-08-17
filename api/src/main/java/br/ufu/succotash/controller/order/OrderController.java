package br.ufu.succotash.controller.order;

import br.ufu.succotash.controller.order.request.NewOrderRequest;
import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.domain.model.User;
import br.ufu.succotash.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Transactional
public class OrderController {

    private final OrderService orderService;

    @PutMapping("/{orderId}")
    public ResponseEntity<?> editOrder(@PathVariable String orderId, @Valid @RequestBody OrderRequest orderItem) {
        orderService.editOrder(orderId, orderItem);
        URI location = URI.create("/api/v1/order/".concat(orderId));
        return ResponseEntity.ok().location(location).build();
    }

    @PostMapping
    public ResponseEntity<?> newOrder(@Valid @RequestBody NewOrderRequest orderRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.newOrder(orderRequest.tableId(), user));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.findOrder(orderId));
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<?> payOrder(@PathVariable String orderId) {
        orderService.payOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
