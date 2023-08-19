package com.progressoftassesment.progressoftassesment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "FX_Deals")
public class FxDeals {


    @Id
    @NotNull
    private Long dealId ;
    private String fromCurrencyISOCode;
    private String toCurrencyISOCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dealTimeStamp;
    @DecimalMin("0.0")
    private  double amountOfDeal ;

    public FxDeals(Long dealId, String fromCurrencyISOCode, String toCurrencyISOCode, Date dealTimeStamp, double amountOfDeal) {
        this.dealId = dealId;
        this.fromCurrencyISOCode = fromCurrencyISOCode;
        this.toCurrencyISOCode = toCurrencyISOCode;
        this.dealTimeStamp = dealTimeStamp;
        this.amountOfDeal = amountOfDeal;
    }

    @PrePersist
    private void onCreate(){
        dealTimeStamp=new Date();
    }


}
