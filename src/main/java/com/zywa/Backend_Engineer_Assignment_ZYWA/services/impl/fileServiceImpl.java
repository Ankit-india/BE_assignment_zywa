package com.zywa.Backend_Engineer_Assignment_ZYWA.services.impl;

import com.zywa.Backend_Engineer_Assignment_ZYWA.services.FIleService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class fileServiceImpl implements FIleService {

    @Value("${project.data}")
    private String path;

    @Override
    public void upload(MultipartFile[] excelList)
        throws IOException {
        String folderName = path;
        for (MultipartFile image : excelList) {
            String imgName = image.getOriginalFilename();
            String filePath = path + File.separator + imgName;
            File f = new File(path);

            if (!f.exists()) {
                f.mkdir();
            }
            Files.copy(image.getInputStream(), Paths.get(filePath));
        }
        path = "data";
    }
}
