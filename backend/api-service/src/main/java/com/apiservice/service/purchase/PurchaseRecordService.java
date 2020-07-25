package com.apiservice.service.purchase;

import com.apiservice.entity.car.Car;
import com.apiservice.entity.purchase.PurchaseRecord;
import com.apiservice.model.purchase.PurchaseRecordCharge;
import com.apiservice.repository.purchase.PurchaseRecordRepository;
import com.apiservice.service.car.CarService;
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
  private final CarService carService;

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
  public void save(PurchaseRecord purchaseRecord) {
    Car car = purchaseRecord.getCar().isDraft()
        ? purchaseRecord.getCar()
        : carService.getByChassisNo(purchaseRecord.getCar().getChassisNo());
    purchaseRecord.setCar(car);
    purchaseRecordRepository.save(purchaseRecord);
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
