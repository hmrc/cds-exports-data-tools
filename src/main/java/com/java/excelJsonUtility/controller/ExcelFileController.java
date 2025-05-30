package com.java.excelJsonUtility.controller;

import com.java.excelJsonUtility.dto.FileData;
import com.java.excelJsonUtility.services.ExcelFileService;
import com.java.excelJsonUtility.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ExcelFileController {

    @Autowired
    ExcelFileService fileService;

    @GetMapping("/")
    public String homepage() {
        return "redirect:/listFiles";
    }

    @GetMapping("/files/newfile")
    public String newFile(Model model) {
        return "upload_excel";
    }

    @PostMapping("/files/upload")
    public String uploadExcel(Model model, @RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
        String message = "";

        try {
            if (!"select".equalsIgnoreCase(fileType) && file.getOriginalFilename().endsWith("xlsx")) {
                fileService.processFile(file, fileType);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
            }else {
                if("select".equalsIgnoreCase(fileType))message = "Please select the file type " ;
                else message = "Please upload only Excel(.xlsx) files " ;
            }

            model.addAttribute("message", message);
            model.addAttribute("fileType", fileType);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            model.addAttribute("message", message);
            model.addAttribute("fileType", fileType);
        }

        return "upload_excel";
    }

    @GetMapping("/listFiles")
    public String showGeneratedFiles(Model model) {
        List<FileData> fileInfos = fileService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(ExcelFileController.class, "getFile", path.getFileName().toString()).build().toString();

            return new FileData(filename, url);
        }).collect(Collectors.toList());

        model.addAttribute("files", fileInfos);

        return "listFiles";
    }
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/files/delete/{filename:.+}")
    public String deleteFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
        try {
            boolean isExistingFile = fileService.delete(Constants.generatedFileFolder,filename);

            if (isExistingFile) {
                redirectAttributes.addFlashAttribute("message", "Delete the file successfully: " + filename);
            } else {
                redirectAttributes.addFlashAttribute("message", "The file does not exist!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "Could not delete the file: " + filename + ". Error: " + e.getMessage());
        }

        return "redirect:/listFiles";
    }

}
