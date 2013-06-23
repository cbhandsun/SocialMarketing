/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.socialmarketing.service.photark.album;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.socialmarketing.core.dao.IDao;
import com.socialmarketing.core.services.impl.BaseService;
import com.socialmarketing.model.photark.Album;
import com.socialmarketing.model.photark.Gallery;
import com.socialmarketing.service.NumResourceService;

@Service(value = "albumService")
public class AlbumServiceImpl extends BaseService<Album> implements AlbumService {
	@Autowired
	@Qualifier("albumDao")
	@Override
	public void setBaseDao(IDao<Album> albumDao) {
		dao = albumDao;
	}
	@Autowired
	@Qualifier("numResourceService")
	private NumResourceService numResourceService = null;
	@Override 
	public List<Album> getAlbumsByGalleryCode(String galleryCode) {
		List<Album> list = dao.findAllByField("galleryCode", galleryCode);
		return list;
	}
	@Override
	public Album getAlbumByAlbumName(Gallery gallery,String albumName)
	{
		List<Album> list = dao.findAllByField("galleryCode", gallery.getGalleryCode());
		for (Album album : list) {
			if (album.getAlbumName().equals(albumName))
				return album;
		}
		return null;
	}
	@Override
	public Album addAlbum(Album  album)
	{
		String albumCode = numResourceService.getNextSequence("album", 5);
		album.setAlbumCode(albumCode);
		save(album);
		return album;
	}

	@Override
	public List<Album> getAlbumsByAlbumCode(String albumCode)
	{
		List<Album> list =  dao.findAllByField("albumCode",albumCode);
		return list;
	}
	@Override
	public Album getAlbumByAlbumCode(String albumCode)
	{
		Album album = dao.findByField("albumCode", albumCode);
		return album;
	}
	/* 统一图库里，相册名称唯一
	 * @see com.socialmarketing.service.photark.album.AlbumService#getAlbumCodeByName(java.lang.String, java.lang.String)
	 */
	@Override
	public String getAlbumCodeByName(String galleryCode,String albumName)
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("galleryCode", galleryCode);
		params.put("albumName", albumName);
		List<Album> list = dao.find("from Album where galleryCode =:galleryCode and albumName = :albumName", params);
		Album album = list.get(0);
		return album.getAlbumCode();
	}
	@Override
	public String getAlbumCover(String galleryCode , String albumCode) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("galleryCode", galleryCode);
		params.put("albumCode", albumCode);
		List<Album> list = dao.find("from Album where galleryCode =:gallery and albumCode = :albumCode", params);
		Album album = list.get(0);
		return "";
	}
	
}

