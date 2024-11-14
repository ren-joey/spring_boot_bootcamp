package example.service;

import example.dao.OrderDao;
import example.dto.OrderRequestDto;
import example.dto.OrderResponseDto;
import example.entity.Order;
import example.entity.User;
import example.repository.OrderRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDao orderDao;

    public OrderService(
            OrderRepository orderRepository,
            OrderDao orderDao
    ) {
        this.orderRepository = orderRepository;
        this.orderDao = orderDao;
    }

    public List<OrderResponseDto> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long id) {

        Optional<Order> order = orderDao.getOrderById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }
        return order.get();
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(
            Long id,
            OrderRequestDto orderRequestDto
    ) {
        Order order = orderUserValidation(id);
        order.setName(orderRequestDto.getName());
        order.setPrice(orderRequestDto.getPrice());
        return orderRepository.save(order);
    }

    public Order updateOrderPatch(
            Long id,
            OrderRequestDto orderRequestDto
    ) {
        Order order = orderUserValidation(id);

        if (Objects.nonNull(orderRequestDto.getName())) {
            order.setName(orderRequestDto.getName());
        }
        if (Objects.nonNull(orderRequestDto.getPrice())) {
            order.setPrice(orderRequestDto.getPrice());
        }
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        Order order = orderUserValidation(id);
        orderRepository.delete(order);
    }

    public Order orderUserValidation(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }
        Order order = orderOptional.get();
        User user = order.getUser();
        if (!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())) {
            throw new RuntimeException("User not authorized");
        }
        return order;
    }

    public OrderResponseDto convertToDto(Order order) {
        User user = order.getUser();
        return new OrderResponseDto(
                order.getId(),
                order.getName(),
                order.getPrice(),
                user.getUsername()
        );
    }
}
