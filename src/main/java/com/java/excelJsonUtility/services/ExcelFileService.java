package com.java.excelJsonUtility.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface ExcelFileService {

    public void init();

    public void processFile(MultipartFile file, String fileType);

    public Resource load(String filename);

    public boolean delete(Path path,String filename);

    public void deleteAll();

    public Stream<Path> loadAll();
}
