package com.proj.authservice.Exception;

public class TokenExpiredException extends Exception{
    public TokenExpiredException(String message) {
        super(message);
    }
}
