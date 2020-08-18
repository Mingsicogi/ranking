package mins.study.ranking;

import mins.study.ranking.app.repository.UserRepository;
import mins.study.ranking.app.vo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Configuration
//@RequiredArgsConstructor
public class InitializeConfiguration {

    private final UserRepository userRepository;

    @Value("${sample.data.count}")
    private Integer sampleCount;

    public InitializeConfiguration(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initDataSetting() {
        List<User> sampleUserList = Stream.iterate(1, count -> count + 1).limit(sampleCount)
                .map(count -> new User(count, RandomStringUtils.randomAlphabetic(10) + "_" + count, new BigInteger(RandomStringUtils.randomNumeric(10, 15)))).collect(Collectors.toList());

        userRepository.saveAll(sampleUserList);
    }
}
