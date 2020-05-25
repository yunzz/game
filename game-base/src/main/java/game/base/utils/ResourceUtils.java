package game.base.utils;

import game.base.log.Logs;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 2020/3/18 17:46
 */
public class ResourceUtils {

    public static File getFile(String path) throws FileNotFoundException {

        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
        String description = "class path resource [" + path + "]";

        if (url == null) {
            throw new FileNotFoundException(description +
                    " cannot be resolved to absolute file path because it does not exist");
        }
        return getFile(url, description);
    }

    public static URL getURL(String path) throws FileNotFoundException {

        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        URL url = (cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path));
        String description = "class path resource [" + path + "]";

        if (url == null) {
            throw new FileNotFoundException(description +
                    " cannot be resolved to absolute file path because it does not exist");
        }
        return url;
    }

    public static Set<URL> resource(String path) throws IOException {
        Set<URL> result = new LinkedHashSet<>(16);
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(path) : ClassLoader.getSystemResources(path));
        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            result.add(url);

            Logs.common.info("找到配置文件:{}", url.toString());
        }
        return result;
    }


    private static File getFile(URL resourceUrl, String description) {
        try {
            return new File(toURI(resourceUrl).getSchemeSpecificPart());
        } catch (URISyntaxException ex) {
            return new File(resourceUrl.getFile());
        }
    }

    public static URI toURI(URL url) throws URISyntaxException {
        return toURI(url.toString());
    }

    public static URI toURI(String location) throws URISyntaxException {
        return new URI(StringUtils.replace(location, " ", "%20"));
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        File file = getFile("app.yaml");
        System.out.println(file.exists());
        File file1 = getFile("config/test.yaml");
        System.out.println(new String(Files.readAllBytes(Paths.get(getURL("app.yaml").toURI()))));


    }
}
