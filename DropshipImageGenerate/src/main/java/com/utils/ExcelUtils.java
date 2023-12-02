/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.config.Configs;
import com.models.aliex.MarketProductBasicItem;
import com.models.amazon.BTGNode;
import com.models.amazon.ProductAmz;
import com.models.amazon.Refiment;
import com.models.aliex.store.AliexStoreInfo;
import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import com.models.amazon.CustomValue;
import com.models.amazon.DataStore;
import com.controller.DownloadManager;
import com.controller.crawl.machine.DownloadMachine;
import com.models.response.TransformResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.activation.DataSource;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Admin
 */
public class ExcelUtils {

    public static final String DATA_SHEET = "DATA";
    public static final String TEMPLATE_SHEET = "TEMPLATE";

    public static void saveListProductsToExcel(AliexStoreInfo aliexStorePageInfo, String sampleFilePath) throws EncryptedDocumentException, InvalidFormatException, IOException {
//        saveListProductsToExcel(aliexStorePageInfo.getListProductAmzs(), aliexStorePageInfo.getExcelFilePath(), sampleFilePath);
    }

    public static void saveListProductsToExcel(ArrayList<ProductAmz> listProducts, String newFilePath, String sampleFilePath, AliexStoreInfo aliexStoreInfo, boolean isSaveAllData) throws EncryptedDocumentException, InvalidFormatException, IOException {
        if (listProducts == null || listProducts.isEmpty()) {
            return;
        }
        
        System.out.println("Save size: " + listProducts.size());
        
        String localImageFolder = null;
        String vpsImageFolder = null;
        
        if (!StringUtils.isEmpty(Configs.vpsIp) && !isSaveAllData) {
            localImageFolder = aliexStoreInfo.getLocalImageFolder();
            vpsImageFolder = aliexStoreInfo.getImageVpsFolder();
        }

        System.out.println("NewFile: " + newFilePath);

//        Workbook workbook = null;
//        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
//        File src = new File(SAMPLE_XLSX_FILE_PATH);
//        File dst = new File(storeId + ".xlsx");
//        FileUtils.copyFile(src, dst);
//        FileInputStream fis = new FileInputStream(storeId + ".xlsx");
        FileInputStream fis = new FileInputStream(sampleFilePath);
        System.out.println("Template: " + "/AmzTemplate" + Configs.pathChar + sampleFilePath);
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/AmzTemplate" + Configs.pathChar + sampleFilePath);
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/AmzTemplate" + Configs.pathChar + sampleFilePath);

//        String fileTemplate = aliexStorePageInfo.getSampleFile();
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            System.out.println("Retrieving Sheets using Iterator");

            if (!sheetIterator.hasNext()) {
                return;
            }

//            sheet = sheetIterator.next();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
//                System.out.println("=> " + sh.getSheetName());

                if (sh.getSheetName().toUpperCase().equals(TEMPLATE_SHEET)) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

// Create Other rows and cells with employees data
            Row fieldRow = sheet.getRow(2);
            int cellMax = 0;

            if (fieldRow == null) {
                workbook.close();
                return;
            }

            cellMax = fieldRow.getLastCellNum();

            if (cellMax == 0) {
                workbook.close();
                return;
            }

            int rowNum = 3;
            while (sheet.getRow(rowNum) != null) {
                rowNum++;
            }
            
            int parentCount = 0;
            int productCount = 0;
            boolean isChildProcessing = false;

            for (ProductAmz productAmz : listProducts) {
//                productCount ++;
//                isChildProcessing = !productAmz.isParent();
//                if (productCount >= 500 && !isChildProcessing) {
//                    System.out.println("Stop at " + productCount);
//                    break;
//                }
                
                if (vpsImageFolder != null) {
                    productAmz.updateImageUrlVps(vpsImageFolder);
                    DownloadManager.getInstance().downloadImageAndUpdate(productAmz, localImageFolder);
                }
                Row row = sheet.createRow(rowNum++);
                for (int j = 0; j < cellMax; j++) {
                    Cell cell = fieldRow.getCell(j);
                    String fieldName = cell.getStringCellValue().trim();
                    if (Configs.hashCustomValue.containsKey(fieldName)) {
                        row.createCell(j).setCellValue(Configs.hashCustomValue.get(fieldName));
                    } else {
                        String value = DataStore.getValue(productAmz.getItem_sku(), fieldName);
                        if (!StringUtils.isEmpty(value)) {
                            row.createCell(j).setCellValue(value);
                        } else {
                            row.createCell(j).setCellValue(productAmz.getValueForCell(fieldName));
                        }
//                        else {
//                            row.createCell(j).setCellValue(productAmz.getValueForCell(fieldName));
//                        }
                    }
                }
//                
//
//                row.createCell(0)
//                        .setCellValue("Name " + i);
//
//                row.createCell(1)
//                        .setCellValue("Email " + i);
//
//                Cell dateOfBirthCell = row.createCell(2);
//                dateOfBirthCell.setCellValue("Birthday " + i);
//                dateOfBirthCell.setCellStyle(dateCellStyle);
//
//                row.createCell(3)
//                        .setCellValue("Salary " + i);
            }

            fis.close();

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(newFilePath);
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

    public static void saveListProductsToExcel(ArrayList<ProductAmz> listProducts, String sampleFilePath, String sampleSheet, String newFilePath, String dataFolder) throws EncryptedDocumentException, InvalidFormatException, IOException {

//        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
//        File src = new File(SAMPLE_XLSX_FILE_PATH);
//        File dst = new File(storeId + ".xlsx");
//        FileUtils.copyFile(src, dst);
//        FileInputStream fis = new FileInputStream(storeId + ".xlsx");
        FileInputStream fis = new FileInputStream(sampleFilePath);

        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            System.out.println("Retrieving Sheets using Iterator");
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
                System.out.println("=> " + sh.getSheetName());

                if (sh.getSheetName().equals(sampleSheet)) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return;
            }   // Create a Sheet

            // Create a Font for styling header cells
//        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
//        headerFont.setFontHeightInPoints((short) 14);
//        headerFont.setColor(IndexedColors.RED.getIndex());
// Create a CellStyle with the font
//        CellStyle headerCellStyle = workbook.createCellStyle();
//        headerCellStyle.setFont(headerFont);
// Create a Row
//        Row headerRow = btgSheet.createRow(0);
// Create cells
//        for (int i = 0; i < listProductColumns.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(listProductColumns[i]);
//            cell.setCellStyle(headerCellStyle);
//        }
// Create Cell Style for formatting Date
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
// Create Other rows and cells with employees data

            Row fieldRow = sheet.getRow(2);
            int cellMax = 0;

            if (fieldRow == null) {
                workbook.close();
                return;
            }

            cellMax = fieldRow.getLastCellNum();

            if (cellMax == 0) {
                workbook.close();
                return;
            }

            int rowNum = 3;
            while (sheet.getRow(rowNum) != null) {
                rowNum++;
            }

            for (ProductAmz productAmz : listProducts) {
                Row row = sheet.createRow(rowNum++);
                for (int j = 0; j < cellMax; j++) {
                    Cell cell = fieldRow.getCell(j);
                    row.createCell(j)
                            .setCellValue(productAmz.getValueForCell(cell.getStringCellValue()));

                }

//                
//
//                row.createCell(0)
//                        .setCellValue("Name " + i);
//
//                row.createCell(1)
//                        .setCellValue("Email " + i);
//
//                Cell dateOfBirthCell = row.createCell(2);
//                dateOfBirthCell.setCellValue("Birthday " + i);
//                dateOfBirthCell.setCellStyle(dateCellStyle);
//
//                row.createCell(3)
//                        .setCellValue("Salary " + i);
            }

            fis.close();

            FileOutputStream fileOut = null;
            try {
//                if (newFilePath.endsWith(".xlsx")) {
//                    newFilePath = newFilePath.replace(".xlsx", "_" + listProducts.size() + ".xlsx");
//                } else {
//                    newFilePath += "_" + listProducts.size() + ".xlsx";
//                }
//                fileOut = new FileOutputStream(newFilePath);
                fileOut = new FileOutputStream(newFilePath);
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

//    public static void fixingProductIdError(String errorFilePath, String sheetName, String sampleFilePath, String sampleSheet) throws FileNotFoundException, IOException, InvalidFormatException {
//        ArrayList<ProductAmz> listProduct = copyAndFixIdFromErrorFile(errorFilePath, sheetName);
//        saveListProductsToExcel(listProduct, sampleFilePath, sampleSheet, Configs.FIXING_PRODUCT_PATH + System.currentTimeMillis() + ".xlsx");
//    }
//
//    public static void removeBannedInfo(String errorFilePath, String sheetName, String sampleFilePath, String sampleSheet) throws FileNotFoundException, IOException, InvalidFormatException {
//        ArrayList<ProductAmz> listProduct = copyAndFixIdFromErrorFile(errorFilePath, sheetName);
//        saveListProductsToExcel(listProduct, sampleFilePath, sampleSheet, Configs.FIXING_PRODUCT_PATH + System.currentTimeMillis() + ".xlsx");
//    }
    public static ArrayList<ProductAmz> copyAndFixIdFromErrorFile(String errorPathFile, String sheetName) throws FileNotFoundException, IOException, InvalidFormatException {
        ArrayList<ProductAmz> listResults = new ArrayList<>();

        FileInputStream fis = new FileInputStream(errorPathFile);

        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();

                if (sh.getSheetName().equals(sheetName)) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return null;
            }   // Create a Sheet

            Row fieldnameRow = sheet.getRow(2);
            int cellMax = fieldnameRow.getLastCellNum();

            int i = 3;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                ProductAmz productAmz = new ProductAmz();

                DataFormatter formatter = new DataFormatter();

                String errorValue = formatter.formatCellValue(fieldRow.getCell(0));
//                System.out.println("ErrorValue: " + errorValue);
                if (errorValue.equals("SUCCESS")) {
                    continue;
                }
                productAmz.setExternal_product_id(CodeUtils.genRandomProductId());

                for (int j = 2; j < cellMax; j++) {
                    String value = formatter.formatCellValue(fieldRow.getCell(j));
//                    if(fieldnameRow.getCell(j).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
//                        value += fieldRow.getCell(j).getNumericCellValue();
//                    } else if(fieldnameRow.getCell(j).getCellType() == XSSFCell.CELL_TYPE_STRING) {
//                        value += fieldRow.getCell(j).getStringCellValue();
//                    }
                    productAmz.setValueForAFiled(formatter.formatCellValue(fieldnameRow.getCell(j)), value);

                }
                
                listResults.add(productAmz);

                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();
            workbook.close();
        }

        return listResults;
    }
//    
//    public String getImageVpsFolder(String folderPath) {
//        return "http://" + Configs.vpsIp + "/" + accNo + "/" + storeSign + "/";
//    }

    
    
