package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.dto.DocRkkDto;
import ru.sibdigital.lexpro.dto.KeyValue;
import ru.sibdigital.lexpro.dto.RegRkkAttachmentDto;
import ru.sibdigital.lexpro.model.ClsEmployee;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkAttachment;
import ru.sibdigital.lexpro.model.RegRkkFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class RkkAttachmentController extends SuperController {

    @GetMapping("/doc_rkk_attachments")
    public @ResponseBody List<RegRkkAttachment> getRegDocFiles(@RequestParam(value = "docRkkId") Long docRkkId) {
        Boolean isDeleted = false;

        DocRkk docRkk = docRkkRepo.findById(docRkkId).orElse(null);
        return regRkkAttachmentRepo.findAllByDocRkkAndIsDeleted(docRkk, isDeleted).orElse(null);
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
        List<KeyValue> list = rkkAttachmentService.getGroupAttachmentList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/type_attachement_list")
    public @ResponseBody
    List<KeyValue> getTypeAttachmentListForRichselect() {
        List<KeyValue> list = rkkAttachmentService.getTypeAttachmentList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @PostMapping("/save_rkk_attachment")
    public @ResponseBody String saveRkkAttachment(@RequestBody RegRkkAttachmentDto rkkAttachmentDto) {
        RegRkkAttachment rkkAttachment = rkkAttachmentService.saveRkkAttachment(rkkAttachmentDto);
        return "Вложение сохранено";
    }

    @PostMapping("/delete_rkk_attachment")
    public @ResponseBody RegRkkAttachmentDto deleteEmployee(@RequestBody RegRkkAttachmentDto rkkAttachmentDto) {
        try{
            rkkAttachmentService.deleteRkkAttachment(rkkAttachmentDto);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
        return rkkAttachmentDto;
    }
}
