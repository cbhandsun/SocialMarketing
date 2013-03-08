package com.socialmarketing.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.socialmarketing.core.exception.ApplicationException;

/**
 * 
 * @author LiuYanLu
 *         <p>
 *         FTP操作工具类
 */

public class FtpUtil {
	
	protected final Logger log = LogManager.getLogger(getClass());

	/**
	 * @$comment ftp客户端对象
	 */
	private FTPClient client;

	/**
	 * 构造函数
	 * @param controlEncoding   传输协议编码，例如UTF-8, GBK等
	 */
	public FtpUtil(String controlEncoding) {
		client = new FTPClient();
		client.setControlEncoding(controlEncoding);
	}

	/**
	 * 登录FTP服务器
	 * 
	 * @param userName
	 *            用户名
	 * @param passWord
	 *            密码
	 * @param server
	 *            服务器地址
	 * @throws Exception
	 */
	public boolean login(String userName, String passWord, String server, boolean passive_mode)
			throws Exception {
		int reply = 230;
		try {
			// 联接服务器
			int hasport = server.lastIndexOf(":");
			String[] servmes;
			if (hasport > 0) {
				servmes = server.split(":");
				client.connect(servmes[0], Integer.parseInt(servmes[1]));
			} else {
				client.connect(server);
			}
			if(passive_mode){
				client.enterLocalPassiveMode();
			}
			
			// 登录
			client.login(userName, passWord);
			// 获得回持码
			reply = client.getReplyCode();

			// 验证回持码,登录失败
			if (!FTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
				return false;
			}

			client.setDefaultTimeout(600000);
			client.setSoTimeout(600000);
			client.setDataTimeout(600000);
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.setBufferSize(500000);
			//client.setReaderThread(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// 连接没有关闭
			if (client.isConnected()) {
				try {
					// 关闭连接
					client.disconnect();
				} catch (IOException e1) {
					e.printStackTrace();
				}
			}
			throw new ApplicationException("登陆FTP失败", new String[] { userName, server });
		}
	}

	/**
	 * @$comment 获得ftp服务器相应目录的文件列表
	 * @param sourceDir FTP目录
	 * @return 相应目录下面的文件列表
	 * @throws IOException
	 */
	public FTPFile[] getFileList(String sourceDir) throws IOException {

		FTPFile[] fileList = null;
		
		// 改变FTP服务器的工作目录
		client.changeWorkingDirectory(sourceDir);
		
		// 获得当前目录的文件列表
		fileList = client.listFiles();
		
		return fileList;
	}

	/**
	 * @$comment 获得ftp服务器相应目录的文件列表
	 * @param sourceDir
	 *            FTP目录
	 * @return 相应目录下面的文件列表
	 * @throws IOException
	 */
	public String[] getFilenameList(String sourceDir) throws IOException {

		String[] str;
		
		// 改变FTP服务器的工作目录
		client.changeWorkingDirectory(sourceDir);

		str = client.listNames(sourceDir);
		
		return str;
	}

	/**
	 * 从FTP服务器取得文件输入流
	 * 
	 * @param SourceFile
	 *            源文件
	 * @throws DownLoadException
	 *             文件下载异常
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 * 
	 * commons-net的FTPClient，在使用public InputStream retrieveFileStream(String remote)
		方法时需要特别注意，在调用这个接口后，一定要手动close掉返回的InputStream，然后再调用completePendingCommand方法，若不是按照这个顺序，则不对，伪代码：
		InputStream is = ftpClient.retrieveFileStream(remote);
		is.close();
		ftpClient.completePendingCommand();
	 */
	public InputStream getFileStream(String SourceFileName) throws IOException,
			InterruptedException {
		InputStream in = null;
		client.noop();
		in = client.retrieveFileStream(SourceFileName);
		// 防止连接远程FTP时响应过慢，等待1秒
		Thread.sleep(1000);
		return in;
	}
	
	public void getFileFromFTPServer(String remoteFileName, String localFileName) throws IOException{
		
		OutputStream output ;
		try{
			output = new FileOutputStream(localFileName);
		}catch(FileNotFoundException e){
			log.error("Can't create local file. "+ localFileName, e);
			throw e;
		}
		
		try {
			boolean result = client.retrieveFile(remoteFileName, output);
			if(!result){
				//如果没有成功拿到则抛出错误
				throw new IOException();
			}
		} catch (IOException e) {
			log.error("Error getting remote file " + remoteFileName + "to locale file "+ localFileName, e);
			throw e;
		}finally{
			try{
				output.close();
			} catch (IOException e1) {
				//ignore
			}
		}
	}
	
	public void sendFileToFTPServer(String remoteFileName, String localFileName) throws IOException{
		InputStream input;
		try {
			input = new FileInputStream(localFileName);
		} catch (FileNotFoundException e) {
			log.error("Can't get local file. "+ localFileName, e);
			throw e;
		}
		
		try {
			client.storeFile(remoteFileName, input);
		} catch (IOException e) {
			log.error("Error send local file " + localFileName + "to remote file "+ remoteFileName, e);
			throw e;
		}finally{
			try{
				input.close();
			}catch(IOException e1){
				//ignore
			}
		}
	}

	/**
	 * 开输出流到ftp
	 * 
	 * @param remote
	 *            服务器端的文件名称
	 * @param local
	 *            本地输入流
	 * @return 操作真值
	 * @throws IOException
	 */
	public OutputStream upFileStream(String remote) throws IOException {
		OutputStream outs;
		outs = client.appendFileStream(remote);
		return outs;
	}

	/**
	 * 修改制定文件的文件名
	 * 
	 * @param oldname
	 *            原文件名
	 * @param newname
	 *            新文件名
	 * @return 操作真值
	 */
	public boolean rename(String oldname, String newname) throws IOException {
		boolean value = false;
		value = client.rename(oldname, newname);
		return value;
	}

	/**
	 * 删除当前操作路径下的文件
	 * 
	 * @param filename
	 *            文件名
	 * @return 操作真值
	 * @throws IOException
	 */
	public boolean delete(String filename) throws IOException {
		boolean value = client.deleteFile(filename);
		return value;
	}

	/**
	 * 创立一个指定文件名的空文件
	 * 
	 * @param filename
	 * @return
	 */
	public boolean create(String filename) throws IOException {
		OutputStream outs = null;
		try {
			boolean creasuc = false;
			outs = client.appendFileStream(filename);
			if (outs != null)
				outs.close();
			return creasuc;
		} finally {
			if (outs != null) {
				try {
					outs.close();
				} catch (Exception e) {
					log.error(e);
				}
				outs = null;
			}
		}
	}

	/**
	 * 退出登陆，关闭连接
	 * 
	 * @throws IOException
	 * 
	 */
	public void logout() throws IOException {
		if (client.isConnected()) {
			try {
				client.logout();
				client.disconnect();
			} catch (IOException e) {
				log.error("FTP退出登录失败", e);
				throw e;
			}
		}
	}

	/**
	 * 检测是否完成文件提取操作
	 * 
	 * @return 操作真值
	 */
	public boolean complete() throws IOException {
		boolean temp;
		temp = client.completePendingCommand();
		return temp;
	}

	/**
	 * @return the client
	 */
	public FTPClient getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(FTPClient client) {
		this.client = client;
	}
}
