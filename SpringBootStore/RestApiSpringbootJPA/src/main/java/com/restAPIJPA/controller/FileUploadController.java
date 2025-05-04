package com.restAPIJPA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restAPIJPA.services.FileUploadHelper;

@RestController
public class FileUploadController {
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@PostMapping("/fileupload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file ){
		
		try {
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getContentType());
			System.out.println(file.getSize());
			System.out.println(file.getName());
			
			if(file.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File not uploaded");
			}
			
			if(!file.getContentType().equals("application/pdf")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File type must be pdf");
			}
			boolean fileUpload = fileUploadHelper.fileUpload(file);
			if(fileUpload) {
//				return ResponseEntity.ok("Uploaded");
				return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/").path(file.getOriginalFilename()).toUriString());
				
			}else {
				return ResponseEntity.ok("Failed to uploaded");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok("Working");
	}
}
