package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.item.lost.response.LostItemResponse;
import com.example.LostAndFoundApp.mapping.MappingService;
import com.example.LostAndFoundApp.user.User;
import com.example.LostAndFoundApp.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LostItemServiceTest {

    @Mock
    private LostItemRepository lostItemRepository;

    @Mock
    private CoordinatesRepository coordinatesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private LostItemService lostItemService;


    private LostItem lostItem1;
    private LostItem lostItem2;
    private User user;
    private Coordinates coordinates;
    private LostItem savedLostItem1;
    private LostItem savedLostItem2;

    @BeforeEach
    void setUp() {

        user = SampleTestObjects.createUser();
        coordinates = SampleTestObjects.createCoordinates();
        lostItem1 = SampleTestObjects.createLostItem(user, coordinates);
        lostItem2 = SampleTestObjects.createLostItem(user, coordinates);
    }

    @Test
    @DisplayName("Add Two Lost Items and Verify Retrieval")
    public void getAll() {

        List<LostItem> lostItems = Arrays.asList(lostItem1, lostItem2);

        when(lostItemRepository.findAll()).thenReturn(lostItems);

        List<LostItem> result = lostItemService.getAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(lostItems, result);
    }

    @Test
    @DisplayName("Add new Lost Item - Mapping and Save Verification")
    public void addNewLostItem() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItem mappedItem = lostItem1;

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(mappingService.mapLostItem(request)).thenReturn(mappedItem);

        LostItemResponse response = lostItemService.add(request);

        Assertions.assertEquals("Created successfully", response.getMessage());
        verify(lostItemRepository).save(mappedItem);

    }

    @Test
    @DisplayName("Add existing lost item - verify that item is not duplicated")
    public void addExistingLostItem() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItem existingItem = lostItem1;

        when(lostItemRepository.findById(anyLong())).thenReturn(Optional.of(existingItem));

        LostItemResponse response = lostItemService.add(request);

        Assertions.assertEquals("Item already exists", response.getMessage());
        verify(coordinatesRepository, never()).save(any());
        verify(lostItemRepository, never()).save(any());
    }

    @Test
    @DisplayName("Get Lost Item by ID - Item Found")
    public void findExistingItemById() {
        long existingLostItemID = lostItem1.getId();
        Optional<LostItem> optionalLostItem = Optional.of(lostItem1);

        when(lostItemRepository.findById(existingLostItemID)).thenReturn(optionalLostItem);

        LostItemResponse response = lostItemService.getById(existingLostItemID);

        Assertions.assertEquals("FOUND BY ID 1", response.getMessage());
        Assertions.assertEquals(lostItem1, response.getItem());
    }

    @Test
    @DisplayName("Get Lost Item by ID - Item Not Found")
    public void findNotExistingItemById() {
        Long nonExistingId = lostItem1.getId();
        Optional<LostItem> optionalLostItem = Optional.empty();

        when(lostItemRepository.findById(nonExistingId)).thenReturn(optionalLostItem);

        LostItemResponse response = lostItemService.getById(nonExistingId);

        Assertions.assertEquals("NOT FOUND", response.getMessage());
        Assertions.assertNull(response.getItem());
    }

    @Test
    @DisplayName("Delete existing item - verify that item is deleted")
    public void deleteExistingItem() {
        Long existingId = lostItem1.getId();
        Optional<LostItem> optionalLostItem = Optional.of(lostItem1);

        when(lostItemRepository.findById(existingId)).thenReturn(optionalLostItem);

        LostItemResponse response = lostItemService.delete(existingId);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Deleted successfully", response.getMessage());
    }

    @Test
    @DisplayName("Delete non existing item - verify correct response")
    public void deleteNonExistingItem() {
        Long nonExistingId = lostItem1.getId();
        Optional<LostItem> optionalLostItem = Optional.empty();

        when(lostItemRepository.findById(nonExistingId)).thenReturn(optionalLostItem);

        LostItemResponse response = lostItemService.delete(nonExistingId);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found", response.getMessage());
    }

    @Test
    @DisplayName("Update existing item - verify correct response")
    public void updateExistingItem() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItem mappedItem =  SampleTestObjects.createLostItem(user, coordinates);

        when(mappingService.mapLostItem(request)).thenReturn(mappedItem);
        when(lostItemRepository.findById(request.getId())).thenReturn(Optional.of(lostItem1));

        LostItemResponse response = lostItemService.update(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());

        // Verify that save was called with the correct argument
        ArgumentCaptor<LostItem> lostItemCaptor = ArgumentCaptor.forClass(LostItem.class);
        verify(lostItemRepository).save(lostItemCaptor.capture());

        // Assert the saved item
        LostItem savedItem = lostItemCaptor.getValue();
        Assertions.assertEquals(mappedItem.getId(), savedItem.getId());

        // Verify that findById was called with the correct ID
        verify(lostItemRepository).findById(request.getId());

    }

    @Test
    @DisplayName("Update non existing item - verify exception")
    public void updateNonExistingItem() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        when(lostItemRepository.findById(request.getId())).thenReturn(Optional.empty());

        LostItemResponse exception = lostItemService.update(request);

        Assertions.assertEquals("Item to update not found", exception.getMessage());
    }

//    @Test
//    @DisplayName("Get item by coordinates - item found")
//    public void getItemByCoordinatesSuccess() {
//        LostItemRequest request = SampleTestObjects.createLostItemRequest();
//
//        // Assume that there is an item with the given coordinates
//        when(lostItemRepository.findByCoordinatesId(anyLong())).thenReturn(Optional.of(lostItem1));
//
//        LostItemResponse response = lostItemService.getByCoordinates(request);
//
//        Assertions.assertTrue(response.isSuccess());
//        Assertions.assertNull(response.getMessage());
//        Assertions.assertEquals(lostItem1, response.getItem());
//    }

