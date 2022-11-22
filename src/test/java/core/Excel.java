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
    @Getter static private String positionLetter="";
    @Getter static private Integer positionRow = 0;

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

    public Cell dataAt(String address) {
        processPosition(address);
        int row = getPositionRow() - 1;
        int col = toNumber(getPositionLetter()) - 1;
        return currentSheetsAllRows.get(row).getCell(col);
    }

    public Cell dataAt(int row, int col) {
        int fixedRow = --row;
        int fixedCol = --col;
        return currentSheetsAllRows.get(fixedRow)
                .getCell(fixedCol);
    }

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

    // ---- Helper Functions ----- //
    private static int toNumber(String name) {
        int number = 0;
        for (int i = 0; i < name.length(); i++) {
            number = number * 26 + (name.charAt(i) - ('A' - 1));
        }
        return number;
    }

    private static String toName(int number) {
        StringBuilder sb = new StringBuilder();
        while (number-- > 0) {
            sb.append((char)('A' + (number % 26)));
            number /= 26;
        }
        return sb.reverse().toString();
    }

    private static void processPosition(String position) {
        String letter = "";
        String number = "";
        for(int i = 0; i < position.length(); i++) {
            char c = position.charAt(i);
            if(c >= '0' && c <= '9') number+= c;
            else letter+=c;
        }

        positionLetter = letter;
        positionRow = Integer.valueOf(number);
    }
}//end::class
