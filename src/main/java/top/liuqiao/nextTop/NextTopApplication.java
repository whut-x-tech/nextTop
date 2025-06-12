package top.liuqiao.nextTop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.liuqiao.nextTop.mapper")
public class NextTopApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextTopApplication.class, args);
    }

}
