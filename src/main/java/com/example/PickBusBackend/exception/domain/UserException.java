package com.example.PickBusBackend.exception.domain;

import com.example.PickBusBackend.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends BusinessException {
    public UserException(ErrorCode code) {
        super(code);
    }
}