    public static ArrayList<MarketProductBasicItem> getListAliexItems(String filePath) throws FileNotFoundException {

        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        ArrayList<MarketProductBasicItem> listItems = null;
        FileInputStream fis = new FileInputStream(file);
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
//            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");
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
                return null;
            }   // Create a Sheet

            Row fieldnameRow = sheet.getRow(2);
            int cellMax = fieldnameRow.getLastCellNum();

            int i = 3;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                DataFormatter formatter = new DataFormatter();
                String item_sku = null;
                String parent = null;
                for (int j = 0; j < cellMax; j++) {
                    String fieldName = formatter.formatCellValue(fieldnameRow.getCell(j));

                    if (fieldName.equals("item_sku")) {
                        item_sku = formatter.formatCellValue(fieldRow.getCell(j));
                    } else if (fieldName.equals("parent_child")) {
                        parent = formatter.formatCellValue(fieldRow.getCell(j));
                        if (parent == null) {
                            parent = "";
                        }
                    }

                    if (item_sku != null && parent != null) {
                        break;
                    }

                }

                if (parent.isEmpty() || parent.toLowerCase().equals("parent")) {
                    String[] valueParts = item_sku.split(Pattern.quote("_"));
                    if (listItems == null) {
                        listItems = new ArrayList<>();
                    }
                    MarketProductBasicItem marketProductBasicItem = new MarketProductBasicItem();
                    marketProductBasicItem.setId(valueParts[1]);
                    listItems.add(marketProductBasicItem);
                }

                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();
            workbook.close();
        } catch (Exception ex) {

        }

        return listItems;
    }

    public static ArrayList<String> getBannedKeywords(String filePath) throws FileNotFoundException {
        ArrayList<String> listKeywords = null;
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        FileInputStream fis = new FileInputStream(file);
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
//            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");
            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();

                if (sh.getSheetName().equals("Data")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return null;
            }   // Create a Sheet

            int i = 0;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                DataFormatter formatter = new DataFormatter();
                String keyword = formatter.formatCellValue(fieldRow.getCell(0));

                if (StringUtils.isEmpty(keyword)) {
                    i++;
                    fieldRow = sheet.getRow(i);
                    continue;
                }

                if (listKeywords == null) {
                    listKeywords = new ArrayList<>();
                }

                listKeywords.add(keyword.trim());
                i++;
                fieldRow = sheet.getRow(i);
            }

            workbook.close();
        } catch (Exception ex) {

        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        return listKeywords;
    }

    public static void getBTGFromExcel(String filePath, String templateFile, HashMap<String, BTGNode> hashMapBTG, HashMap<String, String> hashMapBTGSub) throws FileNotFoundException, IOException, InvalidFormatException {

        if (hashMapBTG == null) {
            hashMapBTG = new HashMap<>();
        }

        HashMap<String, ArrayList<Refiment>> hashMapRefiment = new HashMap<>();
//        System.out.println("BTG file: " + "/BTG" + Configs.pathChar + filePath);
//        ArrayList<BTGNode> listBTGNode = null;
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/BTG" + Configs.pathChar + filePath);
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/" + filePath);
        FileInputStream fis = new FileInputStream(Configs.CONFIG_FOLDER_PATH + Configs.pathChar + "BTG" + Configs.pathChar + filePath);

        String parentBranch = null;

        SimpleDateFormat spd = new SimpleDateFormat("HH':'mm':'ss' - 'dd/MM/yyyy");
        String time = spd.format(new Date());


        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return;
            }

            Sheet btgSheet = sheetIterator.next();

            if (btgSheet == null) {
                return;
            }

            if (!sheetIterator.hasNext()) {
                return;
            }
            Sheet refimentSheet = sheetIterator.next();

            if (refimentSheet == null) {
                return;
            }

            DataFormatter formatter = new DataFormatter();

            int i = 1;
            Row fieldRowRefiment = refimentSheet.getRow(i);

