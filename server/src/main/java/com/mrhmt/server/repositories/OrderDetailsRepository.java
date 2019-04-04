package com.mrhmt.server.repositories;

import com.mrhmt.server.entities.OrderDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    @Query(value = "SELECT od FROM OrderDetail od")
    List<OrderDetails> paging(Pageable pageable);

    @Query("SELECT od FROM OrderDetail od WHERE LOWER(od.orderDetailIdentity.order.id) = LOWER(:orderId)")
    List<OrderDetails> search(@Param("orderId") String orderId);
}
