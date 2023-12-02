package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import com.example.LostAndFoundApp.mapping.MappingService;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoundItemServiceTest {

    @Mock
    private FoundItemRepository foundItemRepository;

    @Mock
    private CoordinatesRepository coordinatesRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FoundItemService foundItemService;

    @InjectMocks
    MappingService mappingService;

    private FoundItem foundItem1;
    private FoundItem foundItem2;
    private User savedUser;
    private FoundItem savedFoundItem1;
    private FoundItem savedFoundItem2;

    @BeforeEach
    void setUp() {

        User user = SampleTestObjects.createUser();
        Coordinates coordinates = SampleTestObjects.createCoordinates();
        foundItem1 = SampleTestObjects.createFoundItem(user, coordinates);
        foundItem2 = SampleTestObjects.createFoundItem(user, coordinates);
    }

    @Test
    @DisplayName("Add Two Found Items and Verify Retrieval")
    public void getAll() {

        List<FoundItem> foundItems = Arrays.asList(foundItem1, foundItem2);

        when(foundItemRepository.findAll()).thenReturn(foundItems);

        List<FoundItem> result = foundItemService.getAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(foundItems, result);
    }

//    @Test
//    @DisplayName("Add new Found Item - Mapping and Save Verification")
//    public void addNewFoundItem() {
//
//        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
//        FoundItem mappedItem = foundItem1;
//
//        when(foundItemRepository.findById(anyLong())).thenReturn(Optional.empty());
//        when(mappingService.mapFoundItem(request)).thenReturn(mappedItem);
//
//        FoundItemResponse response = foundItemService.add(request);
//
//        Assertions.assertEquals("Created successfully", response.getMessage());
//        verify(foundItemRepository).save(mappedItem);
//
//    }

    @Test
    @DisplayName("Add existing found item - verify that item is not duplicated")
    public void addExistingFoundItem() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItem existingItem = foundItem1;

        when(foundItemRepository.findById(anyLong())).thenReturn(Optional.of(existingItem));

        FoundItemResponse response = foundItemService.add(request);

        Assertions.assertEquals("Item already exists", response.getMessage());
        verify(coordinatesRepository, never()).save(any());
        verify(foundItemRepository, never()).save(any());
    }

    @Test
    @DisplayName("Get Found Item by ID - Item Found")
    public void findExistingItemById() {
        long existingFoundItemID = foundItem1.getId();
        Optional<FoundItem> optionalFoundItem = Optional.of(foundItem1);

        when(foundItemRepository.findById(existingFoundItemID)).thenReturn(optionalFoundItem);

        FoundItemResponse response = foundItemService.getById(existingFoundItemID);

        Assertions.assertEquals("FOUND BY ID", response.getMessage());
        Assertions.assertEquals(foundItem1, response.getItem());
    }

    @Test
    @DisplayName("Get Found Item by ID - Item Not Found")
    public void findNotExistingItemById() {
        Long nonExistingId = foundItem1.getId();
        Optional<FoundItem> optionalFoundItem = Optional.empty();

        when(foundItemRepository.findById(nonExistingId)).thenReturn(optionalFoundItem);

        FoundItemResponse response = foundItemService.getById(nonExistingId);

        Assertions.assertEquals("NOT FOUND", response.getMessage());
        Assertions.assertNull(response.getItem());
    }

    @Test
    @DisplayName("Delete existing item - verify that item is deleted")
    public void deleteExistingItem() {
        Long existingId = foundItem1.getId();
        Optional<FoundItem> optionalFoundItem = Optional.of(foundItem1);

        when(foundItemRepository.findById(existingId)).thenReturn(optionalFoundItem);

        FoundItemResponse response = foundItemService.delete(existingId);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Deleted successfully", response.getMessage());
    }

    @Test
    @DisplayName("Delete non existing item - verify correct response")
    public void deleteNonExistingItem() {
        Long nonExistingId = foundItem1.getId();
        Optional<FoundItem> optionalFoundItem = Optional.empty();

        when(foundItemRepository.findById(nonExistingId)).thenReturn(optionalFoundItem);

        FoundItemResponse response = foundItemService.delete(nonExistingId);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found", response.getMessage());
    }

    @Test
    @DisplayName("Update existing item - verify correct response")
    public void updateExistingItem() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        request.setId(1L);
        FoundItem mappedItem = foundItem1;

        when(mappingService.mapFoundItem(request)).thenReturn(mappedItem);

        when(foundItemRepository.findById(request.getId())).thenReturn(Optional.of(foundItem1));

        String response = foundItemService.update(request);

        Assertions.assertEquals("UPDATED", response);

        ArgumentCaptor<FoundItem> foundItemCaptor = ArgumentCaptor.forClass(FoundItem.class);
        verify(foundItemRepository).save(foundItemCaptor.capture());


    }
}


