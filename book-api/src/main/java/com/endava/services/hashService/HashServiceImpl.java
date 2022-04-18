package com.endava.services.hashService;

import com.endava.exception.EmptyCoverImageException;
import com.endava.exception.SomethingWentWrongException;
import com.endava.repostitory.BookRepository;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class HashServiceImpl implements HashService {

    private final BookRepository bookRepository;

    public HashServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public String createHash(MultipartFile multipartFile) {
        Hash hash;
        try {
            hash = generateHash(multipartFile);
        } catch (IOException exception) {
            throw new SomethingWentWrongException(exception.getMessage());
        } catch (IllegalArgumentException exception) {
            throw new EmptyCoverImageException();
        }
        return hash.getHashValue().toString();
    }

    private Hash generateHash(MultipartFile multipartFile) throws IOException, IllegalArgumentException {
        byte[] bytes = multipartFile.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        HashingAlgorithm hashingAlgorithm = new PerceptiveHash(32);
        return hashingAlgorithm.hash(bufferedImage);
    }
}
