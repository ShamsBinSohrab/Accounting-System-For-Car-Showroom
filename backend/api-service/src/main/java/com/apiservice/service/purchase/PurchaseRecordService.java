package com.apiservice.service.purchase;

import com.apiservice.entity.purchase.PurchaseRecord;
import com.apiservice.model.purchase.PurchaseRecordCharge;
import com.apiservice.repository.purchase.PurchaseRecordRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseRecordService {

  private final PurchaseRecordRepository purchaseRecordRepository;

  @Transactional(readOnly = true)
  public List<PurchaseRecord> getAllPurchaseRecord() {
    return purchaseRecordRepository.findAll();
  }

  @Transactional(readOnly = true)
  public PurchaseRecord getById(long id) {
    return purchaseRecordRepository.findById(id)
        .orElseThrow(() -> EntityNotFoundException.of(PurchaseRecord.class, id));
  }

  @Transactional
  public PurchaseRecord save(PurchaseRecord purchaseRecord) {
    return purchaseRecordRepository.save(purchaseRecord);
  }

  /**
   * Set deleted flag to a purchase record.
   */
  @Transactional
  public void delete(long id) {
    PurchaseRecord purchaseRecord = purchaseRecordRepository.findById(id)
        .orElseThrow(() ->  EntityNotFoundException.of(PurchaseRecord.class, id));
    purchaseRecordRepository.delete(purchaseRecord);
  }

  /**
   * Add charges to a purchase record.
   */
  @Transactional
  public PurchaseRecord addCharge(long id, PurchaseRecordCharge charge) {
    PurchaseRecord purchaseRecord =
        purchaseRecordRepository.findById(id)
            .orElseThrow(() -> EntityNotFoundException.of(PurchaseRecord.class, id));
//    switch (charge.getChargeType()) {
//      case LC_CHARGE -> purchaseRecord.setLcCharge(charge.getAmount());
//      case SHIPPING_CHARGE -> purchaseRecord.setShippingCharge(charge.getAmount());
//      case TAX -> purchaseRecord.setTax(charge.getAmount());
//      case AIT -> purchaseRecord.setAdvancedIncomeTax(charge.getAmount());
//      case CNF_CHARGE -> purchaseRecord.setCnfCharge(charge.getAmount());
//      case TRANSPORTATION_CHARGE -> purchaseRecord.setTransportationCharge(charge.getAmount());
//      case GARAGE_CHARGE -> purchaseRecord.setGarageCharge(charge.getAmount());
//      case MISC_CHARGE -> purchaseRecord.setMiscellaneousCharge(charge.getAmount());
//      default -> throw new IllegalArgumentException(
//          "Invalid charge type " + charge.getChargeType().name());
//    }
    return purchaseRecordRepository.save(purchaseRecord);
  }
}
