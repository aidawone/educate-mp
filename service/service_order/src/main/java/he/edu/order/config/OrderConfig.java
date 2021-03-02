package he.edu.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 14:31 2021/3/2
 */
@Configuration
@ComponentScan(basePackages = {"he.edu"})
@MapperScan("he.edu.order.mapper")
public class OrderConfig {
}
