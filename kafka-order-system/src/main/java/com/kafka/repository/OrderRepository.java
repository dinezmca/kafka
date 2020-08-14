package com.kafka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kafka.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

}
