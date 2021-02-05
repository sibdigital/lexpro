package ru.sibdigital.lexpro.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibdigital.lexpro.model.*;
import ru.sibdigital.lexpro.repository.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class SuperServiceImpl implements SuperService{

    @Autowired
    DocRkkRepo docRkkRepo;

    @Autowired
    ClsRkkStatusRepo clsRkkStatusRepo;

    @Autowired
    ClsNpaTypeRepo clsNpaTypeRepo;

    @Autowired
    ClsOrganizationRepo clsOrganizationRepo;

    @Autowired
    ClsEmployeeRepo clsEmployeeRepo;

    @Autowired
    RegUserRoleRepo regUserRoleRepo;

    @Autowired
    RegRolePrivilegeRepo regRolePrivilegeRepo;

    @Autowired
    ClsUserRepo clsUserRepo;

    @Autowired
    RolePrivilegeService rolePrivilegeService;

    @Autowired
    ClsSessionRepo clsSessionRepo;

    @Autowired
    RegRkkFileRepo regRkkFileRepo;

    @Autowired
    ClsGroupAttachmentRepo clsGroupAttachmentRepo;

    @Autowired
    ClsTypeAttachmentRepo clsTypeAttachmentRepo;

    @Autowired
    FileService fileService;

    @Autowired
    RegRkkAttachmentRepo regRkkAttachmentRepo;

    @Override
    public Date parseDateFromForm(String stringDate) {
        Date date = null;
        try {
            if (stringDate != null && !stringDate.equals("")) {
                date = new Date(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(stringDate).getTime());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return date;
    }

    protected DocRkk getDocRkkById(Long id) {
        DocRkk docRkk = null;
        if (id != null) {
            docRkk = docRkkRepo.findById(id).orElse(null);
        }

        return docRkk;
    }

    protected RegRkkFile getRegRkkFileById(Long id) {
        RegRkkFile rkkFile = null;
        if (id != null) {
            rkkFile = regRkkFileRepo.findById(id).orElse(null);
        }

        return rkkFile;
    }

    protected ClsGroupAttachment getGroupAttachmentById(Long id) {
        ClsGroupAttachment groupAttachment = null;
        if (id != null) {
            groupAttachment = clsGroupAttachmentRepo.findById(id).orElse(null);
        }

        return groupAttachment;
    }

    protected ClsTypeAttachment getTypeAttachmentById(Long id) {
        ClsTypeAttachment typeAttachment = null;
        if (id != null) {
            typeAttachment = clsTypeAttachmentRepo.findById(id).orElse(null);
        }

        return typeAttachment;
    }

    protected ClsNpaType getNpaTypeById(String npaTypeId) {
        ClsNpaType clsNpaType = null;
        if (npaTypeId != null) {
            Long id = Long.parseLong(npaTypeId);
            clsNpaType = clsNpaTypeRepo.findById(id).orElse(null);
        }
        return clsNpaType;
    }

    protected ClsOrganization getOrganizationById(Long id) {
        ClsOrganization clsOrganization = null;
        if (id != null) {
            clsOrganization = clsOrganizationRepo.findById(id).orElse(null);
        }
        return clsOrganization;
    }

    protected ClsEmployee getEmployeeById(Long id) {
        ClsEmployee clsEmployee = null;
        if (id != null) {
            clsEmployee = clsEmployeeRepo.findById(id).orElse(null);
        }
        return clsEmployee;
    }

    protected ClsSession getSessionById(Long id) {
        ClsSession clsSession = null;
        if (id != null) {
            clsSession = clsSessionRepo.findById(id).orElse(null);
        }
        return clsSession;
    }

    protected ClsRkkStatus getRkkStatusById(Long id) {
        ClsRkkStatus status = null;
        if (id != null) {
            status = clsRkkStatusRepo.findById(id).orElse(null);
        }
        return status;
    }

    protected RegRkkAttachment getRkkAttachmentById(Long id) {
        RegRkkAttachment rkkAttachment = null;
        if (id != null) {
            rkkAttachment = regRkkAttachmentRepo.findById(id).orElse(null);
        }
        return rkkAttachment;
    }
}
