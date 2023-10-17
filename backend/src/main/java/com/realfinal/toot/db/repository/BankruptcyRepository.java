package com.realfinal.toot.db.repository;

import com.realfinal.toot.db.entity.Bankruptcy;
import com.realfinal.toot.db.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankruptcyRepository extends JpaRepository<Bankruptcy, Long> {
    List<Bankruptcy> findAllByUser(User user);
    Bankruptcy findByUserAndBankruptcyNo(User user, Integer bankruptcyNo);
}
