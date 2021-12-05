package musala.drones.exception;

import lombok.var;
import musala.drones.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request)
    {
        List<String> errors = new ArrayList<String>();

        for(FieldError error: ex.getBindingResult().getFieldErrors())
        {
            errors.add(error.getField() + " -> " + error.getDefaultMessage());
        }

        for(ObjectError error: ex.getBindingResult().getGlobalErrors())
        {
            errors.add(error.getObjectName() + " -> " + error.getDefaultMessage());
        }

        var apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getLocalizedMessage());
        apiError.setErrors(errors);

        return handleExceptionInternal(ex,apiError,headers,apiError.getStatus(),request);

    }
}
