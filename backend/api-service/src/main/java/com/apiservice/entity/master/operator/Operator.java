package com.apiservice.entity.master.operator;

import com.apiservice.entity.master.company.Company;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import java.util.UUID;
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
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Data
@Entity
@Table(name = "operator", schema = "public")
@TypeDefs({@TypeDef(name = "string-array", typeClass = StringArrayType.class)})
public class Operator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "uuid", nullable = false, unique = true)
  private UUID uuid;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private OperatorRole role;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "scopes", nullable = false)
  @Type(type = "string-array")
  private String[] scopes;

  @JoinColumn(name = "company_id", updatable = false)
  @OneToOne(fetch = FetchType.EAGER)
  private Company company;

  public boolean isSuperAdmin() {
    return role.equals(OperatorRole.SUPER_ADMIN);
  }
}