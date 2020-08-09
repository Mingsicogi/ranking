package mins.study.ranking.app.controller;

import lombok.RequiredArgsConstructor;
import mins.study.ranking.app.repository.UserRepository;
import mins.study.ranking.app.vo.User;
import mins.study.ranking.common.exception.NotFoundDataException;
import mins.study.ranking.common.service.RedisCommonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rankData/manage/onRedis")
@RequiredArgsConstructor
public class RedisRankDataManage {

    private final UserRepository userRepository;
    private final RedisCommonService redisCommonService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@RequestBody Integer seqId) {
        User user = userRepository.findBySeqId(seqId).orElseThrow(NotFoundDataException::new);
        redisCommonService.put(user.getUsername(), user.getScore());
        return ResponseEntity.ok("Save on redis");
    }
}
