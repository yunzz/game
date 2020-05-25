package game.base.utils;

import com.google.common.base.Charsets;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static game.base.utils.ResourceUtils.getURL;

/**
 * @author yzz
 * 2020/3/18 18:17
 */
public class IoUtils {


    public static String readAll(String filePath) throws Exception {
        return new String(Files.readAllBytes(Paths.get(getURL(filePath).toURI())), Charsets.UTF_8);
    }

    public static String readAll(URL filePath) throws Exception {
        if ("file".equals(filePath.getProtocol())) {

            return new String(Files.readAllBytes(Paths.get(filePath.toURI())), Charsets.UTF_8);
        } else if ("jar".equals(filePath.getProtocol())) {

            InputStream inputStream = filePath.openConnection().getInputStream();
            BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = inputStreamReader.readLine()) != null) {
                builder.append(line);
                builder.append('\n');

            }
            return builder.toString();
        }

        return null;
    }

    public static String readAll(File file) throws Exception {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                builder.append(line);
            }
            return builder.toString();
        }
    }
}
