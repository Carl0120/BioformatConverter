package com.proyects.BioformatConverter.Configurations.Exceptions;

import java.io.IOException;

public class InvalidFileException extends IOException {
    public InvalidFileException(String message) {
        super(message);
    }
}
