package com.realfinal.toot.db.repository;

import com.realfinal.toot.common.exception.user.EntitySearchException;
import com.realfinal.toot.db.entity.Stock;
import com.realfinal.toot.db.entity.User;
import com.realfinal.toot.db.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStockRepository extends JpaRepository<UserStock, Long> {

    UserStock findByUserAndStockAndBankruptcyNo(User user, Stock stock, int bankruptcyNo)
        throws EntitySearchException;

    List<UserStock> findAllByUserAndBankruptcyNo(User user, int bankruptcyNo)
        throws EntitySearchException;
}
