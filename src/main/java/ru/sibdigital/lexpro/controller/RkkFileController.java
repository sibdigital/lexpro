package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.model.ClsEmployee;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Paths;


@Log4j2
@Controller
public class RkkFileController extends SuperController {

    @Value("${upload.path:/rkk}")
    String uploadingDir;

    @PostMapping(value = "/upload_rkk_files", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> uploadFile(@RequestParam(value = "upload") MultipartFile part, HttpSession session,
                                             @RequestParam(required = false) Long docRkkId){

        Long userId = (Long) session.getAttribute("id_user");
        ResponseEntity<Object> responseEntity;
        if (Files.notExists(Paths.get(uploadingDir))) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\": \"server\"," +
                            "\"cause\": \"Ошибка сохранения\"}");
        }
        else {
            DocRkk docRkk = (docRkkId == null) ?
                    null : docRkkRepo.findById(docRkkId).orElse(null);
            ClsEmployee operator = getEmployeeByUser_Id(userId);

            RegRkkFile regRkkFile = rkkFileService.constructRkkFile(part, docRkk, operator);

            if (regRkkFile != null){
                regRkkFile = regRkkFileRepo.save(regRkkFile);
                responseEntity = ResponseEntity.ok()
                        .body("{\"cause\": \"Файл успешно загружен\"," +
                                "\"status\": \"server\"," +
                                "\"pageCount\": \"" + regRkkFile.getPageCount() + "\"," +
                                "\"fileId\": \"" + regRkkFile.getId() + "\"," +
                                "\"sname\": \"" + regRkkFile.getOriginalFileName() + "\"}");
            } else{
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"status\": \"server\"," +
                                "\"cause\":\"Ошибка сохранения\"}");
            }
        }

        return responseEntity;//ResponseEntity.ok().body(requestService.uploadFile(file));
    }

}
