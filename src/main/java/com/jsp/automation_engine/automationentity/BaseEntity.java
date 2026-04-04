package com.jsp.automation_engine.automationentity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    private Date createdDate;
    private Date modifiedDate;
    private String modifiedBy;
    private String createdBy;
}