package com.apiservice.entity.tenant;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

  @CreatedBy
  @Column(name = "created_by", updatable = false, nullable = false)
  private Long createdBy;

  @CreatedDate
  @Column(name = "created_date", updatable = false, nullable = false)
  private LocalDateTime createdDate;

  @LastModifiedBy
  @Column(name = "last_modified_by")
  private Long lastModifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;
}
