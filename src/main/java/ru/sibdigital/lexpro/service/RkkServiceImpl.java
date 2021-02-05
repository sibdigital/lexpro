package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.dto.DocRkkDto;
import ru.sibdigital.lexpro.dto.RegRkkAttachmentDto;
import ru.sibdigital.lexpro.model.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RkkServiceImpl extends SuperServiceImpl implements RkkService{

    @Override
    public Page<DocRkk> findAllDocRkk(int page, int size) {
        return docRkkRepo.findAll(PageRequest.of(page, size, Sort.by("registrationDate")));
    }

    @Override
    public List<ClsRkkStatus> getRkkStatusList() {
        return StreamSupport.stream(clsRkkStatusRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClsNpaType> getNpaTypeList() {
        return StreamSupport.stream(clsNpaTypeRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClsOrganization> getOrganizationList() {
        return StreamSupport.stream(clsOrganizationRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClsEmployee> getEmployeeList() {
        return StreamSupport.stream(clsEmployeeRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClsSession> getSessionList() {
        return StreamSupport.stream(clsSessionRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public DocRkk saveDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = null;
        Long docRkkDtoId = docRkkDto.getId();
        if (docRkkDtoId != null) {
            docRkk = docRkkRepo.findById(docRkkDtoId).orElse(null);
            changeDocRkk(docRkk, docRkkDto);
        } else {
            docRkk = createDocRkk(docRkkDto);
        }

        docRkkRepo.save(docRkk);

//        if (docRkkDtoId == null) {
//            setDocRkkInAttachments(docRkkDto, docRkk);
//        }

        return docRkk;
    }

    private DocRkk createDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = DocRkk.builder()
                        .rkkNumber(docRkkDto.getRkkNumber())
                        .npaName(docRkkDto.getNpaName())
                        .npaType(getNpaTypeById(docRkkDto.getNpaType()))
                        .registrationDate(parseDateFromForm(docRkkDto.getRegistrationDate()))
                        .introductionDate(parseDateFromForm(docRkkDto.getIntroductionDate()))
                        .legislativeBasis(docRkkDto.getLegislativeBasis())
                        .lawSubject(getOrganizationById(docRkkDto.getLawSubject()))
                        .speaker(getEmployeeById(docRkkDto.getSpeaker()))
                        .readyForSession(docRkkDto.getReadyForSession())
                        .deadline(parseDateFromForm(docRkkDto.getDeadline()))
                        .includedInAgenda(parseDateFromForm(docRkkDto.getIncludedInAgenda()))
                        .responsibleOrganization(getOrganizationById(docRkkDto.getResponsibleOrganization()))
                        .responsibleEmployee(getEmployeeById(docRkkDto.getResponsibleEmployee()))
                        .status(getRkkStatusById(docRkkDto.getStatus()))
                        .session(getSessionById(docRkkDto.getSession()))
                        .agendaNumber(docRkkDto.getAgendaNumber())
                        .headSignature(parseDateFromForm(docRkkDto.getHeadSignature()))
                        .publicationDate(parseDateFromForm(docRkkDto.getPublicationDate()))
                        .build();
        return docRkk;
    }

    private DocRkk changeDocRkk(DocRkk docRkk, DocRkkDto docRkkDto) {
        docRkk.setRkkNumber(docRkkDto.getRkkNumber());
        docRkk.setNpaName(docRkkDto.getNpaName());
        docRkk.setNpaType(getNpaTypeById(docRkkDto.getNpaType()));
        docRkk.setRegistrationDate(parseDateFromForm(docRkkDto.getRegistrationDate()));
        docRkk.setIntroductionDate(parseDateFromForm(docRkkDto.getIntroductionDate()));
        docRkk.setLegislativeBasis(docRkkDto.getLegislativeBasis());
        docRkk.setLawSubject(getOrganizationById(docRkkDto.getLawSubject()));
        docRkk.setSpeaker(getEmployeeById(docRkkDto.getSpeaker()));
        docRkk.setReadyForSession(docRkkDto.getReadyForSession());
        docRkk.setDeadline(parseDateFromForm(docRkkDto.getDeadline()));
        docRkk.setIncludedInAgenda(parseDateFromForm(docRkkDto.getIncludedInAgenda()));
        docRkk.setResponsibleOrganization(getOrganizationById(docRkkDto.getResponsibleOrganization()));
        docRkk.setResponsibleEmployee(getEmployeeById(docRkkDto.getResponsibleEmployee()));
        docRkk.setStatus(getRkkStatusById(docRkkDto.getStatus()));
        docRkk.setSession(getSessionById(docRkkDto.getSession()));
        docRkk.setAgendaNumber(docRkkDto.getAgendaNumber());
        docRkk.setHeadSignature(parseDateFromForm(docRkkDto.getHeadSignature()));
        docRkk.setPublicationDate(parseDateFromForm(docRkkDto.getPublicationDate()));

        return docRkk;
    }

    private void setDocRkkInAttachments(DocRkkDto docRkkDto, DocRkk docRkk) {
        List<RegRkkAttachmentDto> attachmentDtos = docRkkDto.getAttachments();
        for (RegRkkAttachmentDto attachmentDto : attachmentDtos) {
            RegRkkAttachment attachment = getRkkAttachmentById(attachmentDto.getId());
            attachment.setDocRkk(docRkk);
            regRkkAttachmentRepo.save(attachment);
        }
    }
}
