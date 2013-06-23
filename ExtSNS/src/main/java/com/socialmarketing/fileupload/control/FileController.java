/**
 * 
 */
package com.socialmarketing.fileupload.control;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.socialmarketing.service.promo.PromoServiceTest;

/**********************************************************************
 * FILE : FileController.java
 * CREATE DATE : 2013-6-6
 * DESCRIPTION :
 *		
 *      
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 * 1  | 2013-6-6    |  hongtao     |    创建草稿版本
 *---------------------------------------------------------------------              
 ******************************************************************************/
@Controller
@RequestMapping("/upload")
public class FileController {
	private static final Logger log = LoggerFactory.getLogger(PromoServiceTest.class);
    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    /***************************************************
     * URL: /rest/controller/upload 
     * upload(): receives files
     * @param request : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     ****************************************************/
    @RequestMapping(value="/file", method = RequestMethod.POST)
    public @ResponseBody LinkedHashMap<String, LinkedList<FileMeta>> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
 
        //1. build an iterator
         Iterator<String> itr =  request.getFileNames();
         MultipartFile mpf = null;
 
         //2. get each file
         while(itr.hasNext()){
 
             //2.1 get next MultipartFile
             mpf = request.getFile(itr.next());
             log.info("{} uploaded! size: {}",mpf.getOriginalFilename() ,files.size());
             //2.2 if files > 10 remove the first from the list
             if(files.size() >= 10)
                 files.pop();
 
             //2.3 create new fileMeta
             fileMeta = new FileMeta();
             fileMeta.setFileName(mpf.getOriginalFilename());
             fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
             fileMeta.setFileType(mpf.getContentType());
 
             try {
                fileMeta.setBytes(mpf.getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
             //2.4 add to files
             files.add(fileMeta);
         }
        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
         LinkedHashMap<String, LinkedList<FileMeta>> map = new  LinkedHashMap<String, LinkedList<FileMeta>>();
         map.put("files", files);
        return map;
    }
    /***************************************************
     * URL: /rest/controller/get/{value}
     * get(): get file as an attachment
     * @param response : passed by the server
     * @param value : value from the URL
     * @return void
     ****************************************************/
    @RequestMapping(value = "/images/get/{value}", method = RequestMethod.GET)
     public void get(HttpServletResponse response,@PathVariable String value){
         FileMeta getFile = files.get(Integer.parseInt(value));
         try {     
                response.setContentType(getFile.getFileType());
                response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
                FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
         }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
         }
     }
}