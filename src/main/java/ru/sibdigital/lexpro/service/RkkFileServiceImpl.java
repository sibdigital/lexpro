package ru.sibdigital.lexpro.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sibdigital.lexpro.dto.FileContainer;
import ru.sibdigital.lexpro.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

import java.util.UUID;


@Log4j2
@Service
public class RkkFileServiceImpl extends SuperServiceImpl implements RkkFileService{

    @Value("${upload.path:/rkk}")
    String uploadingDir;

    public RegRkkFile constructRkkFile(MultipartFile part, DocRkk docRkk, ClsEmployee operator){
        RegRkkFile rdrf = null;
        try {
            FileContainer fileContainer = createFileContainer(part, docRkk.getId());
            rdrf = createRkkFile(fileContainer, docRkk, operator);

        } catch (IOException ex){
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error(String.format("file was not saved cause: %s", ex.getMessage()));
        }
        return rdrf;
    }

    private RegRkkFile createRkkFile(FileContainer fileContainer, DocRkk docRkk, ClsEmployee operator) throws IOException {
        RegRkkFile rrf = RegRkkFile.builder()
                        .attachmentPath(String.format("%s/%s", uploadingDir, fileContainer.getFilename()))
                        .fileName(fileContainer.getFilename())
                        .originalFileName(fileContainer.getOriginalFilename())
                        .isDeleted(false)
                        .fileExtension(fileContainer.getExtension())
                        .fileSize(fileContainer.getFileSize())
                        .hash(fileContainer.getFileHash())
                        .timeCreate(new Timestamp(System.currentTimeMillis()))
                        .pageCount(fileContainer.getPageCount())
                        .docRkk(docRkk)
                        .operator(operator)
                        .build();
        return rrf;
    }

    private FileContainer createFileContainer(MultipartFile part, Long docRkkId) throws IOException {
        final String originalFilename = part.getOriginalFilename();
        final String absolutePath = Paths.get(uploadingDir).toFile().getAbsolutePath();
        final String filename = docRkkId.toString() + "_" + UUID.randomUUID();
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

}
