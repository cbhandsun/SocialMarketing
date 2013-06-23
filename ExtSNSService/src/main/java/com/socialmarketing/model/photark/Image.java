package com.socialmarketing.model.photark;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.InputStream;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Image entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="pho_image"
    ,catalog="extsns"
)

public class Image extends com.socialmarketing.core.model.DataAuditEntityBase implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 2501304461465253277L;
	private Long ID;
     private String albumCode;
     private String imageCode;
     private String imageName;
     private Long imageSize;
     private String imageType;
     private String title;
     private String description;
     private Timestamp datePosted;
     private String url;
     private String urlThumb;
     private String delete_url;
     private String delete_type;
     @JsonIgnore
 	private transient InputStream imageStream;

    // Constructors


	/** default constructor */
    public Image() {
    }

    
    /** full constructor */
    public Image(String albumCode, String imageCode, String imageName, String imageType, String title, String description, Timestamp datePosted, String url, String urlThumb) {
        this.albumCode = albumCode;
        this.imageCode = imageCode;
        this.imageName = imageName;
        this.imageType = imageType;
        this.title = title;
        this.description = description;
        this.datePosted = datePosted;
        this.url = url;
        this.urlThumb = urlThumb;
    }

   
    // Property accessors
    @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="ID", unique=true, nullable=false)

    public Long getID() {
        return this.ID;
    }
    
    public void setID(Long ID) {
        this.ID = ID;
    }
    
    @Column(name="albumCode", length=10)

    public String getAlbumCode() {
        return this.albumCode;
    }
    
    public void setAlbumCode(String albumCode) {
        this.albumCode = albumCode;
    }
    
    @Column(name="imageCode", length=20)

    public String getImageCode() {
        return this.imageCode;
    }
    
    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
    
    @Column(name="imageName", length=45)

    public String getImageName() {
        return this.imageName;
    }
    
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    /**
	 * @return the imageSize
	 */
    @Column(name="imageSize")
	public Long getImageSize() {
		return imageSize;
	}


	/**
	 * @param imageSize the imageSize to set
	 */
	public void setImageSize(Long imageSize) {
		this.imageSize = imageSize;
	}


	@Column(name="imageType", length=100)

    public String getImageType() {
        return this.imageType;
    }
    
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
    
    @Column(name="title", length=45)

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(name="description")

    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Column(name="datePosted", length=19)

    public Timestamp getDatePosted() {
        return this.datePosted;
    }
    
    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }
    
    @Column(name="url", length=100)

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="urlThumb", length=100)

    public String getUrlThumb() {
        return this.urlThumb;
    }
    
    public void setUrlThumb(String urlThumb) {
        this.urlThumb = urlThumb;
    }
   
	/**
	 * Return image content as stream
	 * 
	 * @return image stream
	 */
	@Transient
    @JsonIgnore
	public InputStream getImageAsStream() {
		return imageStream;
	}


    /**
	 * @param imageStream the imageStream to set
	 */
	public void setImageStream(InputStream imageStream) {
		this.imageStream = imageStream;
	}


	/**
	 * @return the delete_url
	 */
	@Transient
	public String getDelete_url() {
		return delete_url;
	}


	/**
	 * @param delete_url the delete_url to set
	 */
	public void setDelete_url(String delete_url) {
		this.delete_url = delete_url;
	}


	/**
	 * @return the delete_type
	 */
	@Transient
	public String getDelete_type() {
		return delete_type;
	}


	/**
	 * @param delete_type the delete_type to set
	 */
	public void setDelete_type(String delete_type) {
		this.delete_type = delete_type;
	}






}