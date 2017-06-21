package com.emr.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;

import org.h2.jdbc.JdbcBlob;
import lombok.Data;
import org.hibernate.type.BlobType;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;

    private String patientName;
    private String patientId;
    private String practiceName;
    private String fileUrl;
}
