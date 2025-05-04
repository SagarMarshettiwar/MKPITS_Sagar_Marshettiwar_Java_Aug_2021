package com.restAPIJPA.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	//public final String UploadDir="D:\\springbootprojects\\RestApiSpringbootJPA\\src\\main\\resources\\static\\images";
	public final String UploadDir=new ClassPathResource("static/images/").getFile().getAbsolutePath();
	
	public FileUploadHelper() throws IOException{
		
	}

	public boolean fileUpload(MultipartFile f) {
		boolean b=false;
		try{
			/*
			 * InputStream file=f.getInputStream(); byte[] data=new byte[file.available()];
			 * file.read(data);
			 * 
			 * FileOutputStream fos=new
			 * FileOutputStream(UploadDir+File.separator+f.getOriginalFilename());
			 * fos.write(data); fos.flush(); fos.close();
			 */
			
			Files.copy(f.getInputStream(),Paths.get(UploadDir+File.separator+f.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			b=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return b;
		
	}
}
