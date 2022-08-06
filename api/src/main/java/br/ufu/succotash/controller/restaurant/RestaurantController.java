package br.ufu.succotash.controller.restaurant;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/table")
@RequiredArgsConstructor
@Transactional
public class RestaurantController {

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
