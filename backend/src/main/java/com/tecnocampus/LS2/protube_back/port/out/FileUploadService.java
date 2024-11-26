package com.tecnocampus.LS2.protube_back.port.out;

import java.io.InputStream;

public interface FileUploadService {
    void uploadFile(InputStream inputStream, String fileName);
}
