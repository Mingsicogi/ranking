package mins.study.ranking.app.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;

@Getter @Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    private String id;

    private Integer seqId;
    private String username;
    private BigInteger score;

    public User(String username, BigInteger score) {
        this.username = username;
        this.score = score;
    }

    public User(Integer seqId, String username, BigInteger score) {
        this.seqId = seqId;
        this.username = username;
        this.score = score;
    }
}
