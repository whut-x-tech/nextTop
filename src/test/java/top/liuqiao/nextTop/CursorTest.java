package top.liuqiao.nextTop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.liuqiao.nextTop.common.ErrorCode;
import top.liuqiao.nextTop.exception.BusinessException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuqiao
 * @since 2025-06-23
 */
@SpringBootTest
public class CursorTest {

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Test
    void test() {
        Cursor<String> scan = null;
        try {
            scan = redisTemplate
                    .scan(ScanOptions.scanOptions()
                            .match("1933521385370828800" + ":*")
                            .count(100)
                            .build());
            List<String> idList = new ArrayList<>();
            while (scan.hasNext()) {
                scan.forEachRemaining(idList::add);
            }
            List<byte[]> bList = redisTemplate.opsForValue().multiGet(idList)
                    .stream()
                    .map(s -> s.getBytes(StandardCharsets.UTF_8))
                    .collect(Collectors.toList());

            bList.forEach((byte[] x) -> System.out.println(Arrays.toString(x)));

        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "redis 错误");
        } finally {
            assert scan != null;
            scan.close();
        }
    }
}
