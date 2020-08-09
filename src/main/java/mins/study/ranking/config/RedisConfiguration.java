package mins.study.ranking.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisConnectionException;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedisConfiguration {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private Integer port;

    @Value("${redis.db}")
    private Integer dbNo;

    @Bean
    public RedisClient redisClient() {
        RedisURI redisURI = RedisURI.builder().withHost(host).withPort(port).withDatabase(dbNo).build();
        return RedisClient.create(redisURI);
    }

    @Bean
    public StatefulRedisConnection<byte[], byte[]> statefulRedisConnection(RedisClient redisClient) {
        StatefulRedisConnection<byte[], byte[]> connect = null;
        try {
            connect = redisClient.connect(redisCodec());
            log.info("### Redis Connection Success!! ###");

        } catch (RedisConnectionException e) {
            log.warn("### Redis Connection Fail... Please check redis configuration or redis server.");
        }

        return connect;
    }


    private RedisCodec<byte[], byte[]> redisCodec() {
        return new ByteArrayCodec();
    }
}
