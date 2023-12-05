package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.coordinates.CoordinatesRepository;
import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.item.lost.response.LostItemResponse;
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
    @DisplayName("Add Two Found Items and Verify Retrieval")
    public void getAll() {

        List<LostItem> lostItems = Arrays.asList(lostItem1, lostItem2);

        when(lostItemRepository.findAll()).thenReturn(lostItems);

        List<LostItem> result = lostItemService.getAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(lostItems, result);
    }

    @Test
    @DisplayName("Add new Found Item - Mapping and Save Verification")
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
    @DisplayName("Get Found Item by ID - Item Found")
    public void findExistingItemById() {
        long existingLostItemID = lostItem1.getId();
        Optional<LostItem> optionalLostItem = Optional.of(lostItem1);

        when(lostItemRepository.findById(existingLostItemID)).thenReturn(optionalLostItem);

        LostItemResponse response = lostItemService.getById(existingLostItemID);

        Assertions.assertEquals("FOUND BY ID 1", response.getMessage());
        Assertions.assertEquals(lostItem1, response.getItem());
    }

    @Test
    @DisplayName("Get Found Item by ID - Item Not Found")
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
}