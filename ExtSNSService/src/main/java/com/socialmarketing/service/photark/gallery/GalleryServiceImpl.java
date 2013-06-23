package com.socialmarketing.service.photark.gallery;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.model.photark.Gallery;
import com.socialmarketing.service.NumResourceService;

@Service(value = "galleryService")
public class GalleryServiceImpl extends BaseService<Gallery> implements
		GalleryService {
	Logger logger = LoggerFactory.getLogger(GalleryServiceImpl.class);
	private String rootPath = FileUtils.getUserDirectoryPath();
	IDao<Gallery> galleryDao = null;
	@Override
	public void setRootPath(String path) {
		rootPath = path;
	}

	@Autowired
	@Qualifier("galleryDao")
	@Override
	public void setBaseDao(IDao<Gallery> galleryDao) {
		this.galleryDao = galleryDao;
		dao = galleryDao;

	}

	@Autowired
	@Qualifier("numResourceService")
	private NumResourceService numResourceService = null;

	@Override
	public Gallery addGallery(String galleryName, String relativePath) {
		logger.info(
				"execture method:addGallery;rootPath is {},relativePath is {},gallery is {}",
				rootPath, galleryName, relativePath);
		Gallery gallery = null;
		if (!hasGallery(galleryName)) {
			File directory = new File(rootPath);
			String galleryCode = numResourceService.getNextSequence("gallery", 5);
			gallery =  new Gallery();
			gallery.setGalleryCode(galleryCode);
			gallery.setGalleryName(galleryName);
			gallery.setLocation(relativePath);
			dao.save(gallery);
			File file = FileUtils.getFile(directory, relativePath);
			if (!file.exists())
				file.mkdirs();
		}
		gallery = getGalleryByName(galleryName);
		return gallery;
	}

	@Override
	public List<Gallery> getAllGallery() {
		// TODO Auto-generated method stub
		List<Gallery> list = (List<Gallery>) dao.findAll();
		for (Gallery gallery : list) {
			gallery.setRealPath(rootPath + gallery.getLocation());
		}
		return list;
	}

	@Override
	public Gallery getGalleryByName(String galleryName) {
		Gallery gallery = galleryDao.findByField("galleryName", galleryName);
		return gallery;
	}

	@Override
	public boolean hasGallery(String galleryName) {
		boolean isExist = false;
		List<Gallery> list = galleryDao.findAllByField("galleryName",
				galleryName);
		if (list != null && list.size() > 0)
			isExist = true;
		return isExist;
	}

	@Override
	public String getGalleryNameByCode(String galleryCode) {
		Gallery gallery = galleryDao.findByField("galleryCode", galleryCode);
		String galleryName = gallery.getGalleryName();
		return galleryName;
	}

	@Override
	public String getGalleryCodeByName(String galleryName) {
		Gallery gallery = galleryDao.findByField("galleryName", galleryName);
		String galleryCode = gallery.getGalleryCode();
		return galleryCode;
	}

	@Override
	public String getGalleryPathByCode(String galleryCode) {
		Gallery gallery = galleryDao.findByField("galleryCode", galleryCode);
		String relativePath = gallery.getLocation();
		return rootPath + relativePath;
	}

	@Override
	public String getGalleryPathByName(String galleryName) {
		Gallery gallery = galleryDao.findByField("galleryName", galleryName);
		String relativePath = gallery.getLocation();
		return rootPath + relativePath;
	}

	private void deleteGallery(String galleryCode, boolean isDelSubDirs)
			throws IOException {
		// Map<String,String> params = new HashMap<String,String>();
		// params.put("galleryCode", galleryCode);
		// galleryDao.updateAllObjects("delete from Gallery where galleryCode = ：galleryCode",
		// params);
		Gallery gallery = galleryDao.findByField("galleryCode", galleryCode);
		String relativePath = gallery.getLocation();
		String path = rootPath + relativePath;
		if (isDelSubDirs) {
			FileUtils.deleteDirectory(FileUtils.getFile(path));
		}
	}

	@Override
	public void deleteGalleryWithSubdirs(String galleryCode) throws IOException {
		logger.debug("deleteGalleryWithSubdirs 删除图库及图库下的文件");
		deleteGallery(galleryCode, true);
	}

	@Override
	public void deleteGalleryWithoutSubdirs(String galleryCode)
			throws IOException {
		logger.debug("deleteGalleryWithoutSubdirs 删除图库但不删除图库下的文件");
		deleteGallery(galleryCode, false);
	}

}
