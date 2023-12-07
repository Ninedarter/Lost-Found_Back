package com.example.LostAndFoundApp.mapping;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.FoundItemService;
import com.example.LostAndFoundApp.item.found.SampleTestObjects;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.lost.LostItem;
import com.example.LostAndFoundApp.item.lost.LostItemRepository;
import com.example.LostAndFoundApp.item.lost.LostItemService;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.report.Report;
import com.example.LostAndFoundApp.report.ReportUserRequest;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MappingServiceTest {

    @Mock
    private CoordinatesRepository coordinatesRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FoundItemService foundItemService;

    @Mock
    private LostItemRepository lostItemRepository;

    @InjectMocks
    private com.example.LostAndFoundApp.item.lost.LostItemService LostItemService;

    @InjectMocks
    MappingService mappingService;

    private User user;
    private Coordinates coordinates;


    @BeforeEach
    void setUp() {

        user = SampleTestObjects.createUser();
        coordinates = SampleTestObjects.createCoordinates();
    }


    @Test
    @DisplayName("Mapping FoundItemRequest to FoundItem with Valid User Email")
    public void testMapFoundItem() {

        FoundItem foundItem1 = SampleTestObjects.createFoundItem(user, coordinates);
        FoundItem foundItem2 = SampleTestObjects.createFoundItem(user, coordinates);

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        FoundItem result = mappingService.mapFoundItem(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getCategory(), result.getCategory());
        Assertions.assertEquals(request.getDescription(), result.getDescription());
        Assertions.assertEquals(request.getTitle(), result.getTitle());
        Assertions.assertEquals(request.getDateFound(), result.getDateFound());
        Assertions.assertEquals(request.getDateFound(), result.getDateFound());
        Assertions.assertNotNull(result.getUser());
        Assertions.assertEquals(request.getLatitude(), result.getCoordinates().getLatitude());
        Assertions.assertEquals(request.getLongitude(), result.getCoordinates().getLongitude());

        verify(userRepository).findByEmail(request.getEmail());

    }

    @Test
    @DisplayName("Mapping LostItemRequest to FoundItem with Valid User Email")
    public void testMapLostItem() {

        LostItemRequest request = com.example.LostAndFoundApp.item.lost.SampleTestObjects.createLostItemRequest();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        LostItem result = mappingService.mapLostItem(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(request.getCategory(), result.getCategory());
        Assertions.assertEquals(request.getDescription(), result.getDescription());
        Assertions.assertEquals(request.getTitle(), result.getTitle());
        Assertions.assertEquals(request.getDateLost(), result.getDateLost());
        Assertions.assertEquals(request.getDateLost(), result.getDateLost());
        Assertions.assertNotNull(result.getUser());
        Assertions.assertEquals(request.getLatitude(), result.getCoordinates().getLatitude());
        Assertions.assertEquals(request.getLongitude(), result.getCoordinates().getLongitude());

        verify(userRepository).findByEmail(request.getEmail());

    }

    @Test
    @DisplayName("Mapping ReportUserRequest to Report with Valid User Email")
    public void testMapReport() {

        User user = SampleTestObjects.createUser();
        ReportUserRequest request = SampleTestObjects.createReportUserRequest();

        // Mock the behavior of userRepository.findById
        when(userRepository.findById(request.getReportedUserId())).thenReturn(Optional.of(user));

        // Mock the behavior of userRepository.findByEmail
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(createReportingUser()));

        // Call the method you are testing
        Report result = mappingService.mapReport(request, "reportingUser@example.com");

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result.getUser());
        Assertions.assertEquals(request.getDescription(), result.getDescription());
        Assertions.assertEquals(LocalDate.now(), result.getReportTime());

        // Verify that the expected methods were called
        verify(userRepository).findById(request.getReportedUserId());
        verify(userRepository).findByEmail(anyString());

    }

    public static User createReportingUser() {
        return User.builder()
                .id(2L)
                .firstname("Reporting")
                .lastname("User")
                .email("reporting.user@example.com")
                .phoneNumber("987654321")
                .dob(LocalDate.of(1990, 5, 10))
                .gender(Gender.FEMALE)
                .password("reportingPassword")
                .role(Role.USER)
                .build();
    }
}