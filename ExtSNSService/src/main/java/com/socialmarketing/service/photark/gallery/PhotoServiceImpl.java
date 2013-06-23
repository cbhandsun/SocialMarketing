package com.socialmarketing.service.photark.gallery;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.model.photark.Album;
import com.socialmarketing.model.photark.Gallery;
import com.socialmarketing.model.photark.Image;
import com.socialmarketing.service.NumResourceService;
import com.socialmarketing.service.photark.ImageServiceImpl;
import com.socialmarketing.service.photark.album.AlbumService;
@Service(value="photoService")
public class PhotoServiceImpl implements PhotoService {
	@Autowired
	@Qualifier("numResourceService")
	private NumResourceService numResourceService = null;
	private String rootPath = FileUtils.getUserDirectoryPath();
	@Override
	public void setRootPath(String path) {
		this.rootPath = path;
		galleryService.setRootPath(path);
	}
	@Autowired
	@Qualifier("imageService")
	private ImageServiceImpl imageService = null;
	@Autowired
	@Qualifier("albumService")
	private AlbumService albumService = null;
	@Autowired
	@Qualifier("galleryService")
	private GalleryService galleryService = null;
	/* 增加库并增加默认分类，返回galleryCode 和 albumCode
	 * @see com.socialmarketing.service.photark.gallery.PhotoService#addGallery(java.lang.String, java.lang.String)
	 */
	@Override
	public String[] addGallery(String gallery, String relativePath) {
		String[] strs = new String[2];
		Gallery tmp = galleryService.addGallery(gallery, relativePath);
		strs[0] = tmp.getGalleryCode();
		Album album = new Album();
		album.setAlbumName("temp");
		album.setAlbumType("未分类");
		album.setGalleryCode(tmp.getGalleryCode());
		album.setLocation(relativePath);
		album = albumService.addAlbum(album);
		strs[1] = album.getAlbumCode();
		return strs;
	}
	@Override
	public List<Gallery> getGallery()
	{
		List<Gallery> galleryList = galleryService.getAllGallery();
		return galleryList;
	}

	@Override
	public List<Album> getAlbumsByGalleryName(String galleryName) {
		String galleryCode = galleryService.getGalleryCodeByName(galleryName);
		List<Album> list = albumService.getAlbumsByAlbumCode(galleryCode);
		return list;
	}
	@Override
	public List<Album> getAlbumsByGalleryCode(String galleryCode) {
		List<Album> list = albumService.getAlbumsByGalleryCode(galleryCode);
		return list;
	}

	@Override
	public List<Album> getAlbums() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getGalleryCodeByName(String galleryName) {
		return galleryService.getGalleryCodeByName(galleryName);
	}
	@Override
	public Gallery getGallerByName(String galleryName) {
		return galleryService.getGalleryByName(galleryName);
	}
	@Override
	public String getAlbumCodeByName(String galleryCode,String albumName) {
		return albumService.getAlbumCodeByName(galleryCode, albumName);
	}
	@Override
	public LinkedList<Image> addImages(String galleryCode,String albumCode, LinkedList<Image> list) throws IOException {
		LinkedList<Image> retList = imageService.addImages(albumCode,rootPath, list);
		return retList;
		
	}
	@Override
	public void delImages(String[] imageCodes) throws IOException {
		imageService.deleteImages(rootPath, imageCodes);
		
	}
	@Override
	public void delImage(String imageCode) throws IOException {
		imageService.deleteImage(rootPath, imageCode);
		
	}
	@Override
	public List<Image> getImage(String albumCode) throws IOException {
		return imageService.getImagesByAlbumCode(albumCode);
		
	}
}
