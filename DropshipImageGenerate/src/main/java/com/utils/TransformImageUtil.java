/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.config.Configs;
import com.controller.DownloadManager;
import com.controller.ImgurManager;
import com.models.amazon.DataStore;
import com.models.amazon.ProductAmz;
import com.models.imgur.GenerateModel;
import com.models.imgur.ImgUrImage;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author PhanDuy
 */
public class TransformImageUtil {
    
    public static final String SHEET_DATA = "Data";
    
    public static void transformImageInProductVPS(String filePath) throws FileNotFoundException, IOException, InvalidFormatException {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        String name = file.getName().split(Pattern.quote("."))[0];
        System.out.println("Name: " + name);
//        if (true) return;

        String parent = file.getParent();
        System.out.println("Parent: " + parent);
        file = new File(parent + "/" + name);
        if (!file.exists()) {
            file.mkdir();
        }
        
        String localImageFolder = file.getPath() + "/";
        System.out.println("LocalFodler: " + localImageFolder);
        String vpsImageFolder = "http://" + Configs.vpsIp + "/" + name + "/";
        System.out.println("vpsImageFolder: " + vpsImageFolder);
        
        FileInputStream fis = new FileInputStream(filePath);
        try (Workbook workbook = WorkbookFactory.create(fis)) {
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();

                if (sh.getSheetName().equals("Template")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            Row fieldnameRow = sheet.getRow(2);
            int cellMax = fieldnameRow.getLastCellNum();

            int i = 3;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                ProductAmz productAmz = new ProductAmz();

                DataFormatter formatter = new DataFormatter();
                productAmz.setExternal_product_id(CodeUtils.genRandomProductId());

                for (int j = 1; j < cellMax; j++) {
                    String value = formatter.formatCellValue(fieldRow.getCell(j));
                    productAmz.setValueForAFiled(formatter.formatCellValue(fieldnameRow.getCell(j)), value);
                    
                }
                
                if (vpsImageFolder != null) {
                    productAmz.updateImageUrlVps(vpsImageFolder);
                    DownloadManager.getInstance().downloadImageAndUpdate(productAmz, localImageFolder);
                }

                for (int j = 0; j < cellMax; j++) {
                    Cell fieldCell = fieldnameRow.getCell(j);
                    String fieldName = fieldCell.getStringCellValue().trim();
                    if (fieldName.contains("image_url")) {
                        Cell cell = fieldRow.getCell(j);
                        if (cell != null) {
                            String value = productAmz.getValueForCell(fieldName);
                            if (value != null) {
                                cell.setCellValue(value);
                            }
                        }
                    }
                }

                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();
            
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(parent + "/" + name + "_new.xlsx");
                workbook.write(fileOut);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }
                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
    public static void downloadImagesInProduct(String filePath, String localImageFolder,String name, String parentFolder) throws FileNotFoundException, IOException, InvalidFormatException {
        DownloadManager.getInstance().clearData();
        FileInputStream fis = new FileInputStream(filePath);
        try (Workbook workbook = WorkbookFactory.create(fis)) {
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();

                if (sh.getSheetName().equals("Template")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            Row fieldnameRow = sheet.getRow(2);
            int cellMax = fieldnameRow.getLastCellNum();

            int i = 3;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                ProductAmz productAmz = new ProductAmz();

                DataFormatter formatter = new DataFormatter();
                productAmz.setExternal_product_id(CodeUtils.genRandomProductId());

                for (int j = 1; j < cellMax; j++) {
                    String value = formatter.formatCellValue(fieldRow.getCell(j));
                    productAmz.setValueForAFiled(formatter.formatCellValue(fieldnameRow.getCell(j)), value);
                    
                }
                productAmz.updateImageUrlImgUr();
                DownloadManager.getInstance().downloadImageAndUpdate(productAmz, localImageFolder);
                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();
            workbook.close();
            
            saveLinkDownload(
                        DownloadManager.getInstance().mapUrl,
                        parentFolder + "/" + name + "_map_link.xlsx"
                );
        }
    }
    
    public static void transformImgurToProductsFile(String folderPath, ArrayList<ImgUrImage> listImages, File[] listFiles) throws FileNotFoundException, IOException, InvalidFormatException {
        HashMap<String, ImgUrImage> imageMap = convertListImgUrs(listImages);
        try (Workbook workbook = new XSSFWorkbook()) {
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet(SHEET_DATA);
            
            XSSFFont titleFont= (XSSFFont) workbook.createFont();
            titleFont.setFontHeightInPoints((short)10);
            titleFont.setFontName("Calibri");
            titleFont.setColor(IndexedColors.BLACK.getIndex());
            titleFont.setBold(true);
            
            XSSFFont valueFontLeft= (XSSFFont) workbook.createFont();
            valueFontLeft.setFontHeightInPoints((short)10);
            valueFontLeft.setFontName("Calibri");
            valueFontLeft.setColor(IndexedColors.BLACK.getIndex());
            
            XSSFFont valueFontCenter= (XSSFFont) workbook.createFont();
            valueFontCenter.setFontHeightInPoints((short)10);
            valueFontCenter.setFontName("Calibri");
            valueFontCenter.setColor(IndexedColors.BLACK.getIndex());


            CellStyle styleTitle = workbook.createCellStyle();
            styleTitle.setAlignment(HorizontalAlignment.CENTER);
            styleTitle.setFont(titleFont);
            
            CellStyle styleValueLeft = workbook.createCellStyle();
            styleValueLeft.setAlignment(HorizontalAlignment.LEFT);
            styleValueLeft.setFont(valueFontLeft);
            
            CellStyle styleValueCenter = workbook.createCellStyle();
            styleValueCenter.setAlignment(HorizontalAlignment.CENTER);
            styleValueCenter.setFont(valueFontCenter);

            Row fieldnameRow = sheet.createRow(0);
            for (int i = 0; i < 6; i++) {
                Cell cellTitle = fieldnameRow.createCell(i);
                cellTitle.setCellStyle(styleTitle);
                switch (i) {
                    case 0:
                        cellTitle.setCellValue("SKU");
                        break;
                    case 1:
                        cellTitle.setCellValue("Name");
                        break;
                    case 2:
                        cellTitle.setCellValue("Main Image");
                        break;
                    case 3:
                        cellTitle.setCellValue("Type");
                        break;
                    case 4:
                        cellTitle.setCellValue("Parent SKU");
                        break;
                    case 5:
                        cellTitle.setCellValue("Size Name");
                        break;
                }
            }
            int row = 1;
            int productIndex = 0;
            DataFormatter formatter = new DataFormatter();
            for (File file : listFiles) {
                String fileName = file.getName();
                int lastDotIndex = fileName.lastIndexOf('.');
                if (lastDotIndex > 0) {
                    fileName = fileName.substring(0, lastDotIndex);
                }
                System.out.println("" + fileName);
                ImgUrImage imgUrImage = imageMap.get(fileName);
                if (imgUrImage != null) {
                    ArrayList<GenerateModel> listModel = GenerateModel.genListModels(productIndex, imgUrImage);
                    for (GenerateModel generateModel : listModel) {
                        Row fieldRow = sheet.createRow(row);
                        for (int i = 0; i < 6; i++) {
                            Cell cellValue = fieldRow.createCell(i);
                            switch (i) {
                                case 0:
                                    cellValue.setCellStyle(styleValueLeft);
                                    cellValue.setCellValue(generateModel.getSku());
                                    break;
                                case 1:
                                    cellValue.setCellStyle(styleValueLeft);
                                    cellValue.setCellValue(generateModel.getName());
                                    break;
                                case 2:
                                    cellValue.setCellStyle(styleValueLeft);
                                    cellValue.setCellValue(generateModel.getMainImage());
                                    break;
                                case 3:
                                    cellValue.setCellStyle(styleValueCenter);
                                    cellValue.setCellValue(generateModel.getType());
                                    break;
                                case 4:
                                    cellValue.setCellStyle(styleValueCenter);
                                    cellValue.setCellValue(generateModel.getParentSku());
                                    break;
                                case 5:
                                    cellValue.setCellStyle(styleValueCenter);
                                    cellValue.setCellValue(generateModel.getSizeName());
                                    break;
                            }
                        }
                        row++;
                    }
                    productIndex ++;
                }
            }

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(folderPath + "/result.xlsx");
                workbook.write(fileOut);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }
                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static HashMap<String, ImgUrImage> convertListImgUrs(ArrayList<ImgUrImage> listImages) {
        HashMap<String, ImgUrImage> result = new HashMap<>();
        for (ImgUrImage imgUrImage : listImages) {
            result.put(imgUrImage.name, imgUrImage);
        }
        return result;
    }
    
    
    public static void saveNotFoundImages(HashSet<String> listImages, String originImageFolder, String parentFolder, String folderName) throws IOException {
        if (listImages == null || listImages.isEmpty()) return;
        File folder = new File(parentFolder + Configs.pathChar + folderName);
        if (!folder.exists()) {
            folder.mkdir();
        } else {
            FileUtils.cleanDirectory(folder);
        }
        for (String image : listImages) {
            File origin = new File(originImageFolder + image);
            if (origin.exists()) {
                File newFile = new File(folder.getPath() + Configs.pathChar + image);
                Files.copy(origin.toPath(), newFile.toPath());
            }
        }
    }
    
    public static void saveLinkDownload(HashMap<String, String> mapUrl, String fileName) {
        FileOutputStream fileOut = null;

        Workbook workbook = null;


        /* CreationHelper helps us create instances of various things like DataFormat,
         Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try {
            if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook();
            } else {
                workbook = new HSSFWorkbook();
            }

            /* CreationHelper helps us create instances of various things like DataFormat,
             Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet(SHEET_DATA);

            CellStyle style = workbook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.RED.getIndex());

            Row fieldnameRow = sheet.createRow(0);
            Cell cellValueNameTitle = fieldnameRow.createCell(0);
            cellValueNameTitle.setCellValue("Image Name");
            Cell cellValueMaxSizeTitle = fieldnameRow.createCell(1);
            cellValueMaxSizeTitle.setCellValue("Link Ali");
            int row = 1;
//            Row fieldRow = sheet.getRow(i);

//            int size = listData.size();
//            int count = 0;
            DataFormatter formatter = new DataFormatter();

            for (Map.Entry<String,String> entry  : mapUrl.entrySet()) {

                Row fieldRow = sheet.createRow(row);
                Cell cellValueImageName = fieldRow.createCell(0);
                cellValueImageName.setCellValue(entry.getKey());
                Cell cellValueLinkAli = fieldRow.createCell(1);
                cellValueLinkAli.setCellValue(entry.getValue());
                row++;
            }
//            fis.close();

            fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);

        } catch (IOException | EncryptedDocumentException ex) {
        } finally {
            try {

                if (fileOut != null) {
                    fileOut.close();
                }

                if (workbook != null) {
                    workbook.close();
                }

            } catch (IOException ex) {
                Logger.getLogger(TransformImageUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
