package he.edu.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
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
@MapperScan("he.edu.eduservice.mapper")
@ComponentScan(basePackages = {"he.edu"})
public class EduConfig {
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    @Bean
    public PaginationInterceptor interceptor() {
        return new PaginationInterceptor();
    }
}
