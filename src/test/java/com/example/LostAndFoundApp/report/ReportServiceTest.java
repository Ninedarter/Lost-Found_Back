package com.example.LostAndFoundApp.report;

import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportServiceTest {

    @Test
    void getAllReports_WithNoReports_ReturnsEmptyList() {
        ReportRepository reportRepository = mock(ReportRepository.class);
        UserRepository userRepository = mock(UserRepository.class);

        ReportsService reportsService = new ReportsService(reportRepository, userRepository);

        User userWithNoReports = new User();
        userWithNoReports.setEmail("user1@example.com");
        userWithNoReports.setReports(Collections.emptyList());

        when(userRepository.findAll()).thenReturn(Collections.singletonList(userWithNoReports));

        List<UserReportsDTO> result = reportsService.getAllReports();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void mapReportsToDTOs_WithReports_ReturnsReportInfoDTOList() {
        ReportsService reportsService = new ReportsService(mock(ReportRepository.class), mock(UserRepository.class));

        User user = new User();
        Report report1 = new Report(user, user, "Description 1", LocalDate.now());
        Report report2 = new Report(user, user, "Description 2", LocalDate.now());

        List<ReportInfoDTO> result = reportsService.mapReportsToDTOs(Arrays.asList(report1, report2));

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());

        ReportInfoDTO reportInfoDTO1 = result.get(0);
        Assertions.assertEquals("Description 1", reportInfoDTO1.getDescription());

        ReportInfoDTO reportInfoDTO2 = result.get(1);
        Assertions.assertEquals("Description 2", reportInfoDTO2.getDescription());
    }

    @Test
    void mapReportToDTO_WithValidReport_ReturnsReportInfoDTO() {
        ReportsService reportsService = new ReportsService(mock(ReportRepository.class), mock(UserRepository.class));

        User user = new User();
        Report report = new Report(user, user, "Description", LocalDate.now());

        ReportInfoDTO result = reportsService.mapReportToDTO(report);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Description", result.getDescription());
    }

    @Test
    void mapUserToDTO_WithValidUser_ReturnsUserInfoDTO() {
        ReportsService reportsService = new ReportsService(mock(ReportRepository.class), mock(UserRepository.class));

        User user = new User();
        user.setEmail("user@example.com");

        UserInfoDTO result = reportsService.mapUserToDTO(user);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("user@example.com", result.getEmail());
    }


}
