package com.pragma.powerup.domain.spi;

public interface IAuthPasswordEncoderPort {
    String encodePassword(String decodedPassword);

    String decodePassword(String encodedPassword);
}
