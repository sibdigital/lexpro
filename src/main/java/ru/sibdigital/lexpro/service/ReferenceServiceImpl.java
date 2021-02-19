package ru.sibdigital.lexpro.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.dto.ClsGroupAttachmentDto;
import ru.sibdigital.lexpro.dto.ClsTypeAttachmentDto;
import ru.sibdigital.lexpro.model.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log4j2
@Service
public class ReferenceServiceImpl extends SuperServiceImpl implements ReferenceService{

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
    public List<ClsRkkStage> getStageList() {
        return StreamSupport.stream(clsRkkStageRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClsGroupAttachment> getGroupAttachmentList() {
        return StreamSupport.stream(clsGroupAttachmentRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClsTypeAttachment> getTypeAttachmentList() {
        return StreamSupport.stream(clsTypeAttachmentRepo.findAllByOrderByIdAsc().spliterator(), false)
                .collect(Collectors.toList());    }

    @Override
    public ClsTypeAttachment saveTypeAttachment(ClsTypeAttachmentDto typeAttachmentDto) {
        ClsTypeAttachment typeAttachment = null;
        Long typeAttachmentDtoId = typeAttachmentDto.getId();
        if (typeAttachmentDtoId != null) {
            typeAttachment = clsTypeAttachmentRepo.findById(typeAttachmentDtoId).orElse(null);
            typeAttachment.setName(typeAttachmentDto.getName());
        } else {
            typeAttachment = ClsTypeAttachment.builder()
                            .name(typeAttachmentDto.getName())
                            .build();
        }

        clsTypeAttachmentRepo.save(typeAttachment);

        return typeAttachment;
    }

    @Override
    public void deleteTypeAttachment(ClsTypeAttachmentDto typeAttachmentDto) {
        ClsTypeAttachment typeAttachment = null;
        Long typeAttachmentDtoId = typeAttachmentDto.getId();
        if (typeAttachmentDtoId != null) {
            typeAttachment = clsTypeAttachmentRepo.findById(typeAttachmentDtoId).orElse(null);
            clsTypeAttachmentRepo.delete(typeAttachment);
        }
    }

    @Override
    public ClsGroupAttachment saveGroupAttachment(ClsGroupAttachmentDto groupAttachmentDto) {
        ClsGroupAttachment groupAttachment = null;
        Long groupAttachmentDtoId = groupAttachmentDto.getId();
        if (groupAttachmentDtoId != null) {
            groupAttachment = clsGroupAttachmentRepo.findById(groupAttachmentDtoId).orElse(null);
            groupAttachment.setName(groupAttachmentDto.getName());
        } else {
            groupAttachment = ClsGroupAttachment.builder()
                    .name(groupAttachmentDto.getName())
                    .build();
        }

        clsGroupAttachmentRepo.save(groupAttachment);

        return groupAttachment;
    }

    @Override
    public void deleteGroupAttachment(ClsGroupAttachmentDto groupAttachmentDto) {
        ClsGroupAttachment groupAttachment = null;
        Long groupAttachmentDtoId = groupAttachmentDto.getId();
        if (groupAttachmentDtoId != null) {
            groupAttachment = clsGroupAttachmentRepo.findById(groupAttachmentDtoId).orElse(null);
            clsGroupAttachmentRepo.delete(groupAttachment);
        }
    }

}
