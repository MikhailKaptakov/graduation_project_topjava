package ru.graduation_project_topjava.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    APP_ERROR("appError", HttpStatus.INTERNAL_SERVER_ERROR),
    //  http://stackoverflow.com/a/22358422/548473
    DATA_NOT_FOUND("dataNotFound", HttpStatus.UNPROCESSABLE_ENTITY),
    CONDITION_FAILED("conditionFailed", HttpStatus.UNPROCESSABLE_ENTITY),
    DATA_ERROR("dataError", HttpStatus.CONFLICT),
    VALIDATION_ERROR("validationError", HttpStatus.UNPROCESSABLE_ENTITY),
    WRONG_REQUEST("wrongRequest", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final HttpStatus status;

    ErrorType(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorMessage() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}