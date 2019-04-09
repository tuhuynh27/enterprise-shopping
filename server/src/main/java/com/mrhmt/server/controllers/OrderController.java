package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.Order;
import com.mrhmt.server.entities.OrderDetail;
import com.mrhmt.server.repositories.OrderDetailRepository;
import com.mrhmt.server.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    @PostMapping("")
    Order create(@RequestBody Order newOrder) {
        newOrder.setOrderDate(Calendar.getInstance().getTime());
        newOrder.setModified(Calendar.getInstance().getTime());
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
