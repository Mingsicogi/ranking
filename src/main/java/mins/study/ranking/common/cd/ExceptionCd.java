package mins.study.ranking.common.cd;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCd {

    NOT_FOUND_DATA("Not exist data"),
    REDIS_PROCESSING_EXCEPTION("redis "),
    NOT_DEFINED_EXCEPTION("Not defined exception. please ask to manager."),
    INVALID_PARAMETER("Invalid parameter.")
    ;

    private final String msg;
}
