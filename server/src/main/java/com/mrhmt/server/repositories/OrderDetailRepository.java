package com.mrhmt.server.repositories;

import com.mrhmt.server.entities.OrderDetail;
import com.mrhmt.server.entities.OrderDetailIdentity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailIdentity> {
    @Query(value = "SELECT od FROM OrderDetail od")
    List<OrderDetail> paging(Pageable pageable);

    @Query("SELECT od FROM OrderDetail od WHERE LOWER(od.orderDetailIdentify.order.id) = LOWER(:orderId)")
    List<OrderDetail> search(@Param("orderId") String orderId);
}
