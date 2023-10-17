package com.realfinal.toot.db.repository;

import com.realfinal.toot.common.exception.user.EntitySearchException;
import com.realfinal.toot.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByProviderId(String providerId) throws EntitySearchException;

}
