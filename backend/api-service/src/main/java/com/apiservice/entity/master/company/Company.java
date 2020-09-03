package com.apiservice.entity.master.company;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "company", schema = "public")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "company_name", nullable = false)
  private String companyName;

  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "uuid", nullable = false, unique = true)
  private UUID uuid;

  public Company() {
    uuid = UUID.randomUUID();
  }
}
