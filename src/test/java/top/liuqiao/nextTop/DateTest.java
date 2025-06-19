package top.liuqiao.nextTop;

import java.time.Instant;
import java.util.Date;

/**
 * @author liuqiao
 * @since 2025-06-19
 */
public class DateTest {
    public static void main(String[] args) {
        System.out.println(Date.from(Instant.now()).toString());
        System.out.println(System.currentTimeMillis());
    }
}
