package ru.sibdigital.lexpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sibdigital.lexpro.model.ClsEmployee;
import ru.sibdigital.lexpro.model.ClsNpaType;
import ru.sibdigital.lexpro.model.ClsOrganization;
import ru.sibdigital.lexpro.model.ClsRkkStatus;

import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DocRkkDto {

    private Long            id;
    private String          rkkNumber;
    private String          npaName;
    private String          registrationDate;
    private String          introductionDate;
    private String          deadline;
    private Integer         sessionNumber;
    private String          sessionDate;
    private Boolean         includedInAgenta;
    private String          npaType;
    private String          responsibleOrganization;
    private String          responsibleEmployee;
    private String          status;

}
