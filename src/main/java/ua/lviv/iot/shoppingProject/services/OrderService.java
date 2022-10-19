package ua.lviv.iot.shoppingProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.shoppingProject.dao.OrderRepository;
import ua.lviv.iot.shoppingProject.exceptions.OrderNotFoundException;
import ua.lviv.iot.shoppingProject.models.Order;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order addOrder(Order order) {
        return repository.save(order);
    }

    public Order updateOrder(Order order) throws OrderNotFoundException {
        if (repository.existsById(order.getId())) {
            return repository.save(order);
        }

        throw new OrderNotFoundException("Order with id:" + order.getId() + "not found in the system");
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }

    public Order getOrder(Integer id) {
        return repository.findById(id).orElseThrow();
    }
}
