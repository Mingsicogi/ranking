package mins.study.ranking.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.AllArgsConstructor;
import mins.study.ranking.app.vo.User;
import mins.study.ranking.config.ObjectMapperConfiguration;
import mins.study.ranking.config.RedisConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RedisConfiguration.class, ObjectMapperConfiguration.class})
@TestPropertySource(properties = {"redis.port=6379", "redis.host=localhost", "redis.db=0"})
class RedisSortedSetServiceTest {

    @Autowired
    StatefulRedisConnection<byte[], byte[]> statefulRedisConnection;

    ObjectMapper objectMapper = new ObjectMapper();

    RedisSortedSetService redisSortedSetService;

    @BeforeEach
    void init() {
        redisSortedSetService = new RedisSortedSetService(statefulRedisConnection);
    }

    @Test
    void add() throws Exception {
        // GIVE
        ScoredValue<User> scoredValue1 = ScoredValue.just(1, new User("minssogi", BigInteger.valueOf(100)));
        ScoredValue<User> scoredValue2 = ScoredValue.just(2, new User("minssogi2", BigInteger.valueOf(101)));

//        Mono<Long> result = redisSortedSetService.add(objectMapper.writeValueAsBytes("test"), Arrays.asList(scoredValue1, scoredValue2));
        //        .doOnNext(aLong -> System.out.println("### result : " + aLong))
//        .doOnTerminate(() -> System.out.println("Complete!!!")).subscribe()
//        ;

//        StepVerifier.create(result)
//                .expectNext(1)
//                .expectFusion(1)
//                .verifyComplete();

    }

    @Test
    void get() {
    }
}