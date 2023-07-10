package com.marand.json_parser.controllers;

import lombok.RequiredArgsConstructor;

// import com.fasterxml.jackson.databind.ObjectMapper;
import com.marand.json_parser.models.Doctor;
import com.marand.json_parser.services.JSONReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
@RequiredArgsConstructor
public class JSONController {

    @Autowired
    private JSONReaderService jsonReaderService;

    @PostMapping("/readJson")
    public ResponseEntity<Doctor> getData(@RequestParam("data") MultipartFile file) throws IOException {
        System.out.println("data received");
        return ResponseEntity.ok(jsonReaderService.parse(file));
    }
}