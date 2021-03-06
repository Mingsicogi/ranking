package mins.study.ranking.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisFuture;
import lombok.RequiredArgsConstructor;
import mins.study.ranking.app.repository.UserRepository;
import mins.study.ranking.app.vo.User;
import mins.study.ranking.common.cd.ExceptionCd;
import mins.study.ranking.common.dto.ExceptionDTO;
import mins.study.ranking.common.exception.NotFoundDataException;
import mins.study.ranking.common.service.RedisCacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/rankData/manage/onRedis")
@RequiredArgsConstructor
public class RedisRankDataManage {

    private final UserRepository userRepository;
    private final RedisCacheService redisCommonService;
    private final ObjectMapper objectMapper;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> add(@RequestBody Integer seqId) {
        User user = userRepository.findBySeqId(seqId).orElseThrow(NotFoundDataException::new);
        redisCommonService.put(user.getUsername(), user);
        return ResponseEntity.ok("Save on redis");
    }

    @PutMapping(value = "/asReactive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> addAsReactive(@RequestBody Integer seqId) {
        User user = userRepository.findBySeqId(seqId).orElseThrow(NotFoundDataException::new);
        Mono<Long> result = redisCommonService.putAsReactive(user.getUsername(), user).orElseThrow(() -> new RuntimeException("Occur system error while processing in redis."));
        return result.flatMap(value -> {
            if(value != null && value > 0) {
                return Mono.just(ResponseEntity.ok("Save on redis!!(Reactive)"));
            }

            return Mono.just(ResponseEntity.badRequest().body("Fail saving data on redis..."));
        });
    }

//    @PutMapping(value = "/all/bySetDataType", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> bySetDataType() {
//        List<User> all = userRepository.findAll();
//        redisCommonService.bulkPut("ranking", all.stream().map(User::getUsername).collect(toList()), all.stream().map(User::getScore).collect(toList()));
//        return ResponseEntity.ok("Save rankingData on redis");
//    }

//    @PutMapping(value = "/all/bySortedSetDataType", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> bySortedSetDataType() {
//        List<User> all = userRepository.findAll();
//        redisCommonService.bulkPutBySortedSet("rankingForSorting", all.stream().map(User::getScore).collect(toList()), new ArrayList<>(all));
//        return ResponseEntity.ok("Save rankingData on redis");
//    }

    @GetMapping("/top100")
    public ResponseEntity<Object> top100() {
        List<byte[]> top100UserByteArr = redisCommonService.getTop100("rankingForSorting").orElseThrow(NotFoundDataException::new);

        List<User> collect = top100UserByteArr.stream().map(user -> {
            try {
                return objectMapper.readValue(user, User.class);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }).collect(toList());

        return ResponseEntity.ok(collect);
    }

    @GetMapping("/getOne")
    public ResponseEntity<Object> getOne(String username) throws IOException {
        Optional<byte[]> value = redisCommonService.getValue(username);

        return value.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(ExceptionCd.NOT_FOUND_DATA))
                : ResponseEntity.ok(objectMapper.readValue(value.get(), User.class));
    }

    @GetMapping("/getOne/byAsync")
    public ResponseEntity<Object> getOneByAsync(String username) throws IOException, ExecutionException, InterruptedException {
        RedisFuture<byte[]> redisFuture = redisCommonService.getValueAsAsync(username);

        AtomicReference<User> result = new AtomicReference<>();
        redisFuture.toCompletableFuture().whenComplete((user, e) -> {
            try {
                result.set(objectMapper.readValue(user, User.class));
                System.out.println("result = " + result.toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        return result.get() == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(ExceptionCd.NOT_FOUND_DATA))
                : ResponseEntity.ok(result.get());
    }

    @GetMapping("/getOne/byReactive")
    public Mono<User> getOneByReactive(String username) {
        return redisCommonService.getValueAsReactive(username).orElseThrow(NotFoundDataException::new).map(byteUser -> {
            try {
                System.out.println("Check point 1");
                return objectMapper.readValue(byteUser, User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new User();
        });
    }
}
