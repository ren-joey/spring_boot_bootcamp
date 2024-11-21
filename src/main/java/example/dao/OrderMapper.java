package example.dao;

import example.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM orders")
    List<Order> getAllOrders();
}