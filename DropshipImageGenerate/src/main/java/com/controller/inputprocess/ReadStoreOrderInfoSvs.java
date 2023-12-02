/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.inputprocess;

import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import static com.utils.ExcelUtils.DATA_SHEET;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author duyuno
 */
public abstract class ReadStoreOrderInfoSvs {

    public abstract BaseStoreOrderInfo readStoreFromRow(Row fieldNameRow, Row fieldRow, int cellMax, DataFormatter formatter);

    private ArrayList<BaseStoreOrderInfo> readStoreOrderLinks(Sheet sheet) {

        ArrayList<BaseStoreOrderInfo> listResult = null;

        Row fieldNameRow = sheet.getRow(1);
        if (fieldNameRow == null) {
            return null;
        }

        int cellMax = fieldNameRow.getLastCellNum();

        int i = 2;
        Row fieldRow = sheet.getRow(i);

        DataFormatter formatter = new DataFormatter();

        while (fieldRow != null) {

            BaseStoreOrderInfo baseStoreOrderInfo = readStoreFromRow(fieldNameRow, fieldRow, cellMax, formatter);
            

            if (baseStoreOrderInfo != null) {
                baseStoreOrderInfo.initStoreSign();
                if (listResult == null) {
                    listResult = new ArrayList<>();
                }
                listResult.add(baseStoreOrderInfo);
            } else {
                break;
            }

            i++;
            fieldRow = sheet.getRow(i);
        }

        return listResult;
    }

    public ArrayList<BaseStoreOrderInfo> readStoreOrderLinks(String filePath) {

        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            fis = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(fis);
            Sheet sheet = null;
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {

                Sheet sh = sheetIterator.next();
                if (sh.getSheetName().toUpperCase().equals(DATA_SHEET)) {
                    sheet = sh;
                    break;
                }
            }
            if (sheet == null) {
                return null;
            }   // Create a Sheet

            return readStoreOrderLinks(sheet);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadStoreOrderInfoSvs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ReadStoreOrderInfoSvs.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
                workbook.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadStoreOrderInfoSvs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }
}
