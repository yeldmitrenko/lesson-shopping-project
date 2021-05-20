package ua.lviv.iot.shoppingProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.lviv.iot.shoppingProject.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