//            List<? extends DataValidation> dataValidations = refimentSheet.getDataValidations();
//            for(DataValidation dataValidation : dataValidations) {
//                System.out.println("" + dataValidation.getValidationConstraint().getExplicitListValues());
//            }
            while (fieldRowRefiment != null) {

                String nodeId = formatter.formatCellValue(fieldRowRefiment.getCell(0));
                String name = formatter.formatCellValue(fieldRowRefiment.getCell(2));
                String atttribute = formatter.formatCellValue(fieldRowRefiment.getCell(3));

                if (hashMapRefiment.containsKey(nodeId)) {
                    ArrayList<Refiment> listRefiments = hashMapRefiment.get(nodeId);
                    if (listRefiments == null) {
                        listRefiments = new ArrayList<>();
                    }
                    listRefiments.add(new Refiment(name, atttribute, nodeId));
                } else {
                    ArrayList<Refiment> listRefiments = new ArrayList<>();
                    listRefiments.add(new Refiment(name, atttribute, nodeId));
                    hashMapRefiment.put(nodeId, listRefiments);
                }
                i++;
                fieldRowRefiment = refimentSheet.getRow(i);
            }

            i = 1;
            Row fieldRow = btgSheet.getRow(i);

//            String keywordFiels = "item_type_keyword";
//            String departMentField = "department_name";
            while (fieldRow != null) {

                String nodeId = formatter.formatCellValue(fieldRow.getCell(0));
                String nodePath = formatter.formatCellValue(fieldRow.getCell(1));
                String[] paths = nodePath.split(Pattern.quote("/"));
                String lastNode = paths[paths.length - 1];
                String nodeName = nodePath.trim();

                hashMapBTGSub.put(lastNode.toUpperCase(), nodeName.toUpperCase());

                if (i == 1) {
                    parentBranch = nodeName;
                }

                String keyInfo = formatter.formatCellValue(fieldRow.getCell(2));

                String keyword = null;
                String departMentName = null;
                String audienceKeywords = null;

                if (keyInfo != null && !keyInfo.isEmpty()) {
                    String[] infoParts = keyInfo.split("AND");

                    for (String s : infoParts) {
                        if (s.contains(BTGNode.ITEM_TYPE_KEY_FIELD_NAME)) {
                            keyword = s.trim();
                        } else if (s.contains(BTGNode.DEPART_FIELD_NAME)) {
                            departMentName = s.trim();
                        } else if (s.contains(BTGNode.AUDIENCE_KEY_FIELD_NAME)) {
                            audienceKeywords = s.trim();
                        }
                    }

//                    if (infoParts.length > 1) {
//                        keyword = infoParts[1].trim();
//                        departMentName = infoParts[0].trim();
//                    } else {
//                        keyword = infoParts[0].trim();
//                    }
                }

//                if (listBTGNode == null) {
//                    listBTGNode = new ArrayList<>();
//                }
//
//                listBTGNode.add(new BTGNode(nodePath, nodeName, nodeId, keyword, departMentName, parentBranch, filePath));
                BTGNode bTGNode = new BTGNode(nodePath, nodeName, nodeId, keyword, audienceKeywords, departMentName, parentBranch, filePath, templateFile);
                bTGNode.setListRefiment(hashMapRefiment.get(nodeId));
                hashMapBTG.put(nodeName.trim().toUpperCase(), bTGNode);
                i++;
                fieldRow = btgSheet.getRow(i);
            }

            fis.close();
            workbook.close();

        }

