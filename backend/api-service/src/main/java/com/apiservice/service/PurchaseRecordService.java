package com.apiservice.service;

import com.apiservice.entity.PurchaseRecord;
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
}
