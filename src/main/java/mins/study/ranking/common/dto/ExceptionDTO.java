package mins.study.ranking.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mins.study.ranking.common.cd.ExceptionCd;

@Getter @Setter
@NoArgsConstructor
public class ExceptionDTO {

    private String msg;
    private ExceptionCd exceptionCd;

    public ExceptionDTO(ExceptionCd exceptionCd) {
        this.msg = exceptionCd.getMsg();
        this.exceptionCd = exceptionCd;
    }

    public ExceptionDTO(String msg) {
        this.msg = msg;
        this.exceptionCd = ExceptionCd.NOT_DEFINED_EXCEPTION;
    }

    public ExceptionDTO(ExceptionCd exceptionCd, String msg) {
        this.msg = msg;
        this.exceptionCd = exceptionCd;
    }
}
