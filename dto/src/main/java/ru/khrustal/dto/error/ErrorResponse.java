package ru.khrustal.dto.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private List<ErrorDescription> errors;

    @JsonIgnore
    public void addError(ErrorDescription error) {
        this.errors.add(error);
    }
}
