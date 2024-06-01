package com.owl.auth_service.repository.service.user;

public class UsernameIsExistException extends RuntimeException {
    public UsernameIsExistException() {
        super("This username is used!");
    }
}