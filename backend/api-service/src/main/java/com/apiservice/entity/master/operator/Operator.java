package com.apiservice.entity.master.operator;

import com.apiservice.entity.master.company.Company;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "operator", schema = "public")
public class Operator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private OperatorRole role;

  @Column(name = "email", nullable = false)
  private String email;

  @JoinColumn(name = "company_id", updatable = false)
  @OneToOne(fetch = FetchType.EAGER)
  private Company company;

  public boolean isSuperAdmin() {
    return role.equals(OperatorRole.SUPER_ADMIN);
  }
}