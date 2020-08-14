package mins.study.ranking.common.exception;

import mins.study.ranking.common.cd.ExceptionCd;

public class BadParameterException extends CommonUncheckedException {
    private final static ExceptionCd exceptionCd = ExceptionCd.INVALID_PARAMETER;

    public BadParameterException(String msg) {
        super(exceptionCd, msg);
    }

    public BadParameterException() {
        super(exceptionCd);
    }
}
