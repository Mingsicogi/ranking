package mins.study.ranking.common.service;

import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import mins.study.ranking.app.vo.User;
import mins.study.ranking.common.exception.BadParameterException;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Service("redisSortedSetService")
public class RedisSortedSetService {

    private final StatefulRedisConnection<byte[], byte[]> statefulRedisConnection;
    private RedisReactiveCommands<byte[], byte[]> redisCommand;

    public RedisSortedSetService(StatefulRedisConnection<byte[], byte[]> statefulRedisConnection) {
        this.statefulRedisConnection = statefulRedisConnection;
        redisCommand = statefulRedisConnection.reactive();
    }

    public Mono<Long> add(byte[] key, Mono<List<ScoredValue<byte[]>>> value) {
        if(key == null || value == null) {
            throw new BadParameterException();
        }

        value.publish()
    }

    public Flux<byte[]> get(byte[] key, Integer start, Integer end) {
        return redisCommand.zrange(key, start, end);
    }
}
