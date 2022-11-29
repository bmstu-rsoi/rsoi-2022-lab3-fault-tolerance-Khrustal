package ru.khrustal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    public MessageDto(String message) {
        this.message = message;
    }
    private String message;
    private String details;
}
