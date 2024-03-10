package com.proyects.BioformatConverter.Domain;

import com.proyects.BioformatConverter.Configurations.Enums.Extension;
import com.proyects.BioformatConverter.Configurations.Exceptions.InvalidFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class FileCreator {

    public static File createInputFile(String name, Path inputPath) throws IOException {

        String extencion = name.substring(name.lastIndexOf("."));

        if (Objects.equals(extencion, Extension.FASTALong.get()) || Objects.equals(extencion, Extension.FASTAShort.get())){

            Path filePath = inputPath.resolve(name);
            return new File(filePath.toUri());
        }
        else {
            throw new InvalidFileException("El archivo no posee una extencion valida");
        }
    }

    public static File createOutputFile(String name, Path outputPath) {

        int index = name.lastIndexOf(".");
        name = name.substring(0,index).concat(Extension.FASTQShort.get());

        Path filePath = outputPath.resolve(name);
        return new File(filePath.toUri());
    }
}
