package com.realfinal.toot.db.repository;

import com.realfinal.toot.db.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {


}
