package example.dao;

import example.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Mapper
public interface OrderDao {
    // FIXME: build error when deploying to docker
    public List<Order> getAllOrders();

    public Optional<Order> getOrderById(@Param("orderId") Long id);
}
