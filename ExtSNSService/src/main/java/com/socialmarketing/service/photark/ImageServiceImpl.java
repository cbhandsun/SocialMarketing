package com.socialmarketing.service.photark;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.tasks.io.InputStreamImageSource;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.model.photark.Image;
import com.socialmarketing.service.NumResourceService;
import com.socialmarketing.util.CopyInputStream;

@Service(value = "imageService")
public class ImageServiceImpl extends BaseService<Image> implements
		ImageService {

	IDao<Image> imageDao = null;
	@Autowired
	@Qualifier("numResourceService")
	private NumResourceService numResourceService = null;

	@Autowired
	@Qualifier("imageDao")
	@Override
	public void setBaseDao(IDao<Image> imageDao) {
		this.imageDao = imageDao;
		dao = imageDao;
	}

	@Override
	public List<Image> getImagesByAlbumCode(String albumCode) {
		List<Image> list = imageDao.findAllByField("albumCode", albumCode);
		return list;
	}

	@Override
	public  LinkedList<Image> addImages(String albumCode, String rootPath,
			LinkedList<Image> list) throws IOException {
		String relUrl = "";
		for (Image image : list) {
			String imageCode = "img"
					+ numResourceService.getNextSequence("image", 10);
			image.setAlbumCode(albumCode);
			image.setImageCode(imageCode);
			relUrl = image.getUrl();
			String imagePath = rootPath + "\\" + relUrl + "\\" + imageCode + "."
					+ image.getImageType();
			image.setUrl(relUrl + "/" + imageCode + "." + image.getImageType());
			String thumbUrl = relUrl + "/" + imageCode + "_260_180."
					+ image.getImageType();
			image.setUrlThumb(thumbUrl);
			File imageFile = FileUtils.getFile(imagePath);
			InputStream inputStream = image.getImageAsStream();
			
			
			if (!imageFile.exists()) {
				FileUtils.copyInputStreamToFile(inputStream,
						imageFile);
			}
			// 生成缩略图
			imageFile = FileUtils.getFile(imagePath);
			resize(imageFile, rootPath + "\\" + thumbUrl,image.getImageType());
			
		}
		imageDao.saveObjects(list);
		return list;
	}

	@Override
	public void deleteImages(String rootPath, String[] imageCodes) {
		String imageUrl = "";
		for (String param : imageCodes) {
			Image image = dao.findByField("imageCode", param);
			imageUrl = rootPath + image.getUrl();
			File imageFile = FileUtils.getFile(imageUrl);
			if (imageFile.exists()) {
				imageFile.delete();
				dao.remove(image);
			}

		}
	}

	@Override
	public void deleteImage(String rootPath, String imageCode) {
		Image image = dao.findByField("imageCode", imageCode);
		String imageUrl = rootPath + image.getUrl();
		String thumbImageUrl = rootPath + image.getUrlThumb();
		File imageFile = FileUtils.getFile(imageUrl);
		File thumbImage = FileUtils.getFile(thumbImageUrl);
		if (imageFile.exists()) {
			imageFile.delete();
			dao.remove(image);
		}
		if (thumbImage.exists())
			thumbImage.delete();

	}

	public void resize(File imageFile, String thumbUrl,String type)
			throws IOException {
		
		Thumbnails.of(imageFile).size(180, 260).outputFormat(type).toFile(thumbUrl);

	}
}
