package com.apiservice.entity.tenant.sell;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.entity.tenant.purchase.CarPurchaseRecord;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "car_sell_record")
public class CarSellRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "car_purchase_record_id", unique = true)
    @OneToOne(fetch = FetchType.EAGER)
    private CarPurchaseRecord purchaseRecord;

    @JoinColumn(name = "sell_record_id", unique = true)
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SellRecord sellRecord;
}
