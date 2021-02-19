package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.dto.DocRkkDto;
import ru.sibdigital.lexpro.dto.RegRkkMailingDto;
import ru.sibdigital.lexpro.dto.RegRkkVisaDto;
import ru.sibdigital.lexpro.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RkkServiceImpl extends SuperServiceImpl implements RkkService{

    @Override
    public Page<DocRkk> findActiveDocRkks(int page, int size) {
        return docRkkRepo.findAllByIsDeletedAndIsArchived(false, false, PageRequest.of(page, size, Sort.by("registrationDate")));
    }

    @Override
    public Page<DocRkk> findDeletedDocRkks(int page, int size) {
        return docRkkRepo.findAllByIsDeleted(true, PageRequest.of(page, size, Sort.by("registrationDate")));
    }

    @Override
    public Page<DocRkk> findArchivedDocRkks(int page, int size) {
        return docRkkRepo.findAllByIsArchived(true, PageRequest.of(page, size, Sort.by("registrationDate")));
    }

    @Override
    public DocRkk saveDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = null;
        Long docRkkDtoId = docRkkDto.getId();
        if (docRkkDtoId != null) {
            docRkk = docRkkRepo.findById(docRkkDtoId).orElse(null);
            docRkk = changeDocRkk(docRkk, docRkkDto);
        } else {
            docRkk = createDocRkk(docRkkDto);
        }

        List<RegRkkMailing> regRkkMailingList = parseRkkMailings(docRkkDto, docRkk);
        List<RegRkkVisa>    regRkkVisaList    = parseRkkVisas(docRkkDto, docRkk);

        docRkkRepo.save(docRkk);

        deleteRemovedMailings(docRkkDto, docRkk);
        regRkkMailingRepo.saveAll(regRkkMailingList);

        deleteRemovedVisas(docRkkDto, docRkk);
        regRkkVisaRepo.saveAll(regRkkVisaList);

        return docRkk;
    }

    private void deleteRemovedMailings(DocRkkDto docRkkDto, DocRkk docRkk) {
        Set<Long> set = getPrevMailingIdSet(docRkk);
        Set<Long> currentMailingIdSet = getCurrentMailingIdSet(docRkkDto);
        if (set != null && set.removeAll(currentMailingIdSet)) {
            regRkkMailingRepo.deleteBySetId(set);
        }
    }

    private void deleteRemovedVisas(DocRkkDto docRkkDto, DocRkk docRkk) {
        Set<Long> set = getPrevVisaIdSet(docRkk);
        Set<Long> currentVisaIdSet = getCurrentVisaIdSet(docRkkDto);
        if (set != null && set.removeAll(currentVisaIdSet)) {
            regRkkVisaRepo.deleteBySetId(set);
        }
    }

    private Set<Long> getPrevMailingIdSet(DocRkk docRkk) {
        Set<Long> idList = null;
        List<RegRkkMailing> prevMailingList = regRkkMailingRepo.findAllByDocRkk(docRkk).orElse(null);
        if (prevMailingList != null) {
            idList = prevMailingList.stream()
                    .map(ctr -> ctr.getId())
                    .collect(Collectors.toSet());
        }
        return idList;
    }

    private Set<Long> getPrevVisaIdSet(DocRkk docRkk) {
        Set<Long> idList = null;
        List<RegRkkVisa> prevVisaList = regRkkVisaRepo.findAllByDocRkk(docRkk).orElse(null);
        if (prevVisaList != null) {
            idList = prevVisaList.stream()
                    .map(ctr -> ctr.getId())
                    .collect(Collectors.toSet());
        }
        return idList;
    }

    private Set<Long> getCurrentMailingIdSet(DocRkkDto docRkkDto) {
        Set<Long> idList = null;
        List<RegRkkMailingDto> currentMailingList = docRkkDto.getMailings();
        if (currentMailingList != null) {
            idList = currentMailingList.stream()
                    .filter(ctr -> (ctr.getRegRkkMailingId() != null))
                    .map(ctr -> ctr.getRegRkkMailingId())
                    .collect(Collectors.toSet());
        }
        return idList;
    }

    private Set<Long> getCurrentVisaIdSet(DocRkkDto docRkkDto) {
        Set<Long> idList = null;
        List<RegRkkVisaDto> currentVisaList = docRkkDto.getVisas();
        if (currentVisaList != null) {
            idList = currentVisaList.stream()
                    .filter(ctr -> (ctr.getRegRkkVisaId() != null))
                    .map(ctr -> ctr.getRegRkkVisaId())
                    .collect(Collectors.toSet());
        }
        return idList;
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
                        .isArchived(false)
                        .isDeleted(false)
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
        docRkk.setIsArchived(false);
        docRkk.setIsDeleted(false);

        return docRkk;
    }

    @Override
    public DocRkk archiveDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = null;
        Long docRkkDtoId = docRkkDto.getId();
        if (docRkkDtoId != null) {
            docRkk = getDocRkkById(docRkkDtoId);
            if (docRkk != null) {
                docRkk.setIsArchived(true);
            }
        }

        docRkkRepo.save(docRkk);

        return docRkk;
    }

    @Override
    public DocRkk deleteDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = null;
        Long docRkkDtoId = docRkkDto.getId();
        if (docRkkDtoId != null) {
            docRkk = getDocRkkById(docRkkDtoId);
            if (docRkk != null) {
                docRkk.setIsDeleted(true);
            }
        }
        docRkkRepo.save(docRkk);

        return docRkk;
    }

    @Override
    public DocRkk restoreDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = null;
        Long docRkkDtoId = docRkkDto.getId();
        if (docRkkDtoId != null) {
            docRkk = getDocRkkById(docRkkDtoId);
            if (docRkk != null) {
                docRkk.setIsDeleted(false);
            }
        }
        docRkkRepo.save(docRkk);

        return docRkk;
    }

    @Override
    public DocRkk rearchiveDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = null;
        Long docRkkDtoId = docRkkDto.getId();
        if (docRkkDtoId != null) {
            docRkk = getDocRkkById(docRkkDtoId);
            if (docRkk != null) {
                docRkk.setIsArchived(false);
            }
        }
        docRkkRepo.save(docRkk);

        return docRkk;
    }

    private List<RegRkkMailing> parseRkkMailings(DocRkkDto docRkkDto, DocRkk docRkk) {
        List<RegRkkMailing> list = new ArrayList<>();

        List<RegRkkMailingDto> rkkMailingDtos = docRkkDto.getMailings();
        for (RegRkkMailingDto dto : rkkMailingDtos) {
            RegRkkMailing rkkMailing = null;

            if (dto.getRegRkkMailingId() != null) {
                rkkMailing = changeRegRkkMailing(dto);
            } else {
                rkkMailing = createRegRkkMailing(dto, docRkk);
            }
            list.add(rkkMailing);
        }

        return list;
    }

    private RegRkkMailing changeRegRkkMailing(RegRkkMailingDto dto) {
        RegRkkMailing rkkMailing = regRkkMailingRepo.findById(dto.getRegRkkMailingId()).orElse(null);
        if (rkkMailing != null) {
            rkkMailing.setNote(dto.getNote());
            rkkMailing.setDate(parseDateFromForm(dto.getDate()));

            Long organizationId = dto.getOrganizationId();
            ClsOrganization organization = clsOrganizationRepo.findById(organizationId).orElse(null);
            rkkMailing.setOrganization(organization);
        }

        return rkkMailing;
    }

    private RegRkkMailing createRegRkkMailing(RegRkkMailingDto dto, DocRkk docRkk) {
        Long organizationId = dto.getOrganizationId();
        ClsOrganization organization = clsOrganizationRepo.findById(organizationId).orElse(null);

        RegRkkMailing rkkMailing = RegRkkMailing.builder()
                                    .docRkk(docRkk)
                                    .date(parseDateFromForm(dto.getDate()))
                                    .note(dto.getNote())
                                    .organization(organization)
                                    .build();
        return rkkMailing;
    }

    private List<RegRkkVisa> parseRkkVisas(DocRkkDto docRkkDto, DocRkk docRkk) {
        List<RegRkkVisa> list = new ArrayList<>();

        List<RegRkkVisaDto> rkkVisaDtos = docRkkDto.getVisas();
        for (RegRkkVisaDto dto : rkkVisaDtos) {
            RegRkkVisa rkkVisa = null;

            if (dto.getRegRkkVisaId() != null) {
                rkkVisa = changeRegRkkVisa(dto);
            } else {
                rkkVisa = createRegRkkVisa(dto, docRkk);
            }
            list.add(rkkVisa);
        }

        return list;
    }

    private RegRkkVisa changeRegRkkVisa(RegRkkVisaDto dto) {
        RegRkkVisa rkkVisa = regRkkVisaRepo.findById(dto.getRegRkkVisaId()).orElse(null);
        if (rkkVisa != null) {
            rkkVisa.setNote(dto.getNote());
            rkkVisa.setDate(parseDateFromForm(dto.getDate()));

            Long stageId = dto.getStageId();
            ClsRkkStage stage = clsRkkStageRepo.findById(stageId).orElse(null);
            rkkVisa.setStage(stage);
        }

        return rkkVisa;
    }

    private RegRkkVisa createRegRkkVisa(RegRkkVisaDto dto, DocRkk docRkk) {
        Long stageId = dto.getStageId();
        ClsRkkStage stage = clsRkkStageRepo.findById(stageId).orElse(null);

        RegRkkVisa rkkVisa = RegRkkVisa.builder()
                            .docRkk(docRkk)
                            .date(parseDateFromForm(dto.getDate()))
                            .note(dto.getNote())
                            .stage(stage)
                            .build();
        return rkkVisa;
    }
}