//    @Test
//    @DisplayName("Get item by coordinates - item not found")
//    public void getItemByCoordinatesNotFound() {
//        LostItemRequest request = SampleTestObjects.createLostItemRequest();
//
//        // Assume that there is no item with the given coordinates
//        when(lostItemRepository.findByCoordinatesId(anyLong())).thenReturn(Optional.empty());
//
//        LostItemResponse response = lostItemService.getByCoordinates(request);
//
//        Assertions.assertFalse(response.isSuccess());
//        Assertions.assertEquals("Not found by coordinates", response.getMessage());
//        Assertions.assertNull(response.getItem());
//    }

//    @Test
//    @DisplayName("Get ID by coordinates - ID found")
//    public void getIdByCoordinatesSuccess() {
//        Double latitude = 40.7128;
//        Double longitude = -74.0060;
//
//        when(coordinatesRepository.findIdByLatitudeAndLongitude(latitude, longitude)).thenReturn(Optional.of(1L));
//
//        Long result = lostItemService.getIdByCoordinates(latitude, longitude);
//
//        Assertions.assertEquals(1L, result);
//    }
//
//    @Test
//    @DisplayName("Get ID by coordinates - ID not found")
//    public void getIdByCoordinatesNotFound() {
//        Double latitude = 40.7128;
//        Double longitude = -74.0060;
//
//        when(coordinatesRepository.findIdByLatitudeAndLongitude(latitude, longitude)).thenReturn(Optional.empty());
//
//        Long result = lostItemService.getIdByCoordinates(latitude, longitude);
//
//        Assertions.assertEquals(0L, result);
//    }

    @Test
    @DisplayName("Get all user lost items for existing user")
    public void getAllUserLostItemsSuccess() {
        String email = user.getEmail();

        when(lostItemRepository.findByUser_Email(email)).thenReturn(Collections.emptyList());

        List<LostItem> lostItems = lostItemService.getAllUserLostItems(email);

        verify(lostItemRepository, times(1)).findByUser_Email(email);
        Assertions.assertNotNull(lostItems);
        Assertions.assertEquals(0, lostItems.size());
    }

    @Test
    @DisplayName("Update existing user lost item")
    public void updateUserLostItemSuccess() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        List<LostItem> lostItems = Arrays.asList(SampleTestObjects.createLostItem(user, coordinates));

        when(lostItemRepository.findByUser_Email(anyString())).thenReturn(lostItems);
        when(mappingService.mapLostItem(any())).thenReturn(lostItem1);

        LostItemResponse response = lostItemService.updateUserLostItem(request);

        Assertions.assertEquals("Updated successfully", response.getMessage());

    }

    @Test
    @DisplayName("Update existing lost item which does not belong to the user")
    public void updateUserLostItemDoesNotBelong() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        lostItem1.setId(3L);
        lostItem2.setId(4L);

        List<LostItem> lostItems = SampleTestObjects.createLostItemsWithDifferentUsers();

        when(lostItemRepository.findByUser_Email(anyString())).thenReturn(lostItems);
        when(mappingService.mapLostItem(any())).thenReturn(lostItem1);

        LostItemResponse response = lostItemService.updateUserLostItem(request);

        Assertions.assertEquals("Item to update does not belongs to the user", response.getMessage());

    }

    @Test
    @DisplayName("Delete user lost item - Success")
    public void deleteUserLostItemSuccess() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        when(lostItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenReturn(Optional.of(SampleTestObjects.createLostItem(user, coordinates)));

        LostItemResponse response = lostItemService.deleteUserLostItem(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Deleted successfully", response.getMessage());

        verify(lostItemRepository, times(1)).deleteById(request.getId());
    }


    @Test
    @DisplayName("Delete user lost item - Item not found")
    public void deleteUserLostItemNotFound() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        LostItemResponse response = lostItemService.deleteUserLostItem(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found", response.getMessage());

        verify(lostItemRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Delete user lost item - EntityNotFoundException occurs during isUserProperty")
    public void deleteUserLostItemEntityNotFoundExceptionInIsUserProperty() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        when(lostItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        LostItemResponse response = lostItemService.deleteUserLostItem(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found", response.getMessage());

        verify(lostItemRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Check delete request - Success")
    public void checkDeleteRequestSuccess() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        Assertions.assertDoesNotThrow(() -> lostItemService.checkDeleteRequest(request));
    }

    @Test
    @DisplayName("Check delete request - EntityNotFoundException")
    public void checkDeleteRequestEntityNotFoundException() {
        LostItemRequest request = new LostItemRequest(); // Incomplete request

        Assertions.assertThrows(EntityNotFoundException.class, () -> lostItemService.checkDeleteRequest(request));
    }

    @Test
    @DisplayName("Is user property - Success")
    public void isUserPropertySuccess() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        when(lostItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenReturn(Optional.of(SampleTestObjects.createLostItem(user, coordinates)));

        Assertions.assertTrue(lostItemService.isUserProperty(request));
    }

    @Test
    @DisplayName("Is user property - EntityNotFoundException")
    public void isUserPropertyEntityNotFoundException() {
        LostItemRequest request = SampleTestObjects.createLostItemRequest();

        when(lostItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> lostItemService.isUserProperty(request));
    }
}