package com.proyects.BioformatConverter.Api.ExceptionsHandler;

import com.proyects.BioformatConverter.Configurations.Exceptions.InvalidFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@RestControllerAdvice
public class FileUploadExceptionsAdvice {

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body("El archivo es demaciado grande");
    }
    @ExceptionHandler({MalformedURLException.class})
    public ResponseEntity<String> malformedURLException(MalformedURLException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body("Error interno del servidor");
    }
    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<String> fileNotFoundException(FileNotFoundException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Archivo no encontrado");
    }
    @ExceptionHandler({InvalidFileException.class})
    public ResponseEntity<String> invalidFileException(InvalidFileException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
