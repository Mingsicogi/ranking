package mins.study.ranking.app.controller;

import lombok.RequiredArgsConstructor;
import mins.study.ranking.app.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rankData/manage/onMongo")
@RequiredArgsConstructor
public class MongoRankDataManage {

    private final UserRepository userRepository;

    @GetMapping("/findAll")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}