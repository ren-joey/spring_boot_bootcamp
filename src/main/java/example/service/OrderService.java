package example.service;

import example.dto.OrderRequestDto;
import example.dto.OrderResponseDto;
import example.entity.Order;
import example.entity.User;
import example.repository.OrderRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponseDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> ordersDto = orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        System.out.println(ordersDto);
        return ordersDto;
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }
        return order.get();
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, OrderRequestDto orderRequestDto) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }
        Order order = orderOptional.get();
        User user = order.getUser();
        System.out.printf(
                "Validated user: %s, Order user: %s\n",
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(),
                user.getUsername()
        );
        if (!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())) {
            throw new RuntimeException("User not authorized");
        }
        order.setName(orderRequestDto.getName());
        order.setPrice(orderRequestDto.getPrice());
        return orderRepository.save(order);
    }

    public OrderResponseDto convertToDto(Order order) {
        User user = order.getUser();
        System.out.println(order.getName());
        System.out.println(order.getPrice());
        System.out.println(user.getUsername());
        OrderResponseDto orderResponseDto = new OrderResponseDto(
                order.getId(),
                order.getName(),
                order.getPrice(),
                user.getUsername()
        );
        System.out.println(orderResponseDto);
        return orderResponseDto;
    }
}
