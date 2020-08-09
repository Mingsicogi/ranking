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

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/rankData/manage/onRedis")
@RequiredArgsConstructor
public class RedisRankDataManage {

    private final UserRepository userRepository;
    private final RedisCommonService redisCommonService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@RequestBody Integer seqId) {
        User user = userRepository.findBySeqId(seqId).orElseThrow(NotFoundDataException::new);
        redisCommonService.put(user.getUsername(), user);
        return ResponseEntity.ok("Save on redis");
    }

    @PutMapping(value = "/all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putUsernameAndScore() {
        List<User> all = userRepository.findAll();
        redisCommonService.bulkPut("ranking", all.stream().map(User::getUsername).collect(toList()), all.stream().map(User::getScore).collect(toList()));
        return ResponseEntity.ok("Save rankingData on redis");
    }
}
