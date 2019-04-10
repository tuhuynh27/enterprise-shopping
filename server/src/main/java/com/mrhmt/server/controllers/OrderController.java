package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.Order;
import com.mrhmt.server.entities.OrderDetail;
import com.mrhmt.server.entities.User;
import com.mrhmt.server.repositories.OrderDetailRepository;
import com.mrhmt.server.repositories.OrderRepository;
import com.mrhmt.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    Iterable<Order> readAll() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    Order read(@PathVariable int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @GetMapping("/{id}/view")
    Iterable<OrderDetail> readDetail(@PathVariable int id) {
        return orderDetailRepository.search(id);
    }

    @GetMapping("/my")
    Iterable<Order> readMyOrders(Authentication authentication) {
        User user = userRepository.findByName(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findOrdersByUserId(user.getId());
    }

    @PostMapping("")
    Order create(@RequestBody Order newOrder, Authentication authentication) {
        newOrder.setOrderDate(Calendar.getInstance().getTime());
        newOrder.setModified(Calendar.getInstance().getTime());

        // Get user
        User user = userRepository.findByName(authentication.getName()).orElseThrow(() -> new RuntimeException(("User not found")));
        newOrder.setUserId(user.getId());

        return orderRepository.save(newOrder);
    }

    @PostMapping("/{id}/shipped")
    Order shipped(@PathVariable int id) {
        Order shipped = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException (Integer.toString(id)));
        shipped.setShipped(true);
        shipped.setModified(Calendar.getInstance().getTime());

        return orderRepository.save(shipped);
    }

    @PutMapping("/{id}")
    Order update(@RequestBody Order updatingOrder, @PathVariable int id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setDescription(updatingOrder.getDescription());
                    order.setShipped(updatingOrder.isShipped());
                    order.setUserId(updatingOrder.getUserId());
                    order.setValid(updatingOrder.isValid());
                    order.setModified(Calendar.getInstance().getTime());

                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    updatingOrder.setId(id);
                    return orderRepository.save(updatingOrder);
                });
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {
        orderRepository.deleteById(id);
    }
}
