package com.zywa.Backend_Engineer_Assignment_ZYWA.controller;

import com.zywa.Backend_Engineer_Assignment_ZYWA.dto.CardStatusQueryDto;
import com.zywa.Backend_Engineer_Assignment_ZYWA.services.CardStatusService;
import com.zywa.Backend_Engineer_Assignment_ZYWA.services.FIleService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class CardStatusController {

    @Autowired CardStatusService cardStatusService;

    @Autowired FIleService fileService;

    @GetMapping("/get_card_status")
    public ResponseEntity<?> getCardStatus(@RequestBody CardStatusQueryDto cardStatusQueryDto) {
        String response = cardStatusService.getCardStatus(cardStatusQueryDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uplaod(@RequestParam("files") MultipartFile[] excelList) {
        try {
            fileService.upload(excelList);
            cardStatusService.processUploadedFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("File data Uploaded successfully");
    }
}
