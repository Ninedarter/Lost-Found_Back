package com.example.LostAndFoundApp.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ReportInfoDTOTest {

    @Test
    @DisplayName("Test getters and setters")
    public void testGettersAndSetters() {

        ReportInfoDTO reportInfoDTO = new ReportInfoDTO();
        reportInfoDTO.setId(1L);
        reportInfoDTO.setDescription("Test description");
        reportInfoDTO.setReportTime(LocalDate.now());

        UserInfoDTO reportee = new UserInfoDTO();
        reportee.setEmail("test@example.com");
        reportInfoDTO.setReportee(reportee);

        Long id = reportInfoDTO.getId();
        String description = reportInfoDTO.getDescription();
        LocalDate reportTime = reportInfoDTO.getReportTime();
        UserInfoDTO retrievedReportee = reportInfoDTO.getReportee();

        Assertions.assertEquals(1L, id);
        Assertions.assertEquals("Test description", description);
        Assertions.assertEquals(LocalDate.now(), reportTime);

        Assertions.assertNotNull(retrievedReportee);
        Assertions.assertEquals("test@example.com", retrievedReportee.getEmail());
    }

}
