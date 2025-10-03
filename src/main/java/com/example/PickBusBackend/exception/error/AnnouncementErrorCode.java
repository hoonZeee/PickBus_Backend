package com.example.PickBusBackend.exception.error;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum AnnouncementErrorCode implements ErrorCode {
    ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 공지사항입니다.")
    ;

    private final HttpStatus status;

    @Getter
    private final String message;

    AnnouncementErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }
}
