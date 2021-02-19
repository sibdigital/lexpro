package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.dto.KeyValue;
import ru.sibdigital.lexpro.dto.RegRkkFileDto;
import ru.sibdigital.lexpro.model.*;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Controller
public class RkkFileController extends SuperController {

    @Value("${upload.path:/rkk}")
    String uploadingDir;

    @GetMapping("/doc_rkk_files")
    public @ResponseBody
    List<RegRkkFile> getRegDocFiles(@RequestParam(value = "docRkkId") Long docRkkId) {
        Boolean isDeleted = false;

        DocRkk docRkk = docRkkRepo.findById(docRkkId).orElse(null);
        return regRkkFileRepo.findAllByDocRkkAndIsDeleted(docRkk, isDeleted).orElse(null);
    }


    @PostMapping("/save_rkk_file")
    public @ResponseBody ResponseEntity<Object> saveRkkFile(@RequestParam(value = "upload") MultipartFile part, HttpSession session,
                                                            @ModelAttribute RegRkkFileDto rkkFileDto) {

        RegRkkFile rkkFile = rkkFileService.getRegRkkFile(rkkFileDto);
        rkkFileService.savePreviousFileCopy(rkkFile);

        ResponseEntity<Object> responseEntity;
        if (Files.notExists(Paths.get(uploadingDir))) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\": \"server\"," +
                            "\"cause\": \"Ошибка сохранения\"}");
        }
        else {
            RegRkkFile regRkkFile = rkkFileService.constructRkkFile(part, rkkFileDto);
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

        return responseEntity;
    }

    @PostMapping("/edit_rkk_file")
    public @ResponseBody String editRkkFile(@RequestBody RegRkkFileDto rkkFileDto) {
        String successfullSave = "Не удалось сохранить";
        RegRkkFile rkkFile = rkkFileService.getRegRkkFile(rkkFileDto);
        rkkFileService.savePreviousFileCopy(rkkFile);
        RegRkkFile regRkkFile = rkkFileService.editRkkFile(rkkFileDto);
        if (regRkkFile != null){
            regRkkFileRepo.save(regRkkFile);
            successfullSave = "Вложение сохранено";
        }

        return successfullSave;
    }

    @PostMapping("/delete_rkk_attachment")
    public @ResponseBody RegRkkFileDto deleteEmployee(@RequestBody RegRkkFileDto rkkFileDto) {
        try{
            rkkFileService.deleteRkkFile(rkkFileDto);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
        return rkkFileDto;
    }

}
