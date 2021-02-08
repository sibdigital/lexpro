package ru.sibdigital.lexpro.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.dto.FileContainer;
import ru.sibdigital.lexpro.dto.RegRkkFileDto;
import ru.sibdigital.lexpro.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Log4j2
@Service
public class RkkFileServiceImpl extends SuperServiceImpl implements RkkFileService{

    @Value("${upload.path:/rkk}")
    String uploadingDir;

    @Override
    public RegRkkFile constructRkkFile(MultipartFile part, RegRkkFileDto regRkkFileDto){
        RegRkkFile rrf = null;
        try {
//            Long docRkkId = Long.parseLong(regRkkFileDto.getDocRkkId());
            Long docRkkId = regRkkFileDto.getDocRkkId();
            String docRkkIdString = docRkkId.toString();
            FileContainer fileContainer = createFileContainer(part, docRkkIdString);
            if (regRkkFileDto.getId() == null) {
                rrf = createRkkFile(fileContainer, regRkkFileDto);
            }
            else {
                rrf = changeRkkFile(fileContainer, regRkkFileDto);
            }
        } catch (IOException ex){
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        }
        return rrf;
    }

    private RegRkkFile changeRkkFile(FileContainer fileContainer, RegRkkFileDto regRkkFileDto) throws IOException {
        RegRkkFile rrf = getRegRkkFileById(regRkkFileDto.getId());
        if (rrf != null) {
            DocRkk docRkk = getDocRkk(regRkkFileDto);
            ClsGroupAttachment group = getGroupAttachment(regRkkFileDto);
            ClsTypeAttachment type = getTypeAttachment(regRkkFileDto);
            ClsOrganization participant = getOrganization(regRkkFileDto);
            ClsEmployee operator = getEmployee(regRkkFileDto);
            String sSigningDate = regRkkFileDto.getSigningDate();
            Date signingDate = parseDateFromForm(sSigningDate);
//            Date signingDate = regRkkFileDto.getSigningDate();

            rrf.setDocRkk(docRkk);
            rrf.setGroup(group);
            rrf.setType(type);
            rrf.setParticipant(participant);
            rrf.setNumberAttachment(regRkkFileDto.getNumberAttachment());
            rrf.setSigningDate(signingDate);
            rrf.setPageCount(fileContainer.getPageCount());
            rrf.setDeleted(false);
            rrf.setTimeCreate(new Timestamp(System.currentTimeMillis()));
            rrf.setAttachmentPath(String.format("%s/%s", uploadingDir, fileContainer.getFilename()));
            rrf.setFileName(fileContainer.getFilename());
            rrf.setOriginalFileName(fileContainer.getOriginalFilename());
            rrf.setFileExtension(fileContainer.getExtension());
            rrf.setHash(fileContainer.getFileHash());
            rrf.setFileSize(fileContainer.getFileSize());
            rrf.setOperator(operator);
        }
        else {
            rrf = createRkkFile(fileContainer, regRkkFileDto);
        }
        return rrf;
    }

    private RegRkkFile changeRkkFile(RegRkkFileDto regRkkFileDto) throws IOException {
        RegRkkFile rrf = getRegRkkFileById(regRkkFileDto.getId());
        if (rrf != null) {
            DocRkk docRkk = getDocRkk(regRkkFileDto);
            ClsGroupAttachment group = getGroupAttachment(regRkkFileDto);
            ClsTypeAttachment type = getTypeAttachment(regRkkFileDto);
            ClsOrganization participant = getOrganization(regRkkFileDto);
            ClsEmployee operator = getEmployee(regRkkFileDto);
            String sSigningDate = regRkkFileDto.getSigningDate();
            Date signingDate = parseDateFromForm(sSigningDate);
//            Date signingDate = regRkkFileDto.getSigningDate();

            rrf.setDocRkk(docRkk);
            rrf.setGroup(group);
            rrf.setType(type);
            rrf.setParticipant(participant);
            rrf.setNumberAttachment(regRkkFileDto.getNumberAttachment());
            rrf.setSigningDate(signingDate);
            rrf.setDeleted(false);
            rrf.setOperator(operator);
        }

        return rrf;
    }

