package com.mrhmt.server.repositories;

import com.mrhmt.server.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByName(String name);

    static Specification<User> filterByName(String name) {
        return (root, cq, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }
}
