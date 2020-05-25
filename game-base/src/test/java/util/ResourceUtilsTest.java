package util;

import game.base.utils.ResourceUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author yzz
 * 2020/3/18 18:08
 */
public class ResourceUtilsTest {

    @Test
    public void getFile() throws FileNotFoundException {

        File file = ResourceUtils.getFile("test.yaml");

        System.out.println(file.exists());
    }
}
