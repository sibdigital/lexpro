package ru.sibdigital.lexpro.service;

import ru.sibdigital.lexpro.dto.RegRkkAttachmentDto;
import ru.sibdigital.lexpro.model.ClsGroupAttachment;
import ru.sibdigital.lexpro.model.ClsTypeAttachment;
import ru.sibdigital.lexpro.model.RegRkkAttachment;


import java.util.List;

public interface RkkAttachmentService {
    List<ClsGroupAttachment> getGroupAttachmentList();
    List<ClsTypeAttachment> getTypeAttachmentList();
    RegRkkAttachment saveRkkAttachment(RegRkkAttachmentDto rkkAttachmentDto);
}
