package mins.study.ranking.common.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RedisCommonService {

    void put(Object key, Object value);

    void bulkPut(Object key, List<Object> keyList, List<Object> valueList);

    void bulkPutBySortedSet(Object key, List<Object> scoreList, List<Object> valueList);

    Optional<List<byte[]>> getTop100(Object key);
}
