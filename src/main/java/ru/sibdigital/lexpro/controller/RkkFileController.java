package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.model.ClsUser;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegDocRkkFile;
import ru.sibdigital.lexpro.service.FileService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Log4j2
@Controller
public class RkkFileController extends SuperController {

    @Value("${upload.path:/rkk}")
    String uploadingDir;

    @PostMapping(value = "/upload_rkk_files", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> uploadFile(@RequestParam(value = "upload") MultipartFile part, HttpSession session,
                                             @RequestParam(required = false) Long docRkkId){

        ResponseEntity<Object> responseEntity;
        if (Files.notExists(Paths.get(uploadingDir))) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\": \"server\"," +
                            "\"cause\": \"Ошибка сохранения\"}");
        }
        else {
            DocRkk docRkk = (docRkkId == null) ?
                    null : docRkkRepo.findById(docRkkId).orElse(null);

            RegDocRkkFile regDocRkkFile = construct(part, docRkk);

            if (regDocRkkFile != null){
                if (regDocRkkFile.getId() == null) {
                    regDocRkkFile = regDocRkkFileRepo.save(regDocRkkFile);
                    responseEntity = ResponseEntity.ok()
                            .body("{\"cause\": \"Файл успешно загружен\"," +
                                    "\"status\": \"server\"," +
                                    "\"sname\": \"" + regDocRkkFile.getOriginalFileName() + "\"}");
                }else{
                    responseEntity = ResponseEntity.ok()
                            .body("{\"cause\": \"Вы уже загружали этот файл\"," +
                                    "\"status\": \"server\"," +
                                    "\"sname\": \"" + regDocRkkFile.getOriginalFileName() + "\"}");
                }
            }else{
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"status\": \"server\"," +
                                "\"cause\":\"Ошибка сохранения\"}");
            }
        }

        return responseEntity;//ResponseEntity.ok().body(requestService.uploadFile(file));
    }

    private RegDocRkkFile construct(MultipartFile part, DocRkk docRkk){
        RegDocRkkFile rdrf = null;
        try {

            final String absolutePath = Paths.get(uploadingDir).toFile().getAbsolutePath();
            final String filename = docRkk.getId().toString() + "_" + UUID.randomUUID();
            final String originalFilename = part.getOriginalFilename();
            String extension = FileService.getFileExtension(originalFilename);
            File file = new File(String.format("%s/%s%s", absolutePath, filename, extension));
            part.transferTo(file);

            final String fileHash = FileService.getFileHash(file);
            final long size = Files.size(file.toPath());

            final List<RegDocRkkFile> files = regDocRkkFileRepo.findRegDocRkkFileByDocRkkAndHash(docRkk, fileHash);

            if (!files.isEmpty()){
                rdrf = files.get(0);
            }else{
                rdrf = RegDocRkkFile.builder()
                        .attachmentPath(String.format("%s/%s", uploadingDir, filename))
                        .fileName(filename)
                        .originalFileName(originalFilename)
                        .isDeleted(false)
                        .fileExtension(extension)
                        .fileSize(size)
                        .hash(fileHash)
                        .timeCreate(new Timestamp(System.currentTimeMillis()))
                        .build();

                if (docRkk != null){
                    rdrf.setDocRkk(docRkk);
                }
            }

        } catch (IOException ex){
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        }
        return rdrf;
    }

    @GetMapping("/doc_rkk_files")
    public @ResponseBody List<RegDocRkkFile> getRegDocFiles(@RequestParam(value = "docRkkId") Long docRkkId) {
        DocRkk docRkk = docRkkRepo.findById(docRkkId).orElse(null);
        return regDocRkkFileRepo.findRegDocRkkFileByDocRkk(docRkk);
    }

}
