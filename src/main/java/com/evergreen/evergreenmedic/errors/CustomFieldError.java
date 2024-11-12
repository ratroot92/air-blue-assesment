package com.evergreen.evergreenmedic.errors;

import lombok.Data;

@Data
public class CustomFieldError {
    private String fieldName;
    private String fieldMessage;
}
