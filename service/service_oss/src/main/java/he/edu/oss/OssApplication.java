package he.edu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 12:09 2021/2/23
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"he.edu"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
