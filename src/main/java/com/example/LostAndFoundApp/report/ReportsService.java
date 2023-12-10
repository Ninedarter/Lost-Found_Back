package com.example.LostAndFoundApp.report;

import com.example.LostAndFoundApp.user.Status;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsService {

    private final ReportRepository reportRepository;

    private final UserRepository userRepository;


    public List<UserReportsDTO> getAllReports() {
        List<UserReportsDTO> userReportsList = new ArrayList<>();

        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getReports().size() > 0 && user.getStatus()!= Status.BANNED) {
                UserReportsDTO userReportsDTO = new UserReportsDTO();
                userReportsDTO.setUserEmail(user.getEmail());
                userReportsDTO.setReports(mapReportsToDTOs(user.getReports()));
                userReportsList.add(userReportsDTO);
            }

        }

        return userReportsList;
    }

    List<ReportInfoDTO> mapReportsToDTOs(List<Report> reports) {
        return reports.stream()
                .map(this::mapReportToDTO)
                .collect(Collectors.toList());
    }

    ReportInfoDTO mapReportToDTO(Report report) {
        ReportInfoDTO reportInfoDTO = new ReportInfoDTO();
        reportInfoDTO.setId(report.getId());
        reportInfoDTO.setDescription(report.getDescription());
        reportInfoDTO.setReportTime(report.getReportTime());


        UserInfoDTO reporteeDTO = mapUserToDTO(report.getReportee());
        reportInfoDTO.setReportee(reporteeDTO);

        return reportInfoDTO;
    }

    UserInfoDTO mapUserToDTO(User user) {
        UserInfoDTO userDTO = new UserInfoDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getEmail()); // You might want to use a different field for username
        userDTO.setAccountNonLocked(user.isAccountNonLocked());

        return userDTO;
    }

}
