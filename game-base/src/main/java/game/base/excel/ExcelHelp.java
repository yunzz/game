package game.base.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.joda.time.LocalDateTime;

import java.util.Date;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 13:12
 */
public class ExcelHelp {

    public static String str0(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
            case FORMULA:
                return cell.getNumericCellValue() + "";
        }
        return cell.toString().trim();
    }

    public static Integer int0(Cell cell) {
        return double0(cell).intValue();
    }

    public static Long long0(Cell cell) {
        return double0(cell).longValue();
    }

    public static Float float0(Cell cell) {
        if (cell == null) {
            return 0f;
        }
        String str = cell.toString();

        if (cell.getCellType() == CellType.FORMULA) {
            return double0(cell).floatValue();
        }
        if (str.equals("0%")) {
            return 0f;
        }

        return double0(cell).floatValue();
    }

    public static Double double0(Cell cell) {
        if (cell == null) {
            return 0.0;
        }

        switch (cell.getCellType()) {
            case STRING:
                return Double.valueOf(cell.getStringCellValue());
            case NUMERIC:
            case FORMULA:
                return cell.getNumericCellValue();
        }

        return 0.0;
    }

    public static LocalDateTime date0(Cell cell) {
        if (cell == null) {
            return null;
        }

        Date dateCellValue = cell.getDateCellValue();
        if (dateCellValue != null) {
            return LocalDateTime.fromDateFields(dateCellValue);
        }
        return null;
    }
}
