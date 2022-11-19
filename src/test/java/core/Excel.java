package core;

import lombok.Getter;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.List;

public class Excel {
    // --- Fields --- //
    private File excelFile;
    private ReadableWorkbook workbook;  // Actual Excel object
    private List<Sheet> allSheets;
    private Sheet currentSheet; // Current opened sheets
    private List<Row> currentSheetsAllRows;
    @Getter private Row currentRow;

    // --- Constructor --- //
    public Excel(String path) {
        excelFile = new File(path);
        try {
            this.workbook = new ReadableWorkbook(excelFile);
            this.allSheets = workbook.getSheets().toList();
            this.currentSheet = workbook.getFirstSheet();
            this.currentSheetsAllRows = currentSheet.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded following excel sheet...");
        System.out.println(toString());
    }

    // --- Methods --- //
    public Excel useSheet(int index) {
        index--;
        currentSheet = workbook.getSheet(index).get();
        try {
            currentSheetsAllRows = currentSheet.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Excel useSheet(String name) {
        currentSheet = workbook.findSheet(name).get();
        try {
            currentSheetsAllRows = currentSheet.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Cell getDataAt(String address) {
        ExcelColumn.processPosition(address);
        int row = ExcelColumn.getPositionRow() - 1;
        int col = ExcelColumn.toNumber(ExcelColumn.getPositionLetter()) - 1;
        return currentSheetsAllRows.get(row).getCell(col);
    }

    public Cell getDataAt(int row, int col) {
        int fixedRow = --row;
        int fixedCol = --col;
        return currentSheetsAllRows.get(fixedRow)
                .getCell(fixedCol);
    }

    /**
     * Returns all the row object from current sheet
     *
     * @return List of Row objects
     */
    public List<Row> getAllRows() {
        try {
            currentSheetsAllRows = currentSheet.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentSheetsAllRows;
    }

    public Row getRowAt(int index) {
        try {
            currentSheetsAllRows = currentSheet.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentRow = currentSheetsAllRows.get(index);
        return currentRow;
    }

    public String toString() {
        Formatter fm = new Formatter();
        int row = 1;
        for (Row r : currentSheetsAllRows) {
            if(row >= 10) break;
            fm.format("%5s", row++);
            for (int i = 0; i < r.getCellCount(); i++) {
                if(i >= 15) continue;
                String val = r.getCell(i).asString();
                if (val.length() > 21) {
                    val = val.substring(0, 20) + "...";
                }
                fm.format("%25s", val);
            }
            fm.format("\n");
        }
        return fm.toString();
    }

}//end::class
