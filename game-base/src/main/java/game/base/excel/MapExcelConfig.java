package game.base.excel;

import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 需要long 型 id列
 *
 * @author Yunzhe.Jin
 * 2020/4/9 16:59
 */
public class MapExcelConfig extends ExcelConfig {
    private ImmutableMap<String, Object> map;

    public MapExcelConfig(String path) {
        super(path);
    }

    public MapExcelConfig(File path) {
        super(path);
    }

    @Override
    public void load() {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();

        for (String sheet : excel.sheets) {
            Map<String, Object> sheetData = new LinkedHashMap<>();
            excel.parse(sheet, rowData -> {
                short i = rowData.getColCount();
                String id = rowData.long_("id")+"";

                Map<String, Object> colDataMap = new LinkedHashMap<>();

                for (int j = 0; j < i; j++) {
                    ExcelCellType cellType = excel.indexTypeMap.get(j);
                    String key = excel.colName(j);
                    if(key == null){
                        continue;
                    }

                    switch (cellType) {
                        case FLOAT:
                            colDataMap.put(key, rowData.float_(key));
                            break;
                        case INT:
                            colDataMap.put(key, rowData.int_(key));
                            break;
                        case LONG:
                            colDataMap.put(key, rowData.long_(key));
                            break;
                        case JSON:
                            colDataMap.put(key, rowData.obj(key, Map.class));
                            break;
                        case DATE:
                            colDataMap.put(key, rowData.date(key));
                            break;
                        case STRING:
                            colDataMap.put(key, rowData.str(key));
                            break;
                        case LIST:
                            colDataMap.put(key, rowData.obj(key, LinkedList.class));
                            break;
                    }
                }

                sheetData.put(id, colDataMap);
            });

            builder.put(sheet, sheetData);
        }
        map = builder.build();
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
