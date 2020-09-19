package com.apiservice.service;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.model.car.CarFilter;
import com.apiservice.model.car.CarQueryBuilder;
import com.apiservice.repository.car.CarRepository;
import com.apiservice.utils.pagination.QueryBuilder;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final CarRepository carRepository;
  private String carReportTemplateSourcePath;

  @PostConstruct
  private void initReportTemplates() throws FileNotFoundException {
    carReportTemplateSourcePath =
        ResourceUtils.getFile("classpath:reports/car_report_template.jrxml").getAbsolutePath();
  }

  public byte[] getCarReport(CarFilter filter) {
    final QueryBuilder<Car> queryBuilder = new CarQueryBuilder(filter);
    final List<Car> cars = carRepository.findAll(queryBuilder.buildQuery());
    return generatePdfReport(carReportTemplateSourcePath, cars);
  }

  private byte[] generatePdfReport(String sourceFilePath, Collection<?> collection) {
    try {
      final JasperReport report = JasperCompileManager.compileReport(sourceFilePath);
      final JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(collection);
      final JasperPrint print = JasperFillManager.fillReport(report, null, data);
      return JasperExportManager.exportReportToPdf(print);
    } catch (JRException ex) {
      throw new RuntimeException(ex);
    }
  }
}
