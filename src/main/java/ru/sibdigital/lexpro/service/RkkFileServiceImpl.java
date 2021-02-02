package ru.sibdigital.lexpro.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Log4j2
@Service
public class RkkFileServiceImpl extends SuperServiceImpl implements RkkFileService{

    public RegRkkFile construct(MultipartFile part, DocRkk docRkk, String uploadingDir){
        RegRkkFile rdrf = null;
        try {

            final String absolutePath = Paths.get(uploadingDir).toFile().getAbsolutePath();
            final String filename = docRkk.getId().toString() + "_" + UUID.randomUUID();
            final String originalFilename = part.getOriginalFilename();
            String extension = FileService.getFileExtension(originalFilename);
            File file = new File(String.format("%s/%s%s", absolutePath, filename, extension));
            part.transferTo(file);

            final String fileHash = FileService.getFileHash(file);
            final long size = Files.size(file.toPath());

            final List<RegRkkFile> files = regRkkFileRepo.findRegRkkFileByDocRkkAndHash(docRkk, fileHash);

            if (!files.isEmpty()){
                rdrf = files.get(0);
            }else{
                rdrf = RegRkkFile.builder()
                        .attachmentPath(String.format("%s/%s", uploadingDir, filename))
                        .fileName(filename)
                        .originalFileName(originalFilename)
                        .isDeleted(false)
                        .fileExtension(extension)
                        .fileSize(size)
                        .hash(fileHash)
                        .timeCreate(new Timestamp(System.currentTimeMillis()))
                        .build();

                if (docRkk != null){
                    rdrf.setDocRkk(docRkk);
                }
            }

        } catch (IOException ex){
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        }
        return rdrf;
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
}
