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


    /*public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPatientName() {
        return patientName;
    }

    public void setPatientName(BigDecimal patientName) {
        this.patientName = patientName;
    }*/
}
