package com.socialmarketing.web.modules.barcode.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * QRConfig entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="qrconfig"
    ,catalog="extsns"
)
public class QRConfig extends com.socialmarketing.dao.model.DataAuditEntityBase implements java.io.Serializable {


    // Fields    

     private String uuid;
     private String height;
     private String type;
     private String moduleWidth;
     private String wideFactor;
     private String quietZone;
     private String humanReadable;
     private String humanReadableSize;
     private String humanReadableFont;
     private String humanReadablePattern;
     private boolean qrconfigcol;
     private boolean gray;
     private String cmpId;


    // Constructors

    /** default constructor */
    public QRConfig() {
    }

    
    /** full constructor */
    public QRConfig(String height, String type, String moduleWidth, String wideFactor, String quietZone, String humanReadable, String humanReadableSize, String humanReadableFont, String humanReadablePattern, boolean qrconfigcol, boolean gray, String cmpId) {
        this.height = height;
        this.type = type;
        this.moduleWidth = moduleWidth;
        this.wideFactor = wideFactor;
        this.quietZone = quietZone;
        this.humanReadable = humanReadable;
        this.humanReadableSize = humanReadableSize;
        this.humanReadableFont = humanReadableFont;
        this.humanReadablePattern = humanReadablePattern;
        this.qrconfigcol = qrconfigcol;
        this.gray = gray;
        this.cmpId = cmpId;
    }

   
    // Property accessors
    @Id 
    @Column(name="UUID", unique=true, nullable=false)
    public String getUuid() {
        return this.uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    @Column(name="height", length=45)

    public String getHeight() {
        return this.height;
    }
    
    public void setHeight(String height) {
        this.height = height;
    }
    
    @Column(name="type", length=45)

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @Column(name="moduleWidth", length=45)

    public String getModuleWidth() {
        return this.moduleWidth;
    }
    
    public void setModuleWidth(String moduleWidth) {
        this.moduleWidth = moduleWidth;
    }
    
    @Column(name="wideFactor", length=45)

    public String getWideFactor() {
        return this.wideFactor;
    }
    
    public void setWideFactor(String wideFactor) {
        this.wideFactor = wideFactor;
    }
    
    @Column(name="quietZone", length=45)

    public String getQuietZone() {
        return this.quietZone;
    }
    
    public void setQuietZone(String quietZone) {
        this.quietZone = quietZone;
    }
    
    @Column(name="humanReadable", length=45)

    public String getHumanReadable() {
        return this.humanReadable;
    }
    
    public void setHumanReadable(String humanReadable) {
        this.humanReadable = humanReadable;
    }
    
    @Column(name="humanReadableSize", length=45)

    public String getHumanReadableSize() {
        return this.humanReadableSize;
    }
    
    public void setHumanReadableSize(String humanReadableSize) {
        this.humanReadableSize = humanReadableSize;
    }
    
    @Column(name="humanReadableFont", length=45)

    public String getHumanReadableFont() {
        return this.humanReadableFont;
    }
    
    public void setHumanReadableFont(String humanReadableFont) {
        this.humanReadableFont = humanReadableFont;
    }
    
    @Column(name="humanReadablePattern", length=45)

    public String getHumanReadablePattern() {
        return this.humanReadablePattern;
    }
    
    public void setHumanReadablePattern(String humanReadablePattern) {
        this.humanReadablePattern = humanReadablePattern;
    }
    
    @Column(name="QRConfigcol")

    public boolean getQrconfigcol() {
        return this.qrconfigcol;
    }
    
    public void setQrconfigcol(boolean qrconfigcol) {
        this.qrconfigcol = qrconfigcol;
    }
    
    @Column(name="gray")

    public boolean getGray() {
        return this.gray;
    }
    
    public void setGray(boolean gray) {
        this.gray = gray;
    }
    
    @Column(name="cmpID", length=45)

    public String getCmpId() {
        return this.cmpId;
    }
    
    public void setCmpId(String cmpId) {
        this.cmpId = cmpId;
    }






}