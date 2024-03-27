package com.bluewalnut.api.entity;

import com.bluewalnut.api.domain.CheckoutReq;
import com.bluewalnut.api.domain.CheckoutStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHECKOUT")
public class CheckoutT {

    @Id
    private String id;

    private String ci;
    private String cardRefId;
    private String currency;
    private String amount;
    private String status; // Not Started, Executing, Success, Failed
    private LocalDateTime expirationTime;



    public void updateStatus(CheckoutStatus status) {
        this.status = status.name();
    }
}
