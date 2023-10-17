package com.realfinal.toot.db.repository;

import com.realfinal.toot.common.exception.user.EntitySearchException;
import com.realfinal.toot.db.entity.Interest;
import com.realfinal.toot.db.entity.Stock;
import com.realfinal.toot.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    Interest findByUserAndStock(User user, Stock stock) throws EntitySearchException;

    List<Interest> findAllByUser(User user) throws EntitySearchException;
}
