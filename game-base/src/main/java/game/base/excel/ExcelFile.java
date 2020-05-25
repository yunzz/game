package game.base.excel;

import game.base.log.Logs;
import game.base.utils.JsonUtil;
import game.base.utils.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDateTime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * excel 读取， 第一行注释， 第二行表头，以下数据
 *
 * @author Yunzhe.Jin
 * 2020/3/28 11:28
 */
public class ExcelFile {
    private static final String ERROR_MSG_FORMAT = "Excel加载失败,文件:%s[%s],行:%d,列:%s,值:%s";

    private final String path;
    private File file;
    private XSSFWorkbook workbook;
    private InputStream inputStream;
    protected Map<Integer, String> indexColNameMap = new LinkedHashMap<>();
    protected Map<String, Integer> colNameToIndexMap = new LinkedHashMap<>();
    protected Map<Integer, ExcelCellType> indexTypeMap = new LinkedHashMap<>();
    private boolean closed;
    protected List<String> sheets = new ArrayList<>();

    public ExcelFile(String path) {
        this.path = path;

        open();
    }

    public ExcelFile(File path) {
        this.path = path.getPath();
        this.file = path;

        open();
    }

    private void open() {
        closed = false;
        try {
            if (file != null) {
                this.workbook = new XSSFWorkbook(file);
            } else {
                URL url = ResourceUtils.getURL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setUseCaches(false);
                this.inputStream = urlConnection.getInputStream();
                this.workbook = new XSSFWorkbook(this.inputStream);
            }
            Iterator<Sheet> sheetIterator = this.workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet next = sheetIterator.next();
                sheets.add(next.getSheetName());
            }
        } catch (IOException | InvalidFormatException e) {
            close();
            Logs.common.error("", e);
        }
    }


    public class RowData {
        private final Row row;
        private String col;
        private short colCount;

        public RowData(Row row) {
            this.row = row;
        }

        public String str(String col) {
            this.col = col;
            return ExcelHelp.str0(row.getCell(colNameToIndexMap.get(col)));
        }

        public String str(int col) {
            this.col = indexColNameMap.get(col);
            return ExcelHelp.str0(row.getCell(col));
        }

        public Integer int_(String col) {
            this.col = col;
            return ExcelHelp.int0(row.getCell(colNameToIndexMap.get(col)));
        }

        public Long long_(String col) {
            this.col = col;
            return ExcelHelp.long0(row.getCell(colNameToIndexMap.get(col)));
        }

        public Float float_(String col) {
            this.col = col;
            return ExcelHelp.float0(row.getCell(colNameToIndexMap.get(col)));
        }

        public Double double_(String col) {
            this.col = col;
            return ExcelHelp.double0(row.getCell(colNameToIndexMap.get(col)));
        }

        public LocalDateTime date(String col) {
            this.col = col;
            return ExcelHelp.date0(row.getCell(colNameToIndexMap.get(col)));
        }

        public <T> T obj(String col, Class<T> type) {
            String str = str(col);
            if (StringUtils.isEmpty(str)) {
                return null;
            }
            return JsonUtil.fromJsonString(str, type);
        }

        public short getColCount() {
            return colCount;
        }

        public void setColCount(short colCount) {
            this.colCount = colCount;
        }
    }

    public void clear() {
        indexColNameMap.clear();
        colNameToIndexMap.clear();
        indexTypeMap.clear();
    }

    public void parse(String sheetName, SheetParse sheetParse) {
        clear();
        XSSFSheet sheet = workbook.getSheet(sheetName);

        Iterator<Row> rowIterator = sheet.rowIterator();
        short colCount = 0;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            RowData rowData = new RowData(row);
            rowData.setColCount(colCount);
            try {
                int rowNum = row.getRowNum();
                if (rowNum == 0) {
                    continue;
                }
                if (rowNum == 1) {
                    short lastCellNum = row.getLastCellNum();
                    colCount = lastCellNum;

                    for (int i = 0; i < lastCellNum; i++) {
                        Cell cell = row.getCell(i);
                        String stringCellValue = cell.getStringCellValue();
                        if(StringUtils.isEmpty(stringCellValue)){
                            continue;
                        }
                        indexColNameMap.put(i, stringCellValue);
                        colNameToIndexMap.put(stringCellValue, i);
                    }
                    continue;
                }
                if (rowNum == 2) {
                    for (int i = 0; i < rowData.getColCount(); i++) {
                        String s = rowData.str(i);
                        ExcelCellType cellType = ExcelCellType.STRING;
                        if (StringUtils.isNotEmpty(s)) {
                            cellType = ExcelCellType.valueOf(s.toUpperCase());
                        }
                        indexTypeMap.put(i, cellType);
                    }
                    continue;
                }

                sheetParse.parseRow(rowData);
            } catch (Exception e) {
                Logs.common.error("", e);
                throw fail(rowData);
            }
        }
    }

    private RuntimeException fail(RowData rowData) {
        return new RuntimeException(String.format(ERROR_MSG_FORMAT,
                path,
                rowData.row.getSheet().getSheetName(),
                rowData.row.getRowNum(),
                rowData.col,
                rowData.row.getCell(colNameToIndexMap.get(rowData.col))));

    }

    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        this.workbook = null;
        try {
            if (inputStream != null) {
                this.inputStream.close();
            }

        } catch (Exception e) {
            Logs.common.error("", e);
        }
    }

    public String colName(int col) {
        return indexColNameMap.get(col);
    }
}
