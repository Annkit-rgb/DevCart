package com.dev.cart.prject.DevCart.repositories;

import com.dev.cart.prject.DevCart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
