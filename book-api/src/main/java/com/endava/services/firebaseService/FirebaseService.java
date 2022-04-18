package com.endava.services.firebaseService;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseService {

    String uploadFile(MultipartFile file, String fileName);

}
