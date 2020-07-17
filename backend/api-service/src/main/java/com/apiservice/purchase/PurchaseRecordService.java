package com.apiservice.purchase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseRecordService {

    private final PurchaseRecordRepository purchaseRecordRepository;

    public List<PurchaseRecord> getAllPurchaseRecord() {
        return purchaseRecordRepository.findAll();
    }

    public PurchaseRecord getById(long id) {
        return purchaseRecordRepository.findById(id).orElseThrow();
    }

    public void save(PurchaseRecord purchaseRecord) {
        purchaseRecordRepository.save(purchaseRecord);
    }
}
