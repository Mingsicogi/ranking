package mins.study.ranking.common.service;

import io.lettuce.core.RedisFuture;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface RedisCacheService {

    void put(Object key, Object value);

    Optional<Mono<Long>> putAsReactive(Object key, Object value);

    void bulkPut(Object key, List<Object> keyList, List<Object> valueList);

    void bulkPutBySortedSet(Object key, List<Object> scoreList, List<Object> valueList);

    Optional<List<byte[]>> getTop100(Object key);

    RedisFuture<byte[]> getValueAsAsync(Object key);

    Optional<byte[]> getValue(Object key);

    Optional<Mono<byte[]>> getValueAsReactive(Object key);
}
