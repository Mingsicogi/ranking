package mins.study.ranking;

import lombok.RequiredArgsConstructor;
import mins.study.ranking.app.repository.UserRepository;
import mins.study.ranking.app.vo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Configuration
@RequiredArgsConstructor
public class InitializeConfiguration {

    private final UserRepository userRepository;

    @Value("${sample.data.count}")
    private Integer sampleCount;

    @PostConstruct
    private void initDataSetting() {
        List<User> sampleUserList = Stream.iterate(1, count -> count + 1).limit(sampleCount)
                .map(count -> new User(RandomStringUtils.randomAlphabetic(10) + "_" + count, new BigInteger(RandomStringUtils.randomNumeric(3, 20)))).collect(Collectors.toList());

        userRepository.saveAll(sampleUserList);
    }
}
