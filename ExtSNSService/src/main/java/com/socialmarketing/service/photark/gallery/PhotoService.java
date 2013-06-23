package com.socialmarketing.service.photark.gallery;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.socialmarketing.model.photark.Album;
import com.socialmarketing.model.photark.Gallery;
import com.socialmarketing.model.photark.Image;

public interface PhotoService {

	String[] addGallery(String gallery, String path);

	List<Album> getAlbumsByGalleryName(String galleryName);

	List<Album> getAlbums();

	void setRootPath(String path);

	String getGalleryCodeByName(String galleryName);

	String getAlbumCodeByName(String galleryCode, String albumName);

	LinkedList<Image> addImages(String galleryCode, String albumCode, LinkedList<Image> list)
			throws IOException;

	Gallery getGallerByName(String galleryName);

	void delImages(String[] imageCodes) throws IOException;

	void delImage(String imageCode) throws IOException;

	List<Gallery> getGallery();

	List<Album> getAlbumsByGalleryCode(String galleryCode);

	List<Image> getImage(String albumCode) throws IOException;



}