    private RegRkkFile createRkkFile(FileContainer fileContainer, RegRkkFileDto regRkkFileDto) throws IOException {
        DocRkk docRkk                = getDocRkk(regRkkFileDto);
        ClsGroupAttachment group     = getGroupAttachment(regRkkFileDto);
        ClsTypeAttachment type       = getTypeAttachment(regRkkFileDto);
        ClsOrganization participant = getOrganization(regRkkFileDto);
        ClsEmployee operator = getEmployee(regRkkFileDto);
        String sSigningDate = regRkkFileDto.getSigningDate();
        Date signingDate = parseDateFromForm(sSigningDate);
//        Date signingDate = regRkkFileDto.getSigningDate();

        RegRkkFile rrf = RegRkkFile.builder()
                .docRkk(docRkk)
                .group(group)
                .type(type)
                .participant(participant)
                .numberAttachment(regRkkFileDto.getNumberAttachment())
                .signingDate(signingDate)
                .pageCount(fileContainer.getPageCount())
                .isDeleted(false)
                .timeCreate(new Timestamp(System.currentTimeMillis()))
                .attachmentPath(String.format("%s/%s", uploadingDir, fileContainer.getFilename()))
                .fileName(fileContainer.getFilename())
                .originalFileName(fileContainer.getOriginalFilename())
                .fileExtension(fileContainer.getExtension())
                .hash(fileContainer.getFileHash())
                .fileSize(fileContainer.getFileSize())
                .operator(operator)
                .build();
        return rrf;
    }

    private FileContainer createFileContainer(MultipartFile part, String substring) throws IOException {
        final String originalFilename = part.getOriginalFilename();
        final String absolutePath = Paths.get(uploadingDir).toFile().getAbsolutePath();
        final String filename = substring + "_" + UUID.randomUUID();
        final String extension = fileService.getFileExtension(originalFilename);

        File file = new File(String.format("%s/%s%s", absolutePath, filename, extension));
        part.transferTo(file);

        final String fileHash = fileService.getFileHash(file);
        final long fileSize = Files.size(file.toPath());
        final Integer pageCount = fileService.getPageCount(file, extension);

        FileContainer fileContainer = FileContainer.builder()
                                        .absolutePath(absolutePath)
                                        .filename(filename)
                                        .originalFilename(originalFilename)
                                        .extension(extension)
                                        .file(file)
                                        .fileHash(fileHash)
                                        .fileSize(fileSize)
                                        .pageCount(pageCount)
                                        .build();
        return fileContainer;
    }

    private DocRkk getDocRkk(RegRkkFileDto rkkFileDto){
//        Long docRkkId = Long.parseLong(rkkFileDto.getDocRkkId());
        Long docRkkId = rkkFileDto.getDocRkkId();
        DocRkk docRkk = getDocRkkById(docRkkId);

        return docRkk;
    }

    private ClsGroupAttachment getGroupAttachment(RegRkkFileDto rkkFileDto){
//        Long groupId = Long.parseLong(rkkFileDto.getGroupId());
        Long groupId = rkkFileDto.getGroupId();
        ClsGroupAttachment group = getGroupAttachmentById(groupId);

        return group;
    }

    private ClsTypeAttachment getTypeAttachment(RegRkkFileDto rkkFileDto){
//        Long typeId = Long.parseLong(rkkFileDto.getTypeId());
        Long typeId = rkkFileDto.getTypeId();
        ClsTypeAttachment type = getTypeAttachmentById(typeId);

        return type;
    }

    private ClsOrganization getOrganization(RegRkkFileDto rkkFileDto) {
//        Long organizationId = Long.parseLong(rkkFileDto.getParticipantId());
        Long organizationId = rkkFileDto.getParticipantId();
        ClsOrganization organization = getOrganizationById(organizationId);

        return organization;
    }

    private ClsEmployee getEmployee(RegRkkFileDto rkkFileDto) {
//        Long employeeId = Long.parseLong(rkkFileDto.getUserId());
        Long employeeId = rkkFileDto.getUserId();
        ClsEmployee employee = getEmployeeById(employeeId);

        return employee;
    }

    @Override
    public RegRkkFile editRkkFile(RegRkkFileDto regRkkFileDto) {
        RegRkkFile rrf = null;
        try {
//            Long docRkkId = Long.parseLong(regRkkFileDto.getDocRkkId());
            Long docRkkId = regRkkFileDto.getDocRkkId();
            if (regRkkFileDto.getId() != null) {
                rrf = changeRkkFile(regRkkFileDto);
            }
        } catch (IOException ex){
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        }
        return rrf;
    }

    @Override
    public RegRkkFile getRegRkkFile(RegRkkFileDto regRkkFileDto) {
        Long rkkFileId = regRkkFileDto.getId();
        RegRkkFile regRkkFile = (rkkFileId == null) ?
                null : regRkkFileRepo.findById(rkkFileId).orElse(null);
        return regRkkFile;
    }

    @Override
    public void savePreviousFileCopy(RegRkkFile rkkFile) {
        if (rkkFile != null) {
            RegRkkFileVersion rkkFileVersion = new RegRkkFileVersion(rkkFile);
            regRkkFileVersionRepo.save(rkkFileVersion);
        }
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
    public void deleteRkkFile(RegRkkFileDto rkkFileDto) {
        Long rkkRegFileId = rkkFileDto.getId();
        if (rkkRegFileId != null) {
            RegRkkFile rkkFile = regRkkFileRepo.findById(rkkRegFileId).orElse(null);
            rkkFile.setDeleted(true);
            regRkkFileRepo.save(rkkFile);
        }
    }

}
