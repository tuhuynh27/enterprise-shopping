package com.mrhmt.server.controllers;

import com.mrhmt.server.entities.Order;
import com.mrhmt.server.entities.OrderDetail;
import com.mrhmt.server.entities.OrderDetailIdentity;
import com.mrhmt.server.entities.Product;

import com.mrhmt.server.repositories.OrderDetailRepository;
import com.mrhmt.server.repositories.OrderRepository;
import com.mrhmt.server.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Optional;

@RestController
@RequestMapping("/order_detail")
public class OrderDetailController {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderDetailController(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    private OrderDetailIdentity commonFindId(int orderId, int productId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order order = optionalOrder.get();

        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product product = optionalProduct.get();

        return new OrderDetailIdentity(order,product);
    }

    @GetMapping("")
    Iterable<OrderDetail> readAll() {
        return orderDetailRepository.findAll();
    }

    @GetMapping("/{orderId}/{productId}")
    OrderDetail read(@PathVariable int orderId, @PathVariable int productId) {
        return orderDetailRepository.findById(commonFindId(orderId, productId))
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    @PostMapping("")
    OrderDetail create(@RequestBody OrderDetail newOrderDetail) {
        return orderDetailRepository.save(newOrderDetail);
    }

    @PutMapping("/{orderId}/{productId}")
    OrderDetail update(@RequestBody OrderDetail updatingOrderDetail, @PathVariable int orderId, @PathVariable int productId) {
        OrderDetailIdentity identity = commonFindId(orderId, productId);

        return orderDetailRepository.findById(identity)
                .map(orderDetail -> {
                    orderDetail.setQuantity(updatingOrderDetail.getQuantity());
                    orderDetail.setDiscount(updatingOrderDetail.getDiscount());
                    orderDetail.setValid(updatingOrderDetail.isValid());
                    orderDetail.setModified(Calendar.getInstance().getTime());

                    return orderDetailRepository.save(orderDetail);
                })
                .orElseGet(() -> {
                    updatingOrderDetail.setOrderDetailIdentify(identity);
                    return orderDetailRepository.save(updatingOrderDetail);
                });
    }

    @DeleteMapping("/{orderId}/{productId}")
    void delete(@PathVariable int orderId, @PathVariable int productId) {
        orderDetailRepository.deleteById(commonFindId(orderId, productId));
    }
}
