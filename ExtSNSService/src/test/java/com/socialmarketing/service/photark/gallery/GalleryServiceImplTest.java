package com.socialmarketing.service.photark.gallery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.socialmarketing.service.NumResourceServiceImpl;

@ContextConfiguration("classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GalleryServiceImplTest {
	private static final Logger logger = LoggerFactory
			.getLogger(NumResourceServiceImpl.class.getName());
	@Autowired
	@Qualifier("galleryService")
	private GalleryService galleryService;

	@Test
	public void addGalleryTest() {
		galleryService.addGallery("public", "public/images");

	}

}
