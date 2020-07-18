package com.apiservice.service;

import com.apiservice.entity.PurchaseRecord;
import com.apiservice.model.PurchaseRecordCharge;
import com.apiservice.repository.PurchaseRecordRepository;
import com.apiservice.utils.exceptions.EntityNotFoundException;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseRecordService {

  private final PurchaseRecordRepository purchaseRecordRepository;
  private final ModelMapper modelMapper;

  @PostConstruct
  private void configModelMapper() {
    modelMapper.createTypeMap(PurchaseRecord.class, PurchaseRecord.class)
        .addMappings(mapper -> mapper.skip(PurchaseRecord::setId));
  }

  @Transactional(readOnly = true)
  public List<PurchaseRecord> getAllPurchaseRecord() {
    return purchaseRecordRepository.findAllByDeletedIsFalse();
  }

  @Transactional(readOnly = true)
  public PurchaseRecord getById(long id) {
    return purchaseRecordRepository.findByIdAndDeletedIsFalse(id)
        .orElseThrow(() -> EntityNotFoundException.of(PurchaseRecord.class, id));
  }

  @Transactional
  public PurchaseRecord save(PurchaseRecord purchaseRecord) {
    return purchaseRecordRepository.save(purchaseRecord);
  }

  /**
   * Update a purchase record.
   */
  @Transactional
  public PurchaseRecord update(long id, PurchaseRecord purchaseRecord) {
    PurchaseRecord purchaseRecordToUpdate =
        purchaseRecordRepository.findByIdAndDeletedIsFalse(id)
            .orElseThrow(() -> EntityNotFoundException.of(PurchaseRecord.class, id));
    modelMapper.map(purchaseRecord, purchaseRecordToUpdate);
    return purchaseRecordRepository.save(purchaseRecordToUpdate);
  }

  /**
   * Set deleted flag to a purchase record.
   */
  @Transactional
  public void delete(long id) {
    if (purchaseRecordRepository.deleteById(id) == 0) {
      throw EntityNotFoundException.of(PurchaseRecord.class, id);
    }
  }

  /**
   * Add charges to a purchase record.
   */
  @Transactional
  public PurchaseRecord addCharge(long id, PurchaseRecordCharge charge) {
    PurchaseRecord purchaseRecord =
        purchaseRecordRepository.findByIdAndDeletedIsFalse(id)
            .orElseThrow(() -> EntityNotFoundException.of(PurchaseRecord.class, id));
    switch (charge.getChargeType()) {
      case LC_CHARGE -> purchaseRecord.setLcCharge(charge.getAmount());
      case SHIPPING_CHARGE -> purchaseRecord.setShippingCharge(charge.getAmount());
      case TAX -> purchaseRecord.setTax(charge.getAmount());
      case AIT -> purchaseRecord.setAdvancedIncomeTax(charge.getAmount());
      case CNF_CHARGE -> purchaseRecord.setCnfCharge(charge.getAmount());
      case TRANSPORTATION_CHARGE -> purchaseRecord.setTransportationCharge(charge.getAmount());
      case GARAGE_CHARGE -> purchaseRecord.setGarageCharge(charge.getAmount());
      case MISC_CHARGE -> purchaseRecord.setMiscellaneousCharge(charge.getAmount());
      default -> throw new IllegalArgumentException(
          "Invalid charge type " + charge.getChargeType().name());
    }
    return purchaseRecordRepository.save(purchaseRecord);
  }
}
