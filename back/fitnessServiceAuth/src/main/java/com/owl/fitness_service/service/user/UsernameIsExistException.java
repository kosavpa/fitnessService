package com.owl.fitness_service.service.user;

public class UsernameIsExistException extends RuntimeException {
    public UsernameIsExistException() {
        super("This username is used!");
    }
}