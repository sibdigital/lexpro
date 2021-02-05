package ru.sibdigital.lexpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RegRkkAttachmentDto {

    private Long    id;
    private Long    docRkkId;
    private Long    groupId;
    private Long    typeId;
    private Long    participantId;
    private String  numberAttachment;
    private String  signingDate;
    private Long    rkkFileId;
    private Long    newFileId;

}
