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
import ru.sibdigital.lexpro.dto.KeyValue;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkFile;
import ru.sibdigital.lexpro.service.FileService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

            RegRkkFile regRkkFile = rkkFileService.construct(part, docRkk, uploadingDir);

            if (regRkkFile != null){
                if (regRkkFile.getId() == null) {
                    regRkkFile = regRkkFileRepo.save(regRkkFile);
                    responseEntity = ResponseEntity.ok()
                            .body("{\"cause\": \"Файл успешно загружен\"," +
                                    "\"status\": \"server\"," +
                                    "\"sname\": \"" + regRkkFile.getOriginalFileName() + "\"}");
                }else{
                    responseEntity = ResponseEntity.ok()
                            .body("{\"cause\": \"Вы уже загружали этот файл\"," +
                                    "\"status\": \"server\"," +
                                    "\"sname\": \"" + regRkkFile.getOriginalFileName() + "\"}");
                }
            }else{
                responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("{\"status\": \"server\"," +
                                "\"cause\":\"Ошибка сохранения\"}");
            }
        }

        return responseEntity;//ResponseEntity.ok().body(requestService.uploadFile(file));
    }

    @GetMapping("/doc_rkk_files")
    public @ResponseBody List<RegRkkFile> getRegDocFiles(@RequestParam(value = "docRkkId") Long docRkkId) {
        DocRkk docRkk = docRkkRepo.findById(docRkkId).orElse(null);
        return regRkkFileRepo.findRegRkkFileByDocRkk(docRkk);
    }

    @GetMapping("/participant_attachment_list")
    public @ResponseBody
    List<KeyValue> getParticipantListForRichselect() {
        List<KeyValue> list = rkkService.getOrganizationList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/group_attachement_list")
    public @ResponseBody
    List<KeyValue> getGroupAttachmentListForRichselect() {
        List<KeyValue> list = rkkFileService.getGroupAttachmentList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/type_attachement_list")
    public @ResponseBody
    List<KeyValue> getTypeAttachmentListForRichselect() {
        List<KeyValue> list = rkkFileService.getTypeAttachmentList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

}
