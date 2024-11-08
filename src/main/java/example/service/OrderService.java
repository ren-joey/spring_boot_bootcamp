package example.service;

import example.dto.OrderResponseDto;
import example.entity.Order;
import example.entity.User;
import example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public OrderResponseDto convertToDto(Order order) {
        User user = order.getUser();
        System.out.println(order.getName());
        System.out.println(order.getPrice());
        System.out.println(user.getUsername());
        OrderResponseDto orderResponseDto = new OrderResponseDto(order.getName(), order.getPrice(), user.getUsername());
        System.out.println(orderResponseDto);
        return orderResponseDto;
    }
}
