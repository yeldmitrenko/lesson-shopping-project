package ua.lviv.iot.shoppingProject.controllers;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.shoppingProject.exceptions.OrderNotFoundException;
import ua.lviv.iot.shoppingProject.models.Order;
import ua.lviv.iot.shoppingProject.services.OrderService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = Logger.getLogger("ua.lviv.iot.shoppingProject.controllers.OrderController");

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable(name = "id") Integer id) {
        try{
            return new ResponseEntity<Order>(orderService.getOrder(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            LOGGER.severe("Can't get an order with non-existing id" + id);
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PutMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        if (order.getId() != null) {
            return new ResponseEntity<Order>(orderService.addOrder(order), HttpStatus.OK);
        }

        LOGGER.severe("Failed to create an order with passed id. Order creation should not use external ids");
        return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {

        if (order.getId() == null) {
            LOGGER.severe("Can't update order without id - null value was passed instead ids");
            return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<Order>(orderService.updateOrder(order), HttpStatus.OK);
        } catch (OrderNotFoundException e){
            LOGGER.severe("Cant update an order with non-existing id: " + order.getId());
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }
}
