/**
 * 
 */
package com.socialmarketing.control.photark;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.socialmarketing.model.photark.Album;
import com.socialmarketing.model.photark.Gallery;
import com.socialmarketing.model.photark.Image;
import com.socialmarketing.service.photark.gallery.PhotoService;
import com.socialmarketing.util.DateUtil;

/**********************************************************************
 * FILE : PhotoControl.java CREATE DATE : 2013-6-9 DESCRIPTION :
 * 
 * 
 * CHANGE HISTORY LOG
 * --------------------------------------------------------------------- NO.|
 * DATE | NAME | REASON | DESCRIPTION
 * --------------------------------------------------------------------- 1 |
 * 2013-6-9 | hongtao | 创建草稿版本
 * ---------------------------------------------------------------------
 ******************************************************************************/
@Controller
public class PhotoControl {
	private static final Logger log = LoggerFactory
			.getLogger(PhotoControl.class);
	@Autowired
	@Qualifier("photoService")
	PhotoService photoService = null;

	Image image = null;

	@RequestMapping(value = "/upload/images", method = RequestMethod.POST)
	public @ResponseBody
	LinkedHashMap<String, LinkedList<Image>> upload(
			MultipartHttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LinkedList<Image> files = new LinkedList<Image>();
		String rootPath = request.getSession().getServletContext()
				.getRealPath("\\"); 
		//String rootPath = FileUtils.getUserDirectoryPath();
		String relativePath = ".\\gallery\\public\\";
		// 1. build an iterator
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;

		String currDate = DateUtil.getCurrentDateAsString();
		String firstDate = DateUtil.getFirstDayOfMonth();
		String yyMMDate = DateUtil
				.getCurrentDateAsString(DateUtil.FORMAT_DATE_YYYYMM);
		String galleryCode;
		String albumCode;
		Gallery gallery = photoService.getGallerByName(yyMMDate);

		if (gallery == null || currDate.equals(firstDate)) {
			photoService.setRootPath(rootPath);
			relativePath = relativePath + yyMMDate;
			String[] strs = photoService.addGallery(yyMMDate, relativePath);
			galleryCode = strs[0];
			albumCode = strs[1];
		} else {
			photoService.setRootPath(rootPath);
			relativePath = gallery.getLocation();
			galleryCode = gallery.getGalleryCode();
			albumCode = photoService.getAlbumCodeByName(galleryCode, "temp");
		}

		// 2. get each file
		while (itr.hasNext()) {

			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			log.info("{} uploaded! size: {}", mpf.getOriginalFilename(),
					files.size());
			// 2.2 if files > 10 remove the first from the list
			if (files.size() >= 10)
				files.pop();

			// 2.3 create new fileMeta
			image = new Image();

			image.setAlbumCode(albumCode);
			image.setImageName(mpf.getOriginalFilename());
			image.setImageSize(mpf.getSize()); // 单位byte
			String[] strs = StringUtils.split(mpf.getContentType(), "/");
			image.setImageType(strs[1]);
			image.setUrl(relativePath);
			try {
				image.setImageStream(mpf.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 2.4 add to files
			files.add(image);
		}
		// result will be like this
		// [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
		LinkedList<Image> imageList = photoService.addImages(galleryCode,
				albumCode, files);
		for (Image image : imageList) {
			image.setDelete_type("DELETE");
			image.setDelete_url("./upload/delImage/" + image.getImageCode());
		}
		LinkedHashMap<String, LinkedList<Image>> map = new LinkedHashMap<String, LinkedList<Image>>();
		map.put("files", imageList);
		return map;
	}

	@RequestMapping(value = "/upload/delImage/{imageCode}", method = RequestMethod.DELETE)
	public void delImage(@PathVariable String imageCode) throws IOException {
		photoService.delImage(imageCode);
	}

	@RequestMapping(value = "/images/list/{albumCode}", method = RequestMethod.DELETE)
	public void getImages(@PathVariable String imageCode) throws IOException {
		photoService.delImage(imageCode);
	}

	@RequestMapping(value = "/gallery/list", method = RequestMethod.GET)
	public ModelAndView getGallery() throws IOException {
		List<Gallery> galleryList = photoService.getGallery();
		ModelAndView mav = new ModelAndView("photo/GalleryList", "galleryList",
				galleryList);
		return mav;
	}
	@RequestMapping(value = "/album/list/{galleryCode}", method = RequestMethod.GET)
	public ModelAndView getAlbumByGallery(@PathVariable String galleryCode) throws IOException {
		List<Album> albumList = photoService.getAlbumsByGalleryCode(galleryCode);
		ModelAndView mav = new ModelAndView("photo/AlbumList", "albumList",
				albumList);
		return mav;
	}
	@RequestMapping(value = "/image/list/{albumCode}", method = RequestMethod.GET)
	public ModelAndView getImagesByalbum(@PathVariable String albumCode) throws IOException {
		List<Image> imageList = photoService.getImage(albumCode);
		ModelAndView mav = new ModelAndView("photo/ImageList", "imageList",
				imageList);
		return mav;
	}
}
