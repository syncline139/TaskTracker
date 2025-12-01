package com.tasktracker.backend.exception.сustom;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException() {
        super("Refresh токен не найден в хранилище");
    }
}
