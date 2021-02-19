package ru.sibdigital.lexpro.service;

import ru.sibdigital.lexpro.dto.ClsGroupAttachmentDto;
import ru.sibdigital.lexpro.dto.ClsTypeAttachmentDto;
import ru.sibdigital.lexpro.model.*;

import java.util.List;

public interface ReferenceService {

    List<ClsRkkStatus> getRkkStatusList();

    List<ClsNpaType> getNpaTypeList();

    List<ClsOrganization> getOrganizationList();

    List<ClsEmployee> getEmployeeList();

    List<ClsSession> getSessionList();

    List<ClsRkkStage> getStageList();

    List<ClsGroupAttachment> getGroupAttachmentList();

    List<ClsTypeAttachment> getTypeAttachmentList();

    ClsTypeAttachment saveTypeAttachment(ClsTypeAttachmentDto clsTypeAttachmentDto);

    void deleteTypeAttachment(ClsTypeAttachmentDto clsTypeAttachmentDto);

    ClsGroupAttachment saveGroupAttachment(ClsGroupAttachmentDto clsGroupAttachmentDto);

    void deleteGroupAttachment(ClsGroupAttachmentDto clsGroupAttachmentDto);

}
