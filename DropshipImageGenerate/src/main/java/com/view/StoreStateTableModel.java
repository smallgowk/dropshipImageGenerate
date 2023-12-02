/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import com.models.aliex.store.inputdata.BaseStoreOrderInfo;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author PhanDuy
 */
public class StoreStateTableModel extends AbstractTableModel {
    private final String[] columnNames = {"AccNo",
            "Status"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return DataUtils.getDataRow();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {

            BaseStoreOrderInfo baseStoreOrderInfo = DataUtils.getState(row);
            if (baseStoreOrderInfo == null) {
                return "";
            }


            switch (col) {
                case 0:
                    return baseStoreOrderInfo.getStoreSign();
                case 1:
                    return DataUtils.getStatus(baseStoreOrderInfo.getStoreSign());
            }

            return "";
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return col >= 2;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
//            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
}
