package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.lost.LostItem;
import com.example.LostAndFoundApp.item.lost.SampleTestObjects;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequestAdd;
import com.example.LostAndFoundApp.item.lost.response.LostItemResponse;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LostItemControllerTest {

    @Mock
    private LostItemService lostItemService;

    @InjectMocks
    private LostItemController lostItemController;

    @Test
    void getAll_ReturnsOk() {
        LostItem lostItem1 = LostItem.builder()
                .id(1L)
                .category(ItemCategory.PHONE)
                .title("Item 1")
                .dateLost(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        LostItem foundItem2 = LostItem.builder()
                .id(2L)
                .category(ItemCategory.KEYS)
                .title("Item 2")
                .dateLost(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        List<LostItem> lostItems = Arrays.asList(lostItem1, foundItem2);

        when(lostItemService.getAll()).thenReturn(lostItems);

        ResponseEntity<List<LostItem>> responseEntity = lostItemController.getAll();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(lostItems, responseEntity.getBody());
    }

    @Test
    void add_ValidRequest_ReturnsCreated() {

        LostItemRequest request = com.example.LostAndFoundApp.item.lost.SampleTestObjects.createLostItemRequest();
        LostItemResponse successResponse = new LostItemResponse(true, "Created successfully");
        when(lostItemService.add(request)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.add(request);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void add_InvalidRequest_ReturnsBadRequest() {

        LostItemRequest request = new LostItemRequest();
        LostItemResponse failureResponse = new LostItemResponse(false, "Invalid request");
        when(lostItemService.add(request)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.add(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void getById_ExistingItemId_ReturnsOk() {

        Long itemId = 1L;
        LostItemResponse successResponse = new LostItemResponse(true);
        when(lostItemService.getById(itemId)).thenReturn(successResponse);


        ResponseEntity<LostItemResponse> responseEntity = lostItemController.getById(itemId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void getById_NonExistingItemId_ReturnsNotFound() {

        Long itemId = 2L;
        LostItemResponse failureResponse = new LostItemResponse(false, "Item not found");
        when(lostItemService.getById(itemId)).thenReturn(failureResponse);


        ResponseEntity<LostItemResponse> responseEntity = lostItemController.getById(itemId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void update_ValidRequest_ReturnsOk() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItemResponse successResponse = new LostItemResponse(true, "Updated successfully");
        when(lostItemService.update(request)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.update(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void update_InvalidRequest_ReturnsBadRequest() {

        LostItemRequest request = new LostItemRequest();
        LostItemResponse failureResponse = new LostItemResponse(false, "Invalid request");
        when(lostItemService.update(request)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.update(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void delete_ExistingItemId_ReturnsOk() {

        Long itemId = 1L;
        LostItemResponse successResponse = new LostItemResponse(true, "Deleted successfully");
        when(lostItemService.delete(itemId)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.delete(itemId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_NonExistingItemId_ReturnsBadRequest() {

        Long itemId = 2L;
        LostItemResponse failureResponse = new LostItemResponse(false, "Item not found");
        when(lostItemService.delete(itemId)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.delete(itemId);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void getByUserId_ValidRequest_ReturnsLostItems() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItem lostItem1 = LostItem.builder()
                .id(1L)
                .category(ItemCategory.PHONE)
                .title("Item 1")
                .dateLost(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        LostItem lostItem2 = LostItem.builder()
                .id(2L)
                .category(ItemCategory.KEYS)
                .title("Item 2")
                .dateLost(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        List<LostItem> lostItems = Arrays.asList(lostItem1, lostItem2);

        when(lostItemService.getAllUserLostItems(request.getEmail())).thenReturn(lostItems);

        ResponseEntity<List<LostItem>> responseEntity = lostItemController.getByUserId(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(lostItems, responseEntity.getBody());
    }

    @Test
    void updateUserLostItem_ValidRequest_ReturnsOk() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItemResponse successResponse = new LostItemResponse(true, "Updated successfully");
        when(lostItemService.updateUserLostItem(request)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.updateUserLostItem(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateUserLostItem_InvalidRequest_ReturnsBadRequest() {

        LostItemRequest request = new LostItemRequest();
        LostItemResponse failureResponse = new LostItemResponse(false, "Invalid request");
        when(lostItemService.updateUserLostItem(request)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.updateUserLostItem(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void deleteUserLostItem_ValidRequest_ReturnsOk() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItemResponse successResponse = new LostItemResponse(true, "Deleted successfully");
        when(lostItemService.deleteUserLostItem(request)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.deleteUserLostItem(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteUserLostItem_InvalidRequest_ReturnsBadRequest() {

        LostItemRequest request = new LostItemRequest();
        LostItemResponse failureResponse = new LostItemResponse(false, "Invalid request");
        when(lostItemService.deleteUserLostItem(request)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.deleteUserLostItem(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void addNew_ValidRequest_ReturnsCreated() {
        LostItemRequestAdd request = SampleTestObjects.createLostItemRequestAdd();
        Principal principal = Mockito.mock(Principal.class);
        LostItemResponse successResponse = new LostItemResponse(true, "Created successfully");

        Mockito.when(lostItemService.addNew(request, principal)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.addNew(request, principal);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void addNew_InvalidRequest_ReturnsBadRequest() {
        LostItemRequestAdd request = new LostItemRequestAdd();
        Principal principal = Mockito.mock(Principal.class);
        LostItemResponse failureResponse = new LostItemResponse(false, "Invalid request");

        Mockito.when(lostItemService.addNew(request, principal)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemController.addNew(request, principal);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }
}
