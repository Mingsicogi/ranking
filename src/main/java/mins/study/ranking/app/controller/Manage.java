package mins.study.ranking.app.controller;

import lombok.RequiredArgsConstructor;
import mins.study.ranking.app.repository.UserRepository;
import mins.study.ranking.app.vo.User;
import mins.study.ranking.common.exception.NotFoundDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rank/manage")
@RequiredArgsConstructor
public class Manage {

    private final UserRepository userRepository;

    @GetMapping("/findAll/sampleData")
    public ResponseEntity<Object> getAll() {
        List<User> all = userRepository.findAll();

        if(CollectionUtils.isEmpty(all)) {
            throw new NotFoundDataException();
        }

        return ResponseEntity.ok(all);
    }
}