package com.example.LostAndFoundApp.report;

import com.example.LostAndFoundApp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ReportTest {

    private Report report;
    private User user;
    private User reportee;

    @BeforeEach
    void setUp() {

        user = new User();
        reportee = new User();
        report = new Report(user, reportee, "Test description", LocalDate.now());
    }

    @Test
    void testGettersAndSetters() {

        Assertions.assertNull(report.getId());
        Assertions.assertEquals(user, report.getUser());
        Assertions.assertEquals(reportee, report.getReportee());
        Assertions.assertEquals("Test description", report.getDescription());
        Assertions.assertEquals(LocalDate.now(), report.getReportTime());

        report.setId(1L);
        User newUser = new User();
        User newReportee = new User();
        report.setUser(newUser);
        report.setReportee(newReportee);
        report.setDescription("Updated description");
        LocalDate newReportTime = LocalDate.now().minusDays(1);
        report.setReportTime(newReportTime);

        Assertions.assertEquals(1L, report.getId());
        Assertions.assertEquals(newUser, report.getUser());
        Assertions.assertEquals(newReportee, report.getReportee());
        Assertions.assertEquals("Updated description", report.getDescription());
        Assertions.assertEquals(newReportTime, report.getReportTime());
    }

    @Test
    void testConstructor() {

        Report newReport = new Report(user, reportee, "Another description", LocalDate.now().minusDays(2));

        Assertions.assertNull(newReport.getId());
        Assertions.assertEquals(user, newReport.getUser());
        Assertions.assertEquals(reportee, newReport.getReportee());
        Assertions.assertEquals("Another description", newReport.getDescription());
        Assertions.assertEquals(LocalDate.now().minusDays(2), newReport.getReportTime());
    }
}
