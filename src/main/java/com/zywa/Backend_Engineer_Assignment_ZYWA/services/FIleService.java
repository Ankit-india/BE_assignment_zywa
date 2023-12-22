package com.zywa.Backend_Engineer_Assignment_ZYWA.services;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FIleService {
    void upload(MultipartFile[] excel) throws IOException;
}
