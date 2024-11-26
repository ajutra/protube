package com.tecnocampus.LS2.protube_back.adapter.out.persistence;

import com.tecnocampus.LS2.protube_back.port.out.FileUploadService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${pro_tube.store.dir}")
    private String uploadDir;

    @Override
    public void uploadFile(InputStream inputStream, String fileName) {
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            Path path = Paths.get(uploadDir + "/" + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }
}

