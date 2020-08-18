package mins.study.ranking.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ScoredValue;
import lombok.RequiredArgsConstructor;
import mins.study.ranking.app.repository.UserRepository;
import mins.study.ranking.common.service.RedisSortedSetService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/rankData/manage/onRedis/byReactive")
@RequiredArgsConstructor
public class ReactiveRedisDataManage {

    private final UserRepository userRepository;

    private final RedisSortedSetService redisSortedSetService;

    private final ObjectMapper objectMapper;

    private static byte[] RANKING_DATA_REDIS_KEY;

    @PostConstruct
    public void init() throws JsonProcessingException {
        RANKING_DATA_REDIS_KEY = objectMapper.writeValueAsBytes("rankingData");
    }

    @PostMapping("/loadSampleData")
    public Mono<Long> loadSampleData() {
        return redisSortedSetService.add(RANKING_DATA_REDIS_KEY, userRepository.findAll().stream().map(user -> {
            try {
                return ScoredValue.fromNullable(user.getScore().doubleValue(), objectMapper.writeValueAsBytes(user));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(toList()));
    }
}
