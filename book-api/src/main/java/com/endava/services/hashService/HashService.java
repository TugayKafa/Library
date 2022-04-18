package com.endava.services.hashService;

import org.springframework.web.multipart.MultipartFile;

public interface HashService {

    String createHash(MultipartFile multipartFile);
}
