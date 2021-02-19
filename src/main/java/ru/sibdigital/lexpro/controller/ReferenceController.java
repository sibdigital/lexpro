package ru.sibdigital.lexpro.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sibdigital.lexpro.dto.ClsGroupAttachmentDto;
import ru.sibdigital.lexpro.dto.ClsTypeAttachmentDto;
import ru.sibdigital.lexpro.dto.DocRkkDto;
import ru.sibdigital.lexpro.dto.KeyValue;
import ru.sibdigital.lexpro.model.ClsGroupAttachment;
import ru.sibdigital.lexpro.model.ClsTypeAttachment;
import ru.sibdigital.lexpro.model.DocRkk;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Controller
public class ReferenceController extends SuperController{

    @GetMapping("/status_list_richselect")
    public @ResponseBody
    List<KeyValue> getStatusListForRichselect() {
        List<KeyValue> list = referenceService.getRkkStatusList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/stage_list_richselect")
    public @ResponseBody
    List<KeyValue> getStageListForRichselect() {
        List<KeyValue> list = referenceService.getStageList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/npa_type_list_richselect")
    public @ResponseBody
    List<KeyValue> getNpaTypeListForRichselect() {
        List<KeyValue> list = referenceService.getNpaTypeList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/responsible_organization_list_richselect")
    public @ResponseBody
    List<KeyValue> getResponsibleOrganizationListForRichselect() {
        List<KeyValue> list = referenceService.getOrganizationList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/responsible_employee_list_richselect")
    public @ResponseBody
    List<KeyValue> getResponsibleEmployeeListForRichselect() {
        List<KeyValue> list = referenceService.getEmployeeList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/law_subject_list_richselect")
    public @ResponseBody
    List<KeyValue> getLawSubjectListForRichselect() {
        List<KeyValue> list = referenceService.getOrganizationList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/session_list_richselect")
    public @ResponseBody
    List<KeyValue> getSessionListForRichselect() {
        List<KeyValue> list = referenceService.getSessionList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getNumber()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/participant_attachment_list_richselect")
    public @ResponseBody
    List<KeyValue> getParticipantListForRichselect() {
        List<KeyValue> list = referenceService.getOrganizationList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/group_attachement_list_richselect")
    public @ResponseBody
    List<KeyValue> getGroupAttachmentListForRichselect() {
        List<KeyValue> list = referenceService.getGroupAttachmentList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/type_attachment_list_richselect")
    public @ResponseBody
    List<KeyValue> getTypeAttachmentListForRichselect() {
        List<KeyValue> list = referenceService.getTypeAttachmentList().stream()
                .map(ctr -> new KeyValue(ctr.getClass().getSimpleName(), ctr.getId(), ctr.getName()))
                .collect(Collectors.toList());
        return list;
    }

    @GetMapping("/attachment_type_list")
    public @ResponseBody
    List<ClsTypeAttachment> getAttachmentTypeList() {
        return referenceService.getTypeAttachmentList();
    }

    @GetMapping("/attachment_group_list")
    public @ResponseBody
    List<ClsGroupAttachment> getAttachmentGroupList() {
        return referenceService.getGroupAttachmentList();
    }

    @PostMapping("/save_attach_type")
    public @ResponseBody String saveAttachType(@RequestBody ClsTypeAttachmentDto ctad) {
        ClsTypeAttachment typeAttachment = referenceService.saveTypeAttachment(ctad);
        return "Тип сохранен";
    }

    @PostMapping("/delete_attach_type")
    public @ResponseBody String deleteAttachType(@RequestBody ClsTypeAttachmentDto ctad) {
        referenceService.deleteTypeAttachment(ctad);
        return "Тип удален";
    }

    @PostMapping("/save_attach_group")
    public @ResponseBody String saveAttachGroup(@RequestBody ClsGroupAttachmentDto cgad) {
        ClsGroupAttachment groupAttachment = referenceService.saveGroupAttachment(cgad);
        return "Группа сохранена";
    }

    @PostMapping("/delete_attach_group")
    public @ResponseBody String deleteAttachGroup(@RequestBody ClsGroupAttachmentDto cgad) {
        referenceService.deleteGroupAttachment(cgad);
        return "Группа удалена";
    }
}
