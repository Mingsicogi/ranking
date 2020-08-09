package mins.study.ranking.common.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mins.study.ranking.common.exception.RedisProcessingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

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
        this.commonBulkPutActionValidation(keyList, valueList);

        try {
            Map<byte[], byte[]> redisDataSet = this.commonMakeSetData(keyList, valueList);
            statefulRedisConnection.sync().hmset(objectMapper.writeValueAsBytes(key), redisDataSet);
        } catch (JsonProcessingException e) {
            log.warn("#### jackson lib로 object -> byte[] 를 parsing하는 과정에서 발생한 예외.");
        }
    }

    @Override
    public void bulkPutBySortedSet(Object key, List<Object> scoreList, List<Object> valueList) {
        this.commonBulkPutActionValidation(scoreList, valueList);

        try {
            List<ScoredValue<byte[]>> redisDataSet = new ArrayList<>(10_000);
            for(int i =0; i < scoreList.size(); i++) {
                redisDataSet.add(ScoredValue.fromNullable(((BigInteger)scoreList.get(i)).doubleValue(), objectMapper.writeValueAsBytes(valueList.get(i))));
            }

            statefulRedisConnection.sync().zadd(objectMapper.writeValueAsBytes(key), redisDataSet.toArray());
        } catch (JsonProcessingException e) {
            log.warn("#### jackson lib로 object -> byte[] 를 parsing하는 과정에서 발생한 예외.");
        }
    }

    @Override
    public Optional<List<byte[]>> getTop100(Object key) {
        try {
            List<byte[]> top100 = statefulRedisConnection.sync().zrevrange(objectMapper.writeValueAsBytes(key), 0, 100);
            return Optional.ofNullable(top100);
        } catch (JsonProcessingException e) {
            log.warn("#### jackson lib로 object -> byte[] 를 parsing하는 과정에서 발생한 예외.");
        }

        return Optional.empty();
    }

    private void commonBulkPutActionValidation(List<Object> keyList, List<Object> valueList) {
        if (keyList.isEmpty() || valueList.isEmpty()) {
            throw new RedisProcessingException("key list size or value list size cannot be empty.");
        }

        if (keyList.size() != valueList.size()) {
            throw new RedisProcessingException("key list size and value list size must be same.");
        }
    }

    private Map<byte[], byte[]> commonMakeSetData(List<Object> keyList, List<Object> valueList) throws JsonProcessingException {
        Map<byte[], byte[]> redisDataSet = new HashMap<>();
        for (int i = 0; i < keyList.size(); i++) {
            redisDataSet.put(objectMapper.writeValueAsBytes(keyList.get(i)), objectMapper.writeValueAsBytes(valueList.get(i)));
        }
        return redisDataSet;
    }
}
