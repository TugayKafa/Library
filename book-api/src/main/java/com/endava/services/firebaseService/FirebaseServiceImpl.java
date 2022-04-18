package com.endava.services.firebaseService;

import com.endava.exception.FirebaseServiceException;
import com.endava.model.vo.BlobDetails;
import com.endava.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.commons.io.IOUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.endava.util.Constants.FIREBASE_BUCKET_NAME;
import static com.endava.util.Constants.FIREBASE_CONTENT_TYPE;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private final Environment environment;

    public FirebaseServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        try {
            BlobDetails blobDetails = getBlobDetails(fileName);
            if (blobDetails.getBlob() == null) {
                blobDetails.getStorage().create(blobDetails.getBlobInfo(), file.getBytes());
            }
            return String.format(Constants.DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new FirebaseServiceException(exception.getMessage());
        }
    }

    private BlobDetails getBlobDetails(String fileName) throws Exception {
        BlobId blobId = BlobId.of(FIREBASE_BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(FIREBASE_CONTENT_TYPE).build();
        Credentials credentials = GoogleCredentials.fromStream(createFirebaseCredential());
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(blobId);
        return new BlobDetails(blobInfo, blob, storage);
    }

    private InputStream createFirebaseCredential() throws Exception {
        FirebaseCredential firebaseCredential = new FirebaseCredential();
        //private key
        String privateKey = environment.getRequiredProperty("FIREBASE_PRIVATE_KEY").replace("\\n", "\n");

        firebaseCredential.setType(environment.getRequiredProperty("FIREBASE_TYPE"));
        firebaseCredential.setProject_id(environment.getRequiredProperty("FIREBASE_PROJECT_ID"));
        firebaseCredential.setPrivate_key_id("FIREBASE_PRIVATE_KEY_ID");
        firebaseCredential.setPrivate_key(privateKey);
        firebaseCredential.setClient_email(environment.getRequiredProperty("FIREBASE_CLIENT_EMAIL"));
        firebaseCredential.setClient_id(environment.getRequiredProperty("FIREBASE_CLIENT_ID"));
        firebaseCredential.setAuth_uri(environment.getRequiredProperty("FIREBASE_AUTH_URI"));
        firebaseCredential.setToken_uri(environment.getRequiredProperty("FIREBASE_TOKEN_URI"));
        firebaseCredential.setAuth_provider_x509_cert_url(environment.getRequiredProperty("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
        firebaseCredential.setClient_x509_cert_url(environment.getRequiredProperty("FIREBASE_CLIENT_X509_CERT_URL"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(firebaseCredential);

        //convert jsonString string to InputStream using Apache Commons
        return IOUtils.toInputStream(jsonString);
    }
}
