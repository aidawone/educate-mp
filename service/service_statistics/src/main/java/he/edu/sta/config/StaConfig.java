package he.edu.sta.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:55 2021/3/3
 */
@Configuration
@ComponentScan(basePackages = {"he.edu"})
@MapperScan(basePackages = {"he.edu.sta.mapper"})
public class StaConfig {
}
