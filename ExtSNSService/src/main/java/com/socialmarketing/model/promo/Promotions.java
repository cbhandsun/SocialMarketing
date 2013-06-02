package com.socialmarketing.model.promo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Promotions entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="promotions"
    ,catalog="extsns"
)

public class Promotions extends com.socialmarketing.core.model.DataAuditEntityBase implements java.io.Serializable {


    // Fields    

     private Long ID;
     private String uuid;
     private String compId;
     private String prodType;
     private String prodId;
     private String promoDesp;
     private String productName;


    // Constructors

    /** default constructor */
    public Promotions() {
    }

	/** minimal constructor */
    public Promotions(String uuid) {
        this.uuid = uuid;
    }
    
    /** full constructor */
    public Promotions(String uuid, String compId, String prodType, String prodId, String promoDesp, String productName) {
        this.uuid = uuid;
        this.compId = compId;
        this.prodType = prodType;
        this.prodId = prodId;
        this.promoDesp = promoDesp;
        this.productName = productName;
    }

   
    // Property accessors
    @Id @GeneratedValue
    
    @Column(name="ID", unique=true, nullable=false)

    public Long getID() {
        return this.ID;
    }
    
    public void setID(Long ID) {
        this.ID = ID;
    }
    
    @Column(name="uuid", nullable=false, length=20)

    public String getUuid() {
        return this.uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    @Column(name="compID", length=20)

    public String getCompId() {
        return this.compId;
    }
    
    public void setCompId(String compId) {
        this.compId = compId;
    }
    
    @Column(name="prodType", length=20)

    public String getProdType() {
        return this.prodType;
    }
    
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    
    @Column(name="prodID", length=20)

    public String getProdId() {
        return this.prodId;
    }
    
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    
    @Column(name="promoDesp", length=45)

    public String getPromoDesp() {
        return this.promoDesp;
    }
    
    public void setPromoDesp(String promoDesp) {
        this.promoDesp = promoDesp;
    }
    
    @Column(name="productName", length=45)

    public String getProductName() {
        return this.productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
   








}