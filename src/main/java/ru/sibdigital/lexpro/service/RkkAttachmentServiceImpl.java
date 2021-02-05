package ru.sibdigital.lexpro.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.dto.RegRkkAttachmentDto;
import ru.sibdigital.lexpro.model.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Log4j2
@Service
public class RkkAttachmentServiceImpl extends SuperServiceImpl implements RkkAttachmentService{

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
    public RegRkkAttachment saveRkkAttachment(RegRkkAttachmentDto rkkAttachmentDto) {
        RegRkkAttachment regRkkAttachment = null;
        Long rkkAttachmentId = rkkAttachmentDto.getId();
        if (rkkAttachmentId != null) {
            regRkkAttachment = regRkkAttachmentRepo.findById(rkkAttachmentId).orElse(null);
            regRkkAttachment = changeRegRkkAttachment(regRkkAttachment, rkkAttachmentDto);
        } else {
            regRkkAttachment = createRegRkkAttachment(rkkAttachmentDto);
        }

        regRkkAttachmentRepo.save(regRkkAttachment);
        return regRkkAttachment;
    }

    private RegRkkAttachment changeRegRkkAttachment(RegRkkAttachment regRkkAttachment, RegRkkAttachmentDto rkkAttachmentDto) {
        // NumberAttachment
        regRkkAttachment.setNumberAttachment(rkkAttachmentDto.getNumberAttachment());
        regRkkAttachment.setSigningDate(parseDateFromForm(rkkAttachmentDto.getSigningDate()));

        // DocRkk
        DocRkk docRkk = getDocRkkById(rkkAttachmentDto.getDocRkkId());
        regRkkAttachment.setDocRkk(docRkk);

        // RkkFile
        Long newFileId = rkkAttachmentDto.getNewFileId();
        if (newFileId != null) {
            RegRkkFile newFile = getRegRkkFileById(newFileId);
            regRkkAttachment.setFile(newFile);
        }

        // GroupAttachment
        ClsGroupAttachment groupAttachment = getGroupAttachmentById(rkkAttachmentDto.getGroupId());
        regRkkAttachment.setGroup(groupAttachment);

        // TypeAttachment
        ClsTypeAttachment typeAttachment = getTypeAttachmentById(rkkAttachmentDto.getTypeId());
        regRkkAttachment.setType(typeAttachment);

        // Participant
        ClsOrganization participant = getOrganizationById(rkkAttachmentDto.getParticipantId());
        regRkkAttachment.setParticipant(participant);

        return regRkkAttachment;
    }

    private RegRkkAttachment createRegRkkAttachment(RegRkkAttachmentDto rkkAttachmentDto) {
        DocRkk docRkk = getDocRkkById(rkkAttachmentDto.getDocRkkId());

        RegRkkFile newFile = null;
        Long newFileId = rkkAttachmentDto.getNewFileId();
        if (newFileId != null) {
            newFile = getRegRkkFileById(newFileId);
        }

        ClsGroupAttachment groupAttachment = getGroupAttachmentById(rkkAttachmentDto.getGroupId());
        ClsTypeAttachment typeAttachment = getTypeAttachmentById(rkkAttachmentDto.getTypeId());
        ClsOrganization participant = getOrganizationById(rkkAttachmentDto.getParticipantId());

        RegRkkAttachment rkkAttachment = RegRkkAttachment.builder()
                                        .numberAttachment(rkkAttachmentDto.getNumberAttachment())
                                        .signingDate(parseDateFromForm(rkkAttachmentDto.getSigningDate()))
                                        .docRkk(docRkk)
                                        .file(newFile)
                                        .group(groupAttachment)
                                        .participant(participant)
                                        .type(typeAttachment)
                                        .build();

        return rkkAttachment;
    }
}
