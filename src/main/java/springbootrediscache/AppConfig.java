package springbootrediscache;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import springbootrediscache.models.User;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Autowired
    private Environment env;
    @Resource
    private Environment environment;
    @Value("${spring.datasource.username}")
    private String datasourceUsernameKey;

    @Value("${spring.datasource.password}")
    private String datasourcePasswordKey;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName(env.getProperty("redis.host"));
        jedisConnectionFactory.setPort(env.getProperty("redis.port", Integer.class));
        return jedisConnectionFactory;
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
        ds.setUrl(datasourceUrl);
        ds.setUsername(datasourceUsernameKey);
        ds.setPassword(datasourcePasswordKey);
        return ds;
    }
}
