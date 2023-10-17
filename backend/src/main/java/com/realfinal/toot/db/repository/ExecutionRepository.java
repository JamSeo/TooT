package com.realfinal.toot.db.repository;

import com.realfinal.toot.common.exception.user.EntitySearchException;
import com.realfinal.toot.db.entity.Execution;
import com.realfinal.toot.db.entity.Stock;
import com.realfinal.toot.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionRepository extends JpaRepository<Execution, Long> {

    List<Execution> findAllByUser(User user);

    List<Execution> findAllByUserAndBankruptcyNo(User user, Integer bankruptcyNo);

    List<Execution> findAllByUserAndBankruptcyNoAndStock(User user, Integer bankruptcyNo,
        Stock stock);
}
