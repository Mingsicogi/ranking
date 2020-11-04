package mins.study.ranking.common.service;

import io.lettuce.core.api.StatefulRedisConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisDatabaseServiceImpl implements RedisDatabaseService {

    private final StatefulRedisConnection<byte[], byte[]> statefulRedisConnection;

    @Override
    public void save(Object key, Object val) {
        statefulRedisConnection.sync().
    }

    @Override
    public Object get(Object key) {
        return null;
    }
}
