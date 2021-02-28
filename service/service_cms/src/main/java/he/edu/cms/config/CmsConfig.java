package he.edu.cms.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 18:09 2021/2/27
 */
@Configuration
@MapperScan("he.edu.cms.mapper")
@ComponentScan(basePackages = {"he.edu"})
public class CmsConfig {

}
