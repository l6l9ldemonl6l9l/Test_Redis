package springbootrediscache;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springbootrediscache.models.User;

@Configuration
public class AppConfig {
    @Resource
    private Environment environment;
    @Value("${spring.datasource.username}")
    private String datasourceUsernameKey;

    @Value("${spring.datasource.password}")
    private String datasourcePasswordKey;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisTemplate<String, User> redisTemplate() {
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
    @Bean
    public DriverManagerDataSource getDataSource() {
        final DriverManagerDataSource  ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(datasourceUsernameKey);
        ds.setUsername(datasourcePasswordKey);
        ds.setPassword(datasourceUrl);
        return ds;
    }
  /**  @Bean
    public DriverManagerDataSource getDataSource() {
        final DriverManagerDataSource  ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/test");
        ds.setUsername("root");
        ds.setPassword("password");
        return ds;
    }*/
}
