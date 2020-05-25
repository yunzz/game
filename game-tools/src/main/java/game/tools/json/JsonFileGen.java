package game.tools.json;

import game.base.excel.MapExcelConfig;
import game.base.uitools.event.EventManager;
import game.base.utils.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author Yunzhe.Jin
 * 2020/4/9 18:26
 */
public class JsonFileGen {

    public static void gen(List<File> fileList, File dest) throws IOException {

        for (File file : fileList) {
            MapExcelConfig f = new MapExcelConfig(file);
            f.load();
            Map<String, Object> map = f.getMap();

            for (Map.Entry<String, Object> e : map.entrySet()) {

                String fileName = file.getName().substring(0, file.getName().indexOf(".")) + "_" + e.getKey() + ".json";

                Path path = Paths.get(dest.getPath(), fileName);

                Files.write(path, JsonUtil.toJsonPrettyString(e.getValue()).getBytes());
                EventManager.fireConsoleEvent(String.format("生成文件：%s", path.toString()));
            }
            f.load();
            f.afterLoad();
        }

    }
}
