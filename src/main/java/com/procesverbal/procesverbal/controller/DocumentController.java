package com.procesverbal.procesverbal.controller;

import com.procesverbal.procesverbal.dto.DocumentDto;
import com.procesverbal.procesverbal.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
public class DocumentController {
    @Autowired
    DocumentService service;
    @PostMapping("/create-new-doc/")
    public void createDocument(@RequestBody DocumentDto documentDto) throws IOException {
      service.createDocument(documentDto);
    }


}
