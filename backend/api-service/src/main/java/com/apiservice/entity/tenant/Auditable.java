package com.apiservice.entity.tenant;

import com.apiservice.entity.master.operator.Operator;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Data;

@Data
@MappedSuperclass
public class Auditable {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", updatable = false, nullable = false)
  private Operator createdBy;

  @Column(name = "created_date", updatable = false, nullable = false)
  private LocalDateTime createdDate;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "last_modified_by")
  private Operator lastModifiedBy;

  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;

  @Transient
  public boolean isCreating() {
    return createdBy == null && createdDate == null;
  }
}
