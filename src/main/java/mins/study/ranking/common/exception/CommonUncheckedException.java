package mins.study.ranking.common.exception;

import lombok.Getter;
import mins.study.ranking.common.cd.ExceptionCd;

@Getter
public abstract class CommonUncheckedException extends RuntimeException {

    private final ExceptionCd exceptionCd;

    public CommonUncheckedException(ExceptionCd exceptionCd) {
        super(exceptionCd.getMsg());
        this.exceptionCd = exceptionCd;
    }

    public CommonUncheckedException(String msg) {
        super(msg);
        this.exceptionCd = ExceptionCd.NOT_DEFINED_EXCEPTION;
    }

    public CommonUncheckedException(ExceptionCd exceptionCd, String msg) {
        super(msg);
        this.exceptionCd = exceptionCd;
    }
}
