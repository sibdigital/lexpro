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
        Page<DocRkk> docRkks = rkkService.findAllDocRkk(page, size);

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

    @GetMapping("/status_list")
    public @ResponseBody
    List<KeyValue> getStatusListForRichselect() {
        List<KeyValue> list = rkkService.getRkkStatusList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/stage_list")
    public @ResponseBody
    List<KeyValue> getStageListForRichselect() {
        List<KeyValue> list = rkkService.getStageList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/npa_type_list")
    public @ResponseBody
    List<KeyValue> getNpaTypeListForRichselect() {
        List<KeyValue> list = rkkService.getNpaTypeList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/responsible_organization_list")
    public @ResponseBody
    List<KeyValue> getResponsibleOrganizationListForRichselect() {
        List<KeyValue> list = rkkService.getOrganizationList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/responsible_employee_list")
    public @ResponseBody
    List<KeyValue> getResponsibleEmployeeListForRichselect() {
        List<KeyValue> list = rkkService.getEmployeeList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/law_subject_list")
    public @ResponseBody
    List<KeyValue> getLawSubjectListForRichselect() {
        List<KeyValue> list = rkkService.getOrganizationList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/session_list")
    public @ResponseBody
    List<KeyValue> getSessionListForRichselect() {
        List<KeyValue> list = rkkService.getSessionList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getNumber()))
                .collect(Collectors.toList());
        return list;
    }

    @PostMapping("/save_rkk")
    public @ResponseBody String saveRkk(@RequestBody DocRkkDto docRkkDto) {
        DocRkk docRkk = rkkService.saveDocRkk(docRkkDto);
        return "РКК сохранена";
    }

}
