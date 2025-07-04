package com.java.excelJsonUtility.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.java.excelJsonUtility.dto.GoodsLocationCode;
import com.java.excelJsonUtility.dto.ParentChildDTO;
import com.java.excelJsonUtility.utility.Constants;
import com.java.excelJsonUtility.utility.GenerateJson;
import com.java.excelJsonUtility.utility.ReadSourceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelFileServiceImpl implements ExcelFileService {



    @Autowired
    ReadSourceFile readSourceFile;

    @Autowired
    GenerateJson generateJson;

    @Override
    public void init() {
        try {
            Files.createDirectories(Constants.sourceFolder);
            Files.createDirectories(Constants.generatedFileFolder);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void processFile(MultipartFile file,  String fileType) {
        try {
            Files.copy(file.getInputStream(), Constants.sourceFolder.resolve(file.getOriginalFilename()));
            Path path= Paths.get(Constants.sourceFolder.toString()+ File.separatorChar+file.getOriginalFilename());
            if("goodsLocationCode".equalsIgnoreCase(fileType) || "goodsLocationCode2".equalsIgnoreCase(fileType) || "goodsLocationCode3".equalsIgnoreCase(fileType)) {
                List<GoodsLocationCode> lstGLC = readSourceFile.readGLCDatafromFile(path.toString(),fileType);
                generateJson.createGLCJsonList(lstGLC, Constants.generatedFileFolder,fileType);
            }else if("additionalDocumentCodes".equalsIgnoreCase(fileType) || "statusCodesRequiringAReason".equalsIgnoreCase(fileType) )
            {
                List<ParentChildDTO> lstPCD = readSourceFile.readParentChildDatafromFile(path.toString(),fileType);
                generateJson.createPCJsonList(lstPCD, Constants.generatedFileFolder, fileType);
            }
            delete(Constants.sourceFolder,file.getOriginalFilename());

        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = Constants.generatedFileFolder.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(Path path,String filename) {
        try {
            Path file = path.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Constants.sourceFolder.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(Constants.generatedFileFolder, 1).filter(path -> !path.equals(Constants.generatedFileFolder)).map(Constants.generatedFileFolder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
