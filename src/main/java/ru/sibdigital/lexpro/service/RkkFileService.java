package ru.sibdigital.lexpro.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.model.ClsGroupAttachment;
import ru.sibdigital.lexpro.model.ClsTypeAttachment;
import ru.sibdigital.lexpro.model.DocRkk;
import ru.sibdigital.lexpro.model.RegRkkFile;

import java.util.List;

public interface RkkFileService {
    List<ClsGroupAttachment> getGroupAttachmentList();

    List<ClsTypeAttachment> getTypeAttachmentList();

    RegRkkFile construct(MultipartFile part, DocRkk docRkk, String uploadingDir);
}
