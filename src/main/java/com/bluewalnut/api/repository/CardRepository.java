package com.bluewalnut.api.repository;

import com.bluewalnut.api.entity.CardT;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository  extends JpaRepository<CardT, Long> {
    Boolean existsByCardNo(String cardNo);
    List<CardT> findAllByCi(String ci);
}
