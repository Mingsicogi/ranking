package mins.study.ranking.common.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RedisCommonService {

    void put(Object key, Object value);

    void bulkPut(Object key, List<Object> keyList, List<Object> valueList);
}
