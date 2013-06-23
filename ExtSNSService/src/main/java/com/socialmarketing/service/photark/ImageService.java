package com.socialmarketing.service.photark;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.socialmarketing.core.services.IBaseService;
import com.socialmarketing.model.photark.Image;

public interface ImageService extends IBaseService<Image>{

	List<Image> getImagesByAlbumCode(String albumCode);

	List<Image> addImages(String albumCode, String rootPath, LinkedList<Image> list) throws IOException;

	void deleteImages(String rootPath, String[] imageCodes);

	void deleteImage(String rootPath, String imageCode);

}
