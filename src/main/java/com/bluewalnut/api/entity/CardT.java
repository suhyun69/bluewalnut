package com.bluewalnut.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARD")
public class CardT {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long seq;

    private String ci;
    private String cardNo;
    private String cardRefId;

    public CardT(String ci, String cardNo, String cardRefId) {
        this.ci = ci;
        this.cardNo = cardNo;
        this.cardRefId = cardRefId;
    }
}
