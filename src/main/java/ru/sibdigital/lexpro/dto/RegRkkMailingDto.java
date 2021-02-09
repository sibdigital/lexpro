package ru.sibdigital.lexpro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sibdigital.lexpro.model.ClsOrganization;
import ru.sibdigital.lexpro.model.RegRkkMailing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RegRkkMailingDto {
    private Long    id;
    private Long    regRkkMailingId;
    private String  date;
    private String  note;
    private Long    organizationId;

    public RegRkkMailingDto(RegRkkMailing regRkkMailing) {
        if (regRkkMailing != null) {
            this.setRegRkkMailingId(regRkkMailing.getId());
            this.setNote(regRkkMailing.getNote());
            this.setDate(regRkkMailing.getDate().toString());

            ClsOrganization organization = regRkkMailing.getOrganization();
            if (organization != null) {
                this.setOrganizationId(organization.getId());
            }
        }
    }
}
