package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.mapping.MappingService;
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

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MappingServiceForFoundItemTest {

    @Mock
    private CoordinatesRepository coordinatesRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FoundItemService foundItemService;

    @InjectMocks
    MappingService mappingService;

    private User user;
    private Coordinates coordinates;
    private FoundItem foundItem1;
    private FoundItem foundItem2;

    @BeforeEach
    void setUp() {

        user = SampleTestObjects.createUser();
        coordinates = SampleTestObjects.createCoordinates();
        foundItem1 = SampleTestObjects.createFoundItem(user, coordinates);
        foundItem2 = SampleTestObjects.createFoundItem(user, coordinates);
    }



    @Test
    @DisplayName("Mapping FoundItemRequest to FoundItem with Valid User Email")
    public void testMapFoundItem() {

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


}