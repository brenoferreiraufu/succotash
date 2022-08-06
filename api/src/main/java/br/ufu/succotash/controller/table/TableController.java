package br.ufu.succotash.controller.table;

import br.ufu.succotash.controller.order.request.OrderRequest;
import br.ufu.succotash.controller.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/table")
@RequiredArgsConstructor
@Transactional
public class TableController {

/*
    @PostMapping
    public ResponseEntity<?> newOrder(@Valid @RequestBody OrderRequest order) {
        String orderId = orderService.newOrder(order);
        URI location = URI.create("/api/v1/order/".concat(orderId));
        return ResponseEntity.ok().location(location).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findOrder(@PathVariable String userId) {
        return null;
    }

 */

}
