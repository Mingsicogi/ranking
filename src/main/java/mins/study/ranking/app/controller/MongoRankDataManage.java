package mins.study.ranking.app.controller;

import lombok.RequiredArgsConstructor;
import mins.study.ranking.app.repository.UserRepository;
import mins.study.ranking.app.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/rankData/manage/onMongo")
@RequiredArgsConstructor
public class MongoRankDataManage {

    private final UserRepository userRepository;

    @GetMapping("/findAll")
    public ResponseEntity<Flux<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}