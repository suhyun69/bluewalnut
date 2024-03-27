package com.bluewalnut.api.repository;

import com.bluewalnut.api.entity.CheckoutT;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<CheckoutT, String> {
}
