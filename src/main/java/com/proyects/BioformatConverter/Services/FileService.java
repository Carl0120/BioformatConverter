package com.proyects.BioformatConverter.Services;

import com.proyects.BioformatConverter.Domain.FastaToFastqConverter;
import com.proyects.BioformatConverter.Domain.FileCreator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileService implements IFileService {

    private final Path inputPath = Paths.get("files/input");
    private final Path outputPath = Paths.get("files/output");

    @Override
    public String convert(MultipartFile multipartFile) throws IOException {

        File inputFile = FileCreator.createInputFile(Objects.requireNonNull(multipartFile.getOriginalFilename()), this.inputPath);
        multipartFile.transferTo(inputFile);
        File outputFile = FileCreator.createOutputFile(multipartFile.getOriginalFilename(),this.outputPath);

        FastaToFastqConverter.convertFile(inputFile,outputFile);

            return outputFile.getName();
    }

    @Override
    public Resource load(String fileName) throws MalformedURLException {
        Path filePath = outputPath.resolve(fileName);
        return new UrlResource(filePath.toUri());
    }


}
