package com.socialmarketing.service.photark.album;

import java.util.List;

import com.socialmarketing.core.services.IBaseService;
import com.socialmarketing.model.photark.Album;
import com.socialmarketing.model.photark.Gallery;

public interface AlbumService extends IBaseService<Album>{

	List<Album> getAlbumsByGalleryCode(String galleryCode);

	List<Album> getAlbumsByAlbumCode(String albumCode);

	Album getAlbumByAlbumCode(String albumCode);


	Album getAlbumByAlbumName(Gallery gallery, String albumName);

	Album addAlbum(Album album);

	String getAlbumCover(String galleryCode, String albumCode);

	String getAlbumCodeByName(String galleryCode, String albumName);


}
