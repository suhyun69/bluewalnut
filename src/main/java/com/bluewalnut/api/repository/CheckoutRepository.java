package com.bluewalnut.api.repository;

import com.bluewalnut.api.entity.CheckoutT;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<CheckoutT, String> {
    List<CheckoutT> findAllByCi(String ci);
}
