package com.example.LostAndFoundApp.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class UserReportDTOTest {

    @Test
    @DisplayName("Test getters and setters")
    public void testGettersAndSetters() {
        UserReportsDTO userReportsDTO = new UserReportsDTO();
        userReportsDTO.setUserEmail("test@example.com");
        List<ReportInfoDTO> reports = Arrays.asList(new ReportInfoDTO(), new ReportInfoDTO());
        userReportsDTO.setReports(reports);

        String userEmail = userReportsDTO.getUserEmail();
        List<ReportInfoDTO> retrievedReports = userReportsDTO.getReports();

        Assertions.assertEquals("test@example.com", userEmail);
        Assertions.assertEquals(reports, retrievedReports);
    }

    @Test
    @DisplayName("Test toString method")
    public void testToStringMethod() {
        UserReportsDTO userReportsDTO = new UserReportsDTO();
        userReportsDTO.setUserEmail("test@example.com");
        List<ReportInfoDTO> reports = Arrays.asList(new ReportInfoDTO(), new ReportInfoDTO());
        userReportsDTO.setReports(reports);

        String toStringResult = userReportsDTO.toString();

        Assertions.assertTrue(toStringResult.contains("UserReportsDTO"));
        Assertions.assertTrue(toStringResult.contains("reports=" + reports));
    }
}
