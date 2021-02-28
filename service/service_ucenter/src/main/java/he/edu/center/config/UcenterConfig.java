package he.edu.center.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置類
 *
 * @Author: aidawone
 * @Description:
 * @Date: Create in 13:04 2021/2/7
 */
@Configuration
@MapperScan("he.edu.center.mapper")
@ComponentScan(basePackages = {"he.edu"})
public class UcenterConfig {
}
