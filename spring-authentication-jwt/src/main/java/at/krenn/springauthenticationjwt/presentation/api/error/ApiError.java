package at.krenn.springauthenticationjwt.presentation.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class ApiError implements Serializable {
    private int status;
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" )
    private OffsetDateTime timestamp;
    private String message;
    private String debugMessage;


    private ApiError() {
        timestamp = OffsetDateTime.now();
    }

    public ApiError( HttpStatus status ) {
        this();
        this.status = status.value();
    }

    public ApiError( HttpStatus status, String message ) {
        this();
        this.status = status.value();
        this.message = message;
    }

    public ApiError( HttpStatus status, Throwable ex ) {
        this();
        this.status = status.value();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError( HttpStatus status, String message, Throwable ex ) {
        this();
        this.status = status.value();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
