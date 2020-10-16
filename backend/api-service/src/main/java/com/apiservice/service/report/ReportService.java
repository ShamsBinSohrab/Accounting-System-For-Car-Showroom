package com.apiservice.service.report;

import com.apiservice.entity.tenant.car.Car;
import com.apiservice.model.car.CarFilter;
import com.apiservice.model.car.CarQueryBuilder;
import com.apiservice.repository.car.CarRepository;
import com.apiservice.utils.pagination.QueryBuilder;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {

  private final CarRepository carRepository;
  private Resource carReportTemplate;

  @PostConstruct
  private void initReportTemplates() {
    carReportTemplate = new ClassPathResource("reports/car_report_template.jrxml");
  }

  public byte[] getCarReport(CarFilter filter) {
    final QueryBuilder<Car> queryBuilder = new CarQueryBuilder(filter);
    final List<Car> cars = carRepository.findAll(queryBuilder.buildQuery());
    return generatePdfReport(carReportTemplate, cars);
  }

  private byte[] generatePdfReport(Resource resource, Collection<?> collection) {
    try {
      final JasperReport report = JasperCompileManager.compileReport(resource.getInputStream());
      final JRBeanCollectionDataSource data = new JRBeanCollectionDataSource(collection);
      final JasperPrint print = JasperFillManager.fillReport(report, null, data);
      return JasperExportManager.exportReportToPdf(print);
    } catch (JRException | IOException ex) {
      log.info(ex.getMessage());
      return new byte[0];
    }
  }
}
