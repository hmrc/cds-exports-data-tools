package com.java.excelJsonUtility.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.excelJsonUtility.dto.GoodsLocationCode;
import com.java.excelJsonUtility.dto.ParentChildDTO;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class GenerateJson {

    public void createGLCJsonList(List<GoodsLocationCode> list, Path filePath, String fileType) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(list).replace("\u00a0","").replace("\\u0026","&").replace("\\u0027","'");
        writeJsonFile(jsonData,filePath, fileType);
    }

    public void createPCJsonList(List<ParentChildDTO> list, Path filePath, String fileType) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(list).replace("\u00a0","").replace("\\u0026","&").replace("\\u0027","'");
        writeJsonFile(jsonData,filePath, fileType);
    }


    public  void writeJsonFile(String data,Path filePath, String  fileType)
    {
       Path flp= Path.of(filePath.toString()+ File.separatorChar+fileType+".json");
        try {
            Files.writeString(flp, data, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }
    }
}
