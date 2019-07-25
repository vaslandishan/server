package ir.server.vaslandishan.controller;

import ir.server.vaslandishan.models.FileUpload;
import ir.server.vaslandishan.payload.UploadFileResponse;
import ir.server.vaslandishan.security.CurrentUser;
import ir.server.vaslandishan.security.UserPrincipal;
import ir.server.vaslandishan.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(value = "/uploadFile", method =RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        
        FileUpload  fileResult = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(file.getOriginalFilename())
                .toUriString();

        return new UploadFileResponse(fileResult.getId(), file.getOriginalFilename(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }



    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


    //@GetMapping("/getFile/{fileId}")
    // @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    // @ResponseBody
    // public ResponseEntity<InputStreamResource> getFile(@RequestParam("fileId") Long fileId)
    //         {
    //             FileUpload item = fileStorageService.getFileById(fileId);
    //             InputStreamResource ipSt = new InputStreamResource(new ByteArrayInputStream( item.getData() ));
    //             return ResponseEntity.ok()
    //                     .contentLength(item.getData().length)
    //                     .contentType(MediaType.parseMediaType("image/jpeg, image/jpg, image/png, image/gif"))
    //                     .body(ipSt);


    //     // response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
    //     // response.getOutputStream().write(item.getData());
    //     // response.getOutputStream().close();
    // }

    // //
    // @RequestMapping(value = "/getFile2", method = RequestMethod.GET)
    // //@GetMapping("/getFile2/{fileId}")
    // public String getId() {
    //     return "true";
    // }

    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public void getFile(@RequestParam("fileId") Long fileId, HttpServletResponse response,HttpServletRequest request) 
              throws ServletException, IOException{


        FileUpload item = fileStorageService.getFileById(fileId);     
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(item.getData());


        response.getOutputStream().close();
    }
}