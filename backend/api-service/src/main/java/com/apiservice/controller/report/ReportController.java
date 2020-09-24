package com.apiservice.controller.report;

import com.apiservice.model.car.CarFilter;
import com.apiservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_PDF_VALUE)
public class ReportController {

  private final ReportService reportService;

  @GetMapping(value = "/reports/car")
  public ResponseEntity<byte[]> carReport(CarFilter filter) {
    final byte[] content = reportService.getCarReport(filter);
    return ResponseEntity.ok()
        .header("Content-Disposition", "attachment; filename=\"cars.pdf\"")
        .body(content);
  }
}
