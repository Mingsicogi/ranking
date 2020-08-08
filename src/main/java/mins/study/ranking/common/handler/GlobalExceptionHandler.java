package mins.study.ranking.common.handler;

import lombok.extern.slf4j.Slf4j;
import mins.study.ranking.common.dto.ExceptionDTO;
import mins.study.ranking.common.exception.NotFoundDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundDataException.class)
    public ResponseEntity<Object> notFoundDataException(NotFoundDataException e) {
        log.debug("### exception code : {}, exception message : {}", e.getExceptionCd(), e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionDTO(e.getExceptionCd(), e.getMessage()));
    }
}
