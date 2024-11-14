package example.controller;

import example.dto.OrderRequestDto;
import example.dto.OrderResponseDto;
import example.dto.PatchValidation;
import example.dto.PutValidation;
import example.entity.Order;
import example.entity.User;
import example.repository.UserRepository;
import example.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order API", description = "APIs for managing orders")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Get all orders")
    @GetMapping
    public List<OrderResponseDto> getAllOrders() {
        return orderService.getOrders();
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @Operation(summary = "Create order")
    @PostMapping
    public OrderResponseDto createOrder(
            @Valid @RequestBody Order order
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(user);

        return orderService.convertToDto(orderService.createOrder(order));
    }

    @Operation(summary = "Update order")
    @PutMapping("/{id}")
    public OrderResponseDto updateOrder(
            @PathVariable Long id,
            @Validated(PutValidation.class) @RequestBody OrderRequestDto orderRequestDto
    ) {
        return orderService.convertToDto(orderService.updateOrder(id, orderRequestDto));
    }

    @Operation(summary = "Update order partially")
    @PatchMapping("/{id}")
    public OrderResponseDto updateOrderPatch(
            @PathVariable Long id,
            @Validated(PatchValidation.class) @RequestBody OrderRequestDto orderRequestDto
    ) {
        return orderService.convertToDto(orderService.updateOrderPatch(id, orderRequestDto));
    }

    @Operation(summary = "Delete order")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
