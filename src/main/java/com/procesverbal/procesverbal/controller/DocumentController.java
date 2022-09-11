package com.procesverbal.procesverbal.controller;

import com.procesverbal.procesverbal.dto.DocumentDto;
import com.procesverbal.procesverbal.entities.Document;
import com.procesverbal.procesverbal.helper.Functions;
import com.procesverbal.procesverbal.services.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    DocumentService service;
    @Autowired
    Functions functions;
    @PostMapping("/create-new-doc/")
    public ResponseEntity<?> createDocument(@RequestBody DocumentDto documentDto, HttpServletRequest request) throws Exception {

        String  path =service.createDocument(documentDto);
        Resource resource=functions.loadFileAsResource(path);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.error("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @GetMapping("/download/{title}")

    public ResponseEntity<?> download(@PathVariable String title , HttpServletRequest request) throws Exception {
        String path="C:/proces-verbal-document/"+title+"/";
        String pathFile= path+"/"+title + ".docx";
        Resource resource=functions.loadFileAsResource(pathFile);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.error("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

    @GetMapping("/")
    public ResponseEntity<List<String>> findAll() {
        String path="C:/proces-verbal-document/";
        List<String> files =Functions.listFilesForFolder(new File(path));

    return new ResponseEntity(files, HttpStatus.OK);

    }
}
