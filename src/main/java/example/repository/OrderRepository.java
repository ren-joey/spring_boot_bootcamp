package example.repository;

import example.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // auto-generated methods:
    // findAll(),
    // findById(Long id),
    // save(S entity),
    // delete(S entity),
    // deleteById(ID id),
    // existsById(ID id),
    // count()
}
