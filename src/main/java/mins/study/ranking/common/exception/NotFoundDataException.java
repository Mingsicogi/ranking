package mins.study.ranking.common.exception;

import mins.study.ranking.common.cd.ExceptionCd;

public class NotFoundDataException extends CommonUncheckedException {

    private final static ExceptionCd exceptionCd = ExceptionCd.NOT_FOUND_DATA;

    public NotFoundDataException(String msg) {
        super(exceptionCd, msg);
    }

    public NotFoundDataException() {
        super(exceptionCd);
    }
}
