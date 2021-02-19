package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sibdigital.lexpro.dto.DocRkkDto;
import ru.sibdigital.lexpro.dto.KeyValue;
import ru.sibdigital.lexpro.dto.RegRkkMailingDto;
import ru.sibdigital.lexpro.dto.RegRkkVisaDto;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkFile;
import ru.sibdigital.lexpro.model.RegRkkMailing;
import ru.sibdigital.lexpro.model.RegRkkVisa;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class RkkController extends SuperController {

    @GetMapping("/doc_rkks")
    public @ResponseBody Map<String, Object> getDocRkks(@RequestParam(value = "start", required = false) Integer start,
                                                        @RequestParam(value = "count", required = false) Integer count) {
        int page = start == null ? 0 : start / 25;
        int size = count == null ? 25 : count;

        Map<String, Object> result = new HashMap<>();
        Page<DocRkk> docRkks = rkkService.findActiveDocRkks(page, size);

        result.put("data", docRkks.getContent());
        result.put("pos", (long) page * size);
        result.put("total_count", docRkks.getTotalElements());
        return result;
    }

    @GetMapping("/deleted_doc_rkks")
    public @ResponseBody Map<String, Object> getDeletedDocRkks(@RequestParam(value = "start", required = false) Integer start,
                                                        @RequestParam(value = "count", required = false) Integer count) {
        int page = start == null ? 0 : start / 25;
        int size = count == null ? 25 : count;

        Map<String, Object> result = new HashMap<>();
        Page<DocRkk> docRkks = rkkService.findDeletedDocRkks(page, size);

        result.put("data", docRkks.getContent());
        result.put("pos", (long) page * size);
        result.put("total_count", docRkks.getTotalElements());
        return result;
    }

    @GetMapping("/archived_doc_rkks")
    public @ResponseBody Map<String, Object> getArchivedDocRkks(@RequestParam(value = "start", required = false) Integer start,
                                                               @RequestParam(value = "count", required = false) Integer count) {
        int page = start == null ? 0 : start / 25;
        int size = count == null ? 25 : count;

        Map<String, Object> result = new HashMap<>();
        Page<DocRkk> docRkks = rkkService.findArchivedDocRkks(page, size);

        result.put("data", docRkks.getContent());
        result.put("pos", (long) page * size);
        result.put("total_count", docRkks.getTotalElements());
        return result;
    }

    @GetMapping("/doc_rkk/{id_doc_rkk}")
    public @ResponseBody DocRkk getDocRkk(@PathVariable("id_doc_rkk") Long idDocRkk) {
        return docRkkRepo.findById(idDocRkk).orElse(null);
    }

    @GetMapping("/rkk_visa_dtos")
    public @ResponseBody
    List<RegRkkVisaDto> getRkkVisas(@RequestParam(value = "docRkkId") Long docRkkId) {
        List<RegRkkVisaDto> list = null;

        DocRkk docRkk = docRkkRepo.findById(docRkkId).orElse(null);
        List<RegRkkVisa> visas = regRkkVisaRepo.findAllByDocRkk(docRkk).orElse(null);
        if (visas != null) {
            list = visas.stream()
                    .map(ctr -> new RegRkkVisaDto(ctr))
                    .collect(Collectors.toList());
        }
        return list;
    }

    @GetMapping("/rkk_mailing_dtos")
    public @ResponseBody
    List<RegRkkMailingDto> getRkkMailing(@RequestParam(value = "docRkkId") Long docRkkId) {
        List<RegRkkMailingDto> list = null;

        DocRkk docRkk = docRkkRepo.findById(docRkkId).orElse(null);
        List<RegRkkMailing> mailings = regRkkMailingRepo.findAllByDocRkk(docRkk).orElse(null);
        if (mailings != null) {
            list = mailings.stream()
                    .map(ctr -> new RegRkkMailingDto(ctr))
                    .collect(Collectors.toList());
        }
        return list;
    }

    @PostMapping("/save_rkk")
    public @ResponseBody String saveRkk(@RequestBody DocRkkDto docRkkDto) {
        DocRkk docRkk = rkkService.saveDocRkk(docRkkDto);
        return "РКК сохранена";
    }

    @PostMapping("/archive_rkk")
    public @ResponseBody String archiveRkk(@RequestBody DocRkkDto docRkkDto) {
        DocRkk docRkk = rkkService.archiveDocRkk(docRkkDto);
        return "РКК сохранена";
    }

    @PostMapping("/delete_rkk")
    public @ResponseBody String deleteRkk(@RequestBody DocRkkDto docRkkDto) {
        DocRkk docRkk = rkkService.deleteDocRkk(docRkkDto);
        return "РКК сохранена";
    }

    @PostMapping("/restore_rkk")
    public @ResponseBody String restoreRkk(@RequestBody DocRkkDto docRkkDto) {
        DocRkk docRkk = rkkService.restoreDocRkk(docRkkDto);
        return "РКК сохранена";
    }

    @PostMapping("/rearchive_rkk")
    public @ResponseBody String rearchiveRkk(@RequestBody DocRkkDto docRkkDto) {
        DocRkk docRkk = rkkService.rearchiveDocRkk(docRkkDto);
        return "РКК сохранена";
    }
}
