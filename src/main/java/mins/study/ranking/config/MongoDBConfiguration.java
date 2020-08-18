package mins.study.ranking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"mins.study.ranking.app.repository"})
public class MongoDBConfiguration {

}
