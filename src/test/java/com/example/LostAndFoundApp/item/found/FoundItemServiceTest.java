package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.request.GetByCategoryRequest;
import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import com.example.LostAndFoundApp.item.image.ImageRepository;
import com.example.LostAndFoundApp.mapping.MappingService;
import com.example.LostAndFoundApp.user.Gender;
import com.example.LostAndFoundApp.user.Role;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private FoundItemService foundItemService;


    private FoundItem foundItem1;
    private FoundItem foundItem2;
    private User user;
    private Coordinates coordinates;
    private FoundItem savedFoundItem1;
    private FoundItem savedFoundItem2;

    @BeforeEach
    void setUp() {

        user = SampleTestObjects.createUser();
        coordinates = SampleTestObjects.createCoordinates();
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

    @Test
    @DisplayName("Add new Found Item - Mapping and Save Verification")
    public void addNewFoundItem() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItem mappedItem = foundItem1;

        when(foundItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(mappingService.mapFoundItem(request)).thenReturn(mappedItem);

        FoundItemResponse response = foundItemService.add(request);

        Assertions.assertEquals("Created successfully", response.getMessage());
        verify(foundItemRepository).save(mappedItem);

    }

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
    @DisplayName("Set ID to 0 if null")
    public void setIdToZeroIfNull() {
        FoundItemRequest request = new FoundItemRequest();
        request.setId(null);

        foundItemService.add(request);

        Assertions.assertEquals(0L, request.getId());
    }

    @Test
    @DisplayName("Get Found Item by ID - Item Found")
    public void findExistingItemById() {
        foundItem1.setId(1L);
        long existingFoundItemID = foundItem1.getId();
        Optional<FoundItem> optionalFoundItem = Optional.of(foundItem1);

        when(foundItemRepository.findById(existingFoundItemID)).thenReturn(optionalFoundItem);

        FoundItemResponse response = foundItemService.getById(existingFoundItemID);

        Assertions.assertEquals("FOUND BY ID 1", response.getMessage());
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
        FoundItem mappedItem = SampleTestObjects.createFoundItem(user, coordinates);

        when(mappingService.mapFoundItem(request)).thenReturn(mappedItem);
        when(foundItemRepository.findById(request.getId())).thenReturn(Optional.of(foundItem1));

        FoundItemResponse response = foundItemService.update(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());

        // Verify that save was called with the correct argument
        ArgumentCaptor<FoundItem> foundItemCaptor = ArgumentCaptor.forClass(FoundItem.class);
        verify(foundItemRepository).save(foundItemCaptor.capture());

        // Assert the saved item
        FoundItem savedItem = foundItemCaptor.getValue();
        Assertions.assertEquals(mappedItem.getId(), savedItem.getId());

        // Verify that findById was called with the correct ID
        verify(foundItemRepository).findById(request.getId());

    }

    @Test
    @DisplayName("Update non existing item - verify exception")
    public void updateNonExistingItem() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        when(foundItemRepository.findById(request.getId())).thenReturn(Optional.empty());

        FoundItemResponse exception = foundItemService.update(request);

        Assertions.assertEquals("Item to update not found", exception.getMessage());
    }

    @Test
    @DisplayName("Get item by coordinates - item found")
    public void getItemByCoordinatesSuccess() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        // Assume that there is an item with the given coordinates
        when(foundItemRepository.findByCoordinatesId(anyLong())).thenReturn(Optional.of(foundItem1));

        FoundItemResponse response = foundItemService.getByCoordinates(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
        Assertions.assertEquals(foundItem1, response.getItem());
    }

    @Test
    @DisplayName("Get item by coordinates - item not found")
    public void getItemByCoordinatesNotFound() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        // Assume that there is no item with the given coordinates
        when(foundItemRepository.findByCoordinatesId(anyLong())).thenReturn(Optional.empty());

        FoundItemResponse response = foundItemService.getByCoordinates(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found by coordinates", response.getMessage());
        Assertions.assertNull(response.getItem());
    }

    @Test
    @DisplayName("Get ID by coordinates - ID found")
    public void getIdByCoordinatesSuccess() {
        Double latitude = 40.7128;
        Double longitude = -74.0060;

        when(coordinatesRepository.findIdByLatitudeAndLongitude(latitude, longitude)).thenReturn(Optional.of(1L));

        Long result = foundItemService.getIdByCoordinates(latitude, longitude);

        Assertions.assertEquals(1L, result);
    }

    @Test
    @DisplayName("Get ID by coordinates - ID not found")
    public void getIdByCoordinatesNotFound() {
        Double latitude = 40.7128;
        Double longitude = -74.0060;

        when(coordinatesRepository.findIdByLatitudeAndLongitude(latitude, longitude)).thenReturn(Optional.empty());

        Long result = foundItemService.getIdByCoordinates(latitude, longitude);

        Assertions.assertEquals(0L, result);
    }

    @Test
    @DisplayName("Get all user found items for existing user")
    public void getAllUserFoundItemsSuccess() {
        String email = user.getEmail();

        when(foundItemRepository.findByUser_Email(email)).thenReturn(Collections.emptyList());

        List<FoundItem> foundItems = foundItemService.getAllUserFoundItems(email);

        verify(foundItemRepository, times(1)).findByUser_Email(email);
        Assertions.assertNotNull(foundItems);
        Assertions.assertEquals(0, foundItems.size());
    }

    @Test
    @DisplayName("Update existing user found item")
    public void updateUserFoundItemSuccess() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        List<FoundItem> foundItems = Arrays.asList(SampleTestObjects.createFoundItem(user, coordinates));

        when(foundItemRepository.findByUser_Email(anyString())).thenReturn(foundItems);
        when(mappingService.mapFoundItem(any())).thenReturn(foundItem1);

        FoundItemResponse response = foundItemService.updateUserFoundItem(request);

        Assertions.assertEquals("Updated successfully", response.getMessage());

    }

    @Test
    @DisplayName("Update existing found item which does not belong to the user")
    public void updateUserFoundItemDoesNotBelong() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        foundItem1.setId(1L);
        foundItem2.setId(2L);

        List<FoundItem> foundItems = SampleTestObjects.createFoundItemsWithDifferentUsers();

        when(foundItemRepository.findByUser_Email(anyString())).thenReturn(foundItems);
        when(mappingService.mapFoundItem(any())).thenReturn(foundItem1);

        FoundItemResponse response = foundItemService.updateUserFoundItem(request);

        Assertions.assertEquals("Item to update does not belongs to the user", response.getMessage());

    }

    @Test
    @DisplayName("Delete user found item - Success")
    public void deleteUserFoundItemSuccess() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        when(foundItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenReturn(Optional.of(SampleTestObjects.createFoundItem(user, coordinates)));

        FoundItemResponse response = foundItemService.deleteUserFoundItem(request);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Deleted successfully", response.getMessage());

        verify(foundItemRepository, times(1)).deleteById(request.getId());
    }


    @Test
    @DisplayName("Delete user found item - Item not found")
    public void deleteUserFoundItemNotFound() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        FoundItemResponse response = foundItemService.deleteUserFoundItem(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found", response.getMessage());

        verify(foundItemRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Delete user found item - EntityNotFoundException occurs during isUserProperty")
    public void deleteUserFoundItemEntityNotFoundExceptionInIsUserProperty() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        when(foundItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        FoundItemResponse response = foundItemService.deleteUserFoundItem(request);

        Assertions.assertFalse(response.isSuccess());
        Assertions.assertEquals("Not found", response.getMessage());

        verify(foundItemRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Check delete request - Success")
    public void checkDeleteRequestSuccess() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        Assertions.assertDoesNotThrow(() -> foundItemService.checkDeleteRequest(request));
    }

    @Test
    @DisplayName("Check delete request - EntityNotFoundException")
    public void checkDeleteRequestEntityNotFoundException() {
        FoundItemRequest request = new FoundItemRequest(); // Incomplete request

        Assertions.assertThrows(EntityNotFoundException.class, () -> foundItemService.checkDeleteRequest(request));
    }

    @Test
    @DisplayName("Is user property - Success")
    public void isUserPropertySuccess() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        when(foundItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenReturn(Optional.of(SampleTestObjects.createFoundItem(user, coordinates)));

        Assertions.assertTrue(foundItemService.isUserProperty(request));
    }

    @Test
    @DisplayName("Is user property - EntityNotFoundException")
    public void isUserPropertyEntityNotFoundException() {
        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();

        when(foundItemRepository.findByIdAndUserEmail(request.getId(), request.getEmail()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> foundItemService.isUserProperty(request));
    }

    @Test
    @DisplayName("Get all found items by category - Success")
    public void getAllByCategorySuccess() {

        ItemCategory category = ItemCategory.KEYS;
        List<FoundItem> foundItems = Arrays.asList(foundItem1, foundItem2);

        when(foundItemRepository.findByCategory(category)).thenReturn(foundItems);

        FoundItemResponse response = foundItemService.getAllByCategory(new GetByCategoryRequest(category));

        Assertions.assertEquals(foundItems, response.getAllFound(), "Expected items to match");
        verify(foundItemRepository, times(1)).findByCategory(category);
    }
}


