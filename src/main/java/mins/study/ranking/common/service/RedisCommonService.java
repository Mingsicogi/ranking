package mins.study.ranking.common.service;

import io.lettuce.core.RedisFuture;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RedisCommonService {

    void put(Object key, Object value);

    void bulkPut(Object key, List<Object> keyList, List<Object> valueList);

    void bulkPutBySortedSet(Object key, List<Object> scoreList, List<Object> valueList);

    Optional<List<byte[]>> getTop100(Object key);

    RedisFuture<byte[]> getAsync(Object key);

    Optional<byte[]> getValue(Object key);
}
