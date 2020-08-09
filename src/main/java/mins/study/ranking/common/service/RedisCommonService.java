package mins.study.ranking.common.service;

import org.springframework.stereotype.Service;

@Service
public interface RedisCommonService {
    void put(Object key, Object value);
}
