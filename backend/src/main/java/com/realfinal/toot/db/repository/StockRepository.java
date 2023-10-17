package com.realfinal.toot.db.repository;

import com.realfinal.toot.db.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {

}
