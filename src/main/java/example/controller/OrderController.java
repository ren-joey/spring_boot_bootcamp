package example.controller;

import example.dto.OrderResponseDto;
import example.entity.Order;
import example.entity.User;
import example.repository.UserRepository;
import example.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderResponseDto createOrder(@Valid @RequestBody Order order) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        return orderService.convertToDto(orderService.createOrder(order));
    }
}
