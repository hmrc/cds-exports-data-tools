package com.java.excelJsonUtility.utility;

import com.java.excelJsonUtility.dto.GoodsLocationCode;
import com.java.excelJsonUtility.dto.ParentChildDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadSourceFile {
    protected static final Logger logger = LogManager.getLogger("ReadSourceFile");


    public List<GoodsLocationCode> readGLCDatafromFile(String fileLocation, String fileType) throws IOException
    {
        List<GoodsLocationCode> lstGLC = new ArrayList<GoodsLocationCode>();
        if(fileLocation.endsWith("xlsx"))
            lstGLC= readDataFromExcel( fileLocation, fileType);
        else if (fileLocation.endsWith("ods")) {
           // lstGLC= readDataFromOds( fileLocation);
        }
        logger.info("Total number of records loaded are ::::::  "+lstGLC.size());
        logger.info("from file ::::::  "+new File(fileLocation).getName());
        return lstGLC;
    }

    public  List<GoodsLocationCode> readDataFromExcel(String fileLocation, String fileType)
            throws IOException {
        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<GoodsLocationCode> lstGLC = new ArrayList<GoodsLocationCode>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int n = 1; n < sheet.getPhysicalNumberOfRows(); n++) {
            Row row = sheet.getRow(n);
            GoodsLocationCode glc = new GoodsLocationCode();
            int i = row.getFirstCellNum();
        if(dataFormatter.formatCellValue(row.getCell(i)).isBlank()){
            break;
        }
            if("goodsLocationCode".equalsIgnoreCase(fileType)) {
                glc.setEn(dataFormatter.formatCellValue(row.getCell(i)) + " / " + dataFormatter.formatCellValue(row.getCell(++i)));
                glc.setCyVal(glc.getEn());
            } else if("goodsLocationCode2".equalsIgnoreCase(fileType)) {
                glc.setEn(dataFormatter.formatCellValue(row.getCell(i)));
                glc.setCyVal(glc.getEn());
            }else if("goodsLocationCode3".equalsIgnoreCase(fileType)) {
                glc.setEn(dataFormatter.formatCellValue(row.getCell(i)));
                glc.setCyVal(dataFormatter.formatCellValue(row.getCell(++i)));
            }
                glc.setCode(dataFormatter.formatCellValue(row.getCell(++i)));
            lstGLC.add(glc);
        }
        return lstGLC;
    }
/// ///////////////////////////////////////////////////////////////
///
///
///

public List<ParentChildDTO> readParentChildDatafromFile(String fileLocation , String fileType) throws IOException
    {
        List<ParentChildDTO> lstPCD = new ArrayList<ParentChildDTO>();
        if(fileLocation.endsWith("xlsx"))
            lstPCD= readPCDataFromExcel( fileLocation, fileType);
        else if (fileLocation.endsWith("ods")) {

        }
        logger.info("Total number of records loaded are ::::::  "+lstPCD.size());
        logger.info("from file ::::::  "+new File(fileLocation).getName());
        return lstPCD;
    }

    public  List<ParentChildDTO> readPCDataFromExcel(String fileLocation , String fileType)
            throws IOException {
        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<ParentChildDTO> lstPCd = new ArrayList<ParentChildDTO>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int n = 1; n < sheet.getPhysicalNumberOfRows(); n++) {
            Row row = sheet.getRow(n);
            ParentChildDTO pcd = new ParentChildDTO();
            int i = row.getFirstCellNum();
            if(dataFormatter.formatCellValue(row.getCell(i)).isBlank()){
                break;
            }
            pcd.setParentCode(dataFormatter.formatCellValue(row.getCell(i)));
            if("additionalDocumentCodes".equalsIgnoreCase(fileType)) {
                pcd.setChildCodes(List.of("DocumentCodesRequiringAReason"));
            } else if ("statusCodesRequiringAReason".equalsIgnoreCase(fileType)) {
                pcd.setChildCodes(List.of("StatusCodesRequiringAReason"));
            }

            lstPCd.add(pcd);
        }
        return lstPCd;
    }



}
