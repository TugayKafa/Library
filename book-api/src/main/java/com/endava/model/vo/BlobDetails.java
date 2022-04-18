package com.endava.model.vo;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlobDetails {

    private BlobInfo blobInfo;
    private Blob blob;
    private Storage storage;
}
