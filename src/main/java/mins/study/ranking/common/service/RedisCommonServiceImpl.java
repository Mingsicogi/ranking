package mins.study.ranking.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mins.study.ranking.common.exception.RedisProcessingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public void bulkPut(Object key, List<Object> keyList, List<Object> valueList) {
        if(keyList.isEmpty() || valueList.isEmpty()) {
            throw new RedisProcessingException("key list size or value list size cannot be empty.");
        }

        if(keyList.size() != valueList.size()) {
            throw new RedisProcessingException("key list size and value list size must be same.");
        }

        Map<byte[], byte[]> redisDataSet = new HashMap<>();
        try {
            for(int i = 0; i < keyList.size(); i++) {
                redisDataSet.put(objectMapper.writeValueAsBytes(keyList.get(i)), objectMapper.writeValueAsBytes(valueList.get(i)));
            }
            statefulRedisConnection.sync().hmset(objectMapper.writeValueAsBytes(key), redisDataSet);
        } catch (JsonProcessingException e) {
            log.warn("#### jackson lib로 object -> byte[] 를 parsing하는 과정에서 발생한 예외.");
        }
    }
}
