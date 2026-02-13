package com.myproject.simpleapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository
        extends JpaRepository<com.myproject.simpleapi.entities.User, Long> {
}
