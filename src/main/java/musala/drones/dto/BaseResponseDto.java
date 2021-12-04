package musala.drones.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@Data
public class BaseResponseDto {
    private int code;
    private String message;
    private Object data;
}
