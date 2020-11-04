package mins.study.ranking.common.service;

public interface RedisDatabaseService {

    void save(Object key, Object val);
    Object get(Object key);
}
