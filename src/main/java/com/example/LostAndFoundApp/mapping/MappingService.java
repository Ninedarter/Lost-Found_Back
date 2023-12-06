package com.example.LostAndFoundApp.mapping;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.lost.LostItem;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.report.Report;
import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MappingService {

    private final UserRepository userRepository;

    public LostItem mapLostItem(LostItemRequest request) {
        LostItem item = new LostItem();
        item.setId(request.getId());
        item.setCategory(request.getCategory());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setDateLost(request.getDateLost());
        item.setUser(userRepository.findByEmail(request.getEmail()).get());
        item.setDateLost(request.getDateLost());
        item.setCoordinates(new Coordinates(request.getLatitude(), request.getLongitude()));
        return item;
    }


    public FoundItem mapFoundItem(FoundItemRequest request) {

        FoundItem item = new FoundItem();
        //added id field
        item.setId(request.getId());
        item.setCategory(request.getCategory());
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setDateFound(request.getDateFound());
        item.setUser(userRepository.findByEmail(request.getEmail()).get());
        item.setDateFound(request.getDateFound());
        item.setCoordinates(request.getCoordinates());
        item.setCreationTime(LocalDateTime.now());
        return item;
    }


    public Report mapReport(ReportUserRequest request) {
        Optional<User> reportedUser = userRepository.findByEmail(request.getReportedUserEmail());
        if (reportedUser.isPresent()) {
            Report report = new Report();
            report.setUser(reportedUser.get());
            report.setDescription(request.getDescription());
            report.setReportTime(LocalDate.now());
            return report;
        } else {
            throw new EntityNotFoundException();
        }

    }
}
