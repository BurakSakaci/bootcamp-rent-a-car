package com.turkcell.rentACar.core.entities.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.core.entities.concrates.User;
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

}
