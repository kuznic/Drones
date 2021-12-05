package musala.drones.exception;

import java.util.ArrayList;
import java.util.List;
import lombok.var;
import musala.drones.dto.BaseResponseDto;
import musala.drones.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice()
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        List<String> details = new ArrayList<>();
        for(ObjectError error: ex.getBindingResult().getAllErrors())
        {
            details.add(error.getDefaultMessage());
        }

        var apiError = new ApiError("Validation Failed", details);
        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {

        var baseResponseDto = new BaseResponseDto();
        baseResponseDto.setCode(HttpStatus.BAD_REQUEST.value());
        baseResponseDto.setMessage(ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(baseResponseDto);
    }
}