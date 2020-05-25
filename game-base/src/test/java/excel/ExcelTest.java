package excel;

import game.base.excel.ExcelFile;
import org.joda.time.LocalDateTime;
import org.junit.Test;

/**
 * @author Yunzhe.Jin
 * 2020/3/28 13:31
 */
public class ExcelTest {

    @Test
    public void test1() {
        ExcelFile ef = new ExcelFile("test.xlsx");
        ef.parse("Sheet1", parse -> {
            System.out.println("-------------------------------");
            Integer game1 = parse.int_("game1");
            Double game2 = parse.double_("game2");
            String game3 = parse.str("game3");
            LocalDateTime game4 = parse.date("game4");
            Long game5 = parse.long_("game5");
            Float game6 = parse.float_("game6");
            System.out.println(game1);
            System.out.println(game2);
            System.out.println(game3);
            System.out.println(game4);
            System.out.println(game5);
            System.out.println(game6);

        });

        ef.close();
    }
}
