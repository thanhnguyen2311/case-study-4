package com.example.module_4.controller;

import com.example.module_4.model.ImgHouse;
import com.example.module_4.service.IImgHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping("/img/house")
@PropertySource("classpath:application.properties")
public class ImgHouseController {
    @Autowired
    private IImgHouseService imgHouseService;
    @Value("${upload.path}")
    private String link;
    @Value("${display.path}")
    private String displayLink;

    @GetMapping
    public ResponseEntity<Iterable<ImgHouse>> showAll() {
        Iterable<ImgHouse> imgHouse = imgHouseService.findAll();
        if (!imgHouse.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(imgHouse, HttpStatus.OK);
    }

    private void uploadFile(ImgHouse imgHouse, MultipartFile file){
        if (file != null) {
            String fileName = file.getOriginalFilename();
            try {
                FileCopyUtils.copy(file.getBytes(), new File(link + fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            imgHouse.setImg(displayLink + fileName);
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestPart("img") ImgHouse imgHouse, @RequestPart(value = "file", required = false) MultipartFile file){
        uploadFile(imgHouse, file);
        imgHouseService.save(imgHouse);
        return new ResponseEntity<>("Create Image successfully!", HttpStatus.CREATED);
    }
}