//        return listBTGNode;
    }

    public static int countTotalProductSku(String filePath) {
        if (!filePath.endsWith(".xlsx")) {
            return 0;
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(filePath);
//            System.out.println("" + filePath);
            return countTotalProductSku(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InvalidFormatException ex) {
            Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static int countTotalProductSku(InputStream fis) throws FileNotFoundException, IOException, InvalidFormatException {
        int total = 0;

        HashMap<String, String> skuHash = new HashMap<>();

        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        Workbook workbook = null;
        try {
//            workbook = WorkbookFactory.create(fis);

            workbook = new XSSFWorkbook(fis);
        } catch (Exception ex) {
//            OlePackage pkg = OLEPackage.open(fis);
            workbook = new HSSFWorkbook(fis);
        }

//        workbook.
//        Workbook workbook = new XSSFWorkbook();
//        if (fis.toString().endsWith(".xls")  {
//            workbook = new HSSFWorkbook();
//        } else {
//            workbook = new XSSFWorkbook();
//        }
//        XSSFWorkbook wb = new XSSFWorkbook(pkg);
//        try (workbook = WorkbookFactory.create(fis)) {
        try {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

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
                return -1;
            }   // Create a Sheet

            Row fieldnameRow = sheet.getRow(2);
            int cellMax = fieldnameRow.getLastCellNum();

            int i = 3;
            Row fieldRow = sheet.getRow(i);

            while (fieldRow != null) {
                ProductAmz productAmz = new ProductAmz();

                DataFormatter formatter = new DataFormatter();
                for (int j = 0; j < cellMax; j++) {
                    String fieldName = formatter.formatCellValue(fieldnameRow.getCell(j));

                    if (fieldName.equals("parent_child")) {
                        String value = formatter.formatCellValue(fieldRow.getCell(j));
                        if (value == null || value.isEmpty() || value.toLowerCase().equals("parent")) {
                            total++;
                        }
                        break;
                    }

                }

                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();
            workbook.close();
        } catch (Exception ex) {

        }

        return total;
    }

    public static String zipFoldersInDirectory(String parentPath, String directoryPath) {

        File file = new File(directoryPath);

        if (!file.isDirectory()) {
            return null;
        }
        FileOutputStream fos = null;
        try {
            String targetPath = parentPath + Configs.pathChar + file.getName() + ".zip";
            fos = new FileOutputStream(targetPath);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(directoryPath);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();

            return targetPath;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(OSUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    private static String zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return null;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return fileToZip.getPath();
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();

        return fileToZip.getPath();
    }
    
    public static HashMap<String, ArrayList<String>> initValidValues(String productType, String sampleFile) {
        HashMap<String, ArrayList<String>> hashMapValidValues = new HashMap<>();
        System.out.println("/ValidateValue" + com.config.Configs.pathChar + sampleFile);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(com.config.Configs.CONFIG_FOLDER_PATH + com.config.Configs.pathChar + "ValidateValue" + com.config.Configs.pathChar + sampleFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(com.utils.ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(fis == null) {
            return null;
        }
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/ValidateValue" + Configs.pathChar + sampleFile);
//        InputStream fis = ExcelUtils.class.getResourceAsStream("/ValidateValue" + Configs.pathChar + sampleFile);
        try (Workbook workbook = WorkbookFactory.create(fis)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            //        Sheet btgSheet = workbook.createSheet("Employee");

            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return null;
            }

//            sheet = sheetIterator.next();
            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
//                System.out.println("=> " + sh.getSheetName());

                if (sh.getSheetName().equals("Valid Values")) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return null;
            }   // Create a Sheet

            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

// Create Other rows and cells with employees data
            Row fieldRowFirst = sheet.getRow(0);
            Row fieldRow = sheet.getRow(1);
            int cellMax = 0;

            if (fieldRow == null) {
                workbook.close();
                return null;
            }

            cellMax = fieldRow.getLastCellNum();

            if (cellMax == 0) {
                workbook.close();
                return null;
            }

            DataFormatter formatter = new DataFormatter();

            int rowNum = 2;
            Row row = sheet.getRow(rowNum);

            while (row != null) {
//                System.out.println("Row " + rowNum);
                boolean hasRowValue = false;
                for (int i = 0; i < cellMax; i++) {
                    String key = formatter.formatCellValue(fieldRow.getCell(i));
                    String value = formatter.formatCellValue(row.getCell(i));

                    if (value == null || value.isEmpty()) {
                        continue;
                    }

                    hasRowValue = true;

                    String firstRowValue = formatter.formatCellValue(fieldRowFirst.getCell(i));
                    if (firstRowValue.contains("[")) {
                        if (!firstRowValue.contains(productType.toLowerCase())) {
                            continue;
                        }
                    }

                    if (hashMapValidValues.containsKey(key)) {
                        ArrayList<String> listValues = hashMapValidValues.get(key);
                        if (listValues == null) {
                            listValues = new ArrayList<>();
                        }

                        listValues.add(value.trim());
                    } else {
                        ArrayList<String> listValues = new ArrayList<>();
                        listValues.add(value.trim());
                        hashMapValidValues.put(key, listValues);

                    }
                }

                if (!hasRowValue) {
                    break;
                }

                rowNum++;
                row = sheet.getRow(rowNum);
            }

            fis.close();
            workbook.close();
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(com.utils.ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hashMapValidValues;
        
    }

    public static void updateStoreStatus(String filePath, BaseStoreOrderInfo storePageInfo, boolean isDone, int total) throws FileNotFoundException, IOException, InvalidFormatException {

        if (storePageInfo == null) {
            return;
        }

        FileInputStream fis = new FileInputStream(filePath);

//        SimpleDateFormat spd = new SimpleDateFormat("HH':'mm':'ss' - 'dd/MM/yyyy");
//        String time = spd.format(new Date());
        /* CreationHelper helps us create instances of various things like DataFormat,
        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try (Workbook workbook = WorkbookFactory.create(fis)) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            if (!sheetIterator.hasNext()) {
                return;
            }
            Sheet sheet = null;

            while (sheetIterator.hasNext()) {
                Sheet sh = sheetIterator.next();
//                System.out.println("=> " + sh.getSheetName());

                if (sh.getSheetName().toUpperCase().equals(DATA_SHEET)) {
                    sheet = sh;
                    break;
                }
            }

            if (sheet == null) {
                return;
            }   // Create a Sheet

            Row fieldNameRow = sheet.getRow(1);
            if (fieldNameRow == null) {
                return;
            }

            int cellMax = fieldNameRow.getLastCellNum();

            int i = 2;
            Row fieldRow = sheet.getRow(i);

            DataFormatter formatter = new DataFormatter();

//            HashMap<String, Integer> hashMap = new HashMap<>();
            while (fieldRow != null) {

                String storeUrl = null;
                String accNo = null;
                String status = null;

                for (int j = 0; j < cellMax; j++) {
                    String fieldName = formatter.formatCellValue(fieldNameRow.getCell(j));
                    switch (fieldName) {
                        case "link":
                            storeUrl = formatter.formatCellValue(fieldRow.getCell(j));
                            break;
                        case "acc_no":
                            accNo = formatter.formatCellValue(fieldRow.getCell(j));
                            break;
                        case "status":
                            status = formatter.formatCellValue(fieldRow.getCell(j));
                            break;
                    }
                }

                if (storeUrl == null || storeUrl.trim().isEmpty() || !storeUrl.startsWith("http")) {
                    i++;
                    fieldRow = sheet.getRow(i);
                    continue;
                }

//                URI uri = URI.create(storeUrl);
//                String domain = uri.getHost();
//                String path = uri.getPath();
//                URI storeUri = URI.create(storePageInfo.getOrginalStoreLink());
                if (storeUrl.equals(storePageInfo.getLink())) {
                    for (int j = 0; j < cellMax; j++) {
                        String fieldName = formatter.formatCellValue(fieldNameRow.getCell(j));
                        if (fieldName.equals("status")) {
                            Cell cellValue = fieldRow.getCell(j);
                            if (cellValue == null) {
                                cellValue = fieldRow.createCell(j);
                                cellValue.setCellValue(isDone ? "Done" : "");
                            } else {
                                cellValue.setCellValue(isDone ? "Done" : "");
                            }
                        } else if (fieldName.contains("total")) {
                            Cell cellValueTotal = fieldRow.getCell(j);
                            if (cellValueTotal == null) {
                                cellValueTotal = fieldRow.createCell(j);
                                cellValueTotal.setCellValue(isDone ? "" + total : "");
                            } else {
                                cellValueTotal.setCellValue(isDone ? "" + total : "");
                            }
                        }
                    }

//                    Cell cellValue = fieldRow.getCell(7);
//                    if (cellValue == null) {
//                        cellValue = fieldRow.createCell(7);
//                        cellValue.setCellValue(isDone ? "Done" : "");
//                    } else {
//                        cellValue.setCellValue(isDone ? "Done" : "");
//                    }
//
//                    Cell cellValueTotal = fieldRow.getCell(8);
//                    if (cellValueTotal == null) {
//                        cellValueTotal = fieldRow.createCell(8);
//                        cellValueTotal.setCellValue(isDone ? "" + total : "");
//                    } else {
//                        cellValueTotal.setCellValue(isDone ? "" + total : "");
//                    }
                    break;
                }
                i++;
                fieldRow = sheet.getRow(i);
            }

            fis.close();

            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(filePath);
                workbook.write(fileOut);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(com.utils.ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } catch (IOException ex) {
                Logger.getLogger(com.utils.ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw ex;
            } finally {
                try {
                    if (fileOut != null) {
                        fileOut.close();
                    }

                    workbook.close();
                } catch (IOException ex) {
                    Logger.getLogger(com.utils.ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }

//    public static void readCustomValue() throws FileNotFoundException, IOException {
//        FileInputStream fis = new FileInputStream(Configs.excelCustomValueFilePath);
//
//        /* CreationHelper helps us create instances of various things like DataFormat,
//        Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
//        try (Workbook workbook = WorkbookFactory.create(fis)) {
//
//            /* CreationHelper helps us create instances of various things like DataFormat,
//            Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
//            CreationHelper createHelper = workbook.getCreationHelper();
//            //        Sheet btgSheet = workbook.createSheet("Employee");
//
//            Sheet sheet = null;
//            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
//            while (sheetIterator.hasNext()) {
//                Sheet sh = sheetIterator.next();
//
//                if (sh.getSheetName().equals("data")) {
//                    sheet = sh;
//                    break;
//                }
//            }
//            if (sheet == null) {
//                return;
//            }   // Create a Sheet
//
//            int i = 1;
//            Row fieldRow = sheet.getRow(i);
//
//            while (fieldRow != null) {
//                DataFormatter formatter = new DataFormatter();
//                
//                String fieldName = formatter.formatCellValue(fieldRow.getCell(0));
//                String value = formatter.formatCellValue(fieldRow.getCell(1));
//                
//                if (!StringUtils.isEmpty(fieldName) && !StringUtils.isEmpty(value)) {
//                    Configs.hashCustomValue.put(fieldName.trim(), value.trim());
//                }
//                i++;
//                fieldRow = sheet.getRow(i);
//            }
//
//            fis.close();
//            workbook.close();
//        }
//    }
    
    public static void saveErrorProducts(String filePath, ArrayList<TransformResponse> listErrorProducts) throws FileNotFoundException, IOException {

        if (listErrorProducts == null || listErrorProducts.isEmpty()) {
            return;
        }

        System.out.println("Save error file: " + filePath);
//        FileInputStream fis = null;
        FileOutputStream fileOut = null;

        Workbook workbook = null;


        /* CreationHelper helps us create instances of various things like DataFormat,
         Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        try {
            if (filePath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook();
            } else {
                workbook = new HSSFWorkbook();
            }

            /* CreationHelper helps us create instances of various things like DataFormat,
             Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = workbook.getCreationHelper();
            Sheet sheet = workbook.createSheet("Data");

            CellStyle style = workbook.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.RED.getIndex());

            Row fieldnameRow = sheet.createRow(0);
            Cell cellProductIdTitle = fieldnameRow.createCell(0);
            cellProductIdTitle.setCellValue("ProductId");
            Cell cellReasonTitle = fieldnameRow.createCell(1);
            cellReasonTitle.setCellValue("Reason");

            int row = 1;
//            Row fieldRow = sheet.getRow(i);

//            int size = listData.size();
//            int count = 0;
            DataFormatter formatter = new DataFormatter();

            for (TransformResponse res : listErrorProducts) {
                Row fieldRow = sheet.createRow(row);
                Cell cellProductId = fieldRow.createCell(0);
                Cell cellReason = fieldRow.createCell(1);
                if (res.getData() != null && res.getData().getProductId() != null) {
                    cellProductId.setCellValue(res.getData().getProductId());
                }
                cellReason.setCellValue(res.error);
                row++;
            }
//            fis.close();

            fileOut = new FileOutputStream(filePath);
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
                Logger.getLogger(ExcelUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
