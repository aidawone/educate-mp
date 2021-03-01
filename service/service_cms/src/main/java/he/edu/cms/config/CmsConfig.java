package he.edu.cms.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
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
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    public PaginationInterceptor interceptor() {
        return new PaginationInterceptor();
    }
}
