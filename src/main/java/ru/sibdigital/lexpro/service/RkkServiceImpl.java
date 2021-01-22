package ru.sibdigital.lexpro.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.dto.DocRkkDto;
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
    public DocRkk saveDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = null;
        Long docRkkId = docRkkDto.getId();
        if (docRkkId != null) {
            docRkk = docRkkRepo.findById(docRkkId).orElse(null);
            changeDocRkk(docRkk, docRkkDto);
        } else {
            docRkk = createDocRkk(docRkkDto);
        }

        docRkkRepo.save(docRkk);
        return docRkk;
    }

    private DocRkk createDocRkk(DocRkkDto docRkkDto) {
        DocRkk docRkk = DocRkk.builder()
                        .rkkNumber(docRkkDto.getRkkNumber())
                        .npaName(docRkkDto.getNpaName())
                        .registrationDate(parseDateFromForm(docRkkDto.getRegistrationDate()))
                        .introductionDate(parseDateFromForm(docRkkDto.getIntroductionDate()))
                        .deadline(parseDateFromForm(docRkkDto.getDeadline()))
                        .sessionNumber(docRkkDto.getSessionNumber())
                        .sessionDate(parseDateFromForm(docRkkDto.getSessionDate()))
                        .includedInAgenta(docRkkDto.getIncludedInAgenta())
                        .npaType(getNpaTypeById(docRkkDto.getNpaType()))
                        .responsibleOrganization(getResponsibleOrganizationById(docRkkDto.getResponsibleOrganization()))
                        .responsibleEmployee(getResponsibleEmployeeById(docRkkDto.getResponsibleEmployee()))
                        .status(getRkkStatusById(docRkkDto.getStatus()))
                        .build();
        return docRkk;
    }

    private DocRkk changeDocRkk(DocRkk docRkk, DocRkkDto docRkkDto) {
        docRkk.setRkkNumber(docRkkDto.getRkkNumber());
        docRkk.setNpaName(docRkkDto.getNpaName());
        docRkk.setRegistrationDate(parseDateFromForm(docRkkDto.getRegistrationDate()));
        docRkk.setIntroductionDate(parseDateFromForm(docRkkDto.getIntroductionDate()));
        docRkk.setDeadline(parseDateFromForm(docRkkDto.getDeadline()));
        docRkk.setSessionNumber(docRkkDto.getSessionNumber());
        docRkk.setSessionDate(parseDateFromForm(docRkkDto.getSessionDate()));
        docRkk.setIncludedInAgenta(docRkkDto.getIncludedInAgenta());
        docRkk.setNpaType(getNpaTypeById(docRkkDto.getNpaType()));
        docRkk.setResponsibleOrganization(getResponsibleOrganizationById(docRkkDto.getResponsibleOrganization()));
        docRkk.setResponsibleEmployee(getResponsibleEmployeeById(docRkkDto.getResponsibleEmployee()));
        docRkk.setStatus(getRkkStatusById(docRkkDto.getStatus()));

        return docRkk;
    }

    private ClsNpaType getNpaTypeById(String npaTypeId) {
        ClsNpaType clsNpaType = null;
        Long id = Long.parseLong(npaTypeId);
        if (id != null) {
            clsNpaType = clsNpaTypeRepo.findById(id).orElse(null);
        }
        return clsNpaType;
    }

    private ClsOrganization getResponsibleOrganizationById(String clsOrganizationId) {
        ClsOrganization clsOrganization = null;
        Long id = Long.parseLong(clsOrganizationId);
        if (id != null) {
            clsOrganization = clsOrganizationRepo.findById(id).orElse(null);
        }
        return clsOrganization;
    }

    private ClsEmployee getResponsibleEmployeeById(String employeeId) {
        ClsEmployee clsEmployee = null;
        Long id = Long.parseLong(employeeId);
        if (id != null) {
            clsEmployee = clsEmployeeRepo.findById(id).orElse(null);
        }
        return clsEmployee;
    }

    private ClsRkkStatus getRkkStatusById(String statusId) {
        ClsRkkStatus status = null;
        Long id = Long.parseLong(statusId);
        if (id != null) {
            status = clsRkkStatusRepo.findById(id).orElse(null);
        }
        return status;
    }

}
