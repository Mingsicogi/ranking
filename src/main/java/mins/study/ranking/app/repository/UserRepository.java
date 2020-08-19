package mins.study.ranking.app.repository;

import mins.study.ranking.app.vo.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findBySeqId(Integer seqId);
}
