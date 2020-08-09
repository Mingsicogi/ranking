package mins.study.ranking.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCommonServiceImpl implements RedisCommonService {

    private final StatefulRedisConnection<byte[], byte[]> statefulRedisConnection;

    private final ObjectMapper objectMapper;

    @Override
    public void put(Object key, Object value) {
        try {
            statefulRedisConnection.sync().set(objectMapper.writeValueAsBytes(key), objectMapper.writeValueAsBytes(value));
        } catch (JsonProcessingException e) {
            log.warn("#### jackson lib로 object -> byte[] 를 parsing하는 과정에서 발생한 예외.");
        }
    }
}
