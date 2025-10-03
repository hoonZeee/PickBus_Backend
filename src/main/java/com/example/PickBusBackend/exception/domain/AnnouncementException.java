package com.example.PickBusBackend.exception.domain;

import com.example.PickBusBackend.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class AnnouncementException extends BusinessException {
    public AnnouncementException(ErrorCode code) {
        super(code);
    }
}
