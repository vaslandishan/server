package ir.server.vaslandishan.service;

import ir.server.vaslandishan.exception.FileStorageException;
import ir.server.vaslandishan.exception.MyFileNotFoundException;
import ir.server.vaslandishan.models.FileUpload;
import ir.server.vaslandishan.property.FileStorageProperties;
import ir.server.vaslandishan.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

    }

    public FileUpload storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());        

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // save file to mySQL db
            try {
                
                FileUpload filemode = new FileUpload();
                filemode.setFileName(file.getOriginalFilename());
                filemode.setData(file.getBytes());
                return fileUploadRepository.save(filemode);
                //return "File uploaded successfully! -> filename = " + file.getOriginalFilename();
            } catch (	Exception ex) {
                throw new FileStorageException("Could not store file to database" + file.getBytes().toString() + ". Please try again!> " + ex.toString());
            }    

            //return null;
            
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public FileUpload getFileById(Long fileId)
    {
        FileUpload fileUpload = this.fileUploadRepository.getOne(fileId);
        return  fileUpload;

    }
}
