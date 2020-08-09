package mins.study.ranking.common.exception;

import mins.study.ranking.common.cd.ExceptionCd;

public class RedisProcessingException extends CommonUncheckedException {
    private final static ExceptionCd exceptionCd = ExceptionCd.REDIS_PROCESSING_EXCEPTION;

    public RedisProcessingException(String msg) {
        super(exceptionCd, msg);
    }

    public RedisProcessingException() {
        super(exceptionCd);
    }
}
