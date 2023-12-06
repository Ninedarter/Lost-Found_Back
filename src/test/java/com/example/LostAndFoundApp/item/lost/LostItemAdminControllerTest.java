package com.example.LostAndFoundApp.item.lost;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.lost.request.LostItemRequest;
import com.example.LostAndFoundApp.item.lost.response.LostItemResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LostItemAdminControllerTest {

    @Mock
    private LostItemService lostItemService;

    @InjectMocks
    private LostItemAdminController lostItemAdminController;

    @Test
    void getAll_ReturnsOk() {
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

        List<LostItem> foundItems = Arrays.asList(lostItem1, lostItem2);

        when(lostItemService.getAll()).thenReturn(foundItems);

        ResponseEntity<List<LostItem>> responseEntity = lostItemAdminController.getAll();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(foundItems, responseEntity.getBody());
    }

    @Test
    void add_ValidRequest_ReturnsCreated() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItemResponse successResponse = new LostItemResponse(true, "Created successfully");
        when(lostItemService.add(request)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.add(request);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void add_InvalidRequest_ReturnsBadRequest() {

        LostItemRequest request = new LostItemRequest();
        LostItemResponse failureResponse = new LostItemResponse(false, "Invalid request");
        when(lostItemService.add(request)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.add(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void getById_ExistingItemId_ReturnsOk() {

        Long itemId = 1L;
        LostItemResponse successResponse = new LostItemResponse(true);
        when(lostItemService.getById(itemId)).thenReturn(successResponse);


        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.getById(itemId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void getById_NonExistingItemId_ReturnsNotFound() {

        Long itemId = 2L;
        LostItemResponse failureResponse = new LostItemResponse(false, "Item not found");
        when(lostItemService.getById(itemId)).thenReturn(failureResponse);


        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.getById(itemId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void update_ValidRequest_ReturnsOk() {

        LostItemRequest request = SampleTestObjects.createLostItemRequest();
        LostItemResponse successResponse = new LostItemResponse(true, "Updated successfully");
        when(lostItemService.update(request)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.update(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void update_InvalidRequest_ReturnsBadRequest() {

        LostItemRequest request = new LostItemRequest();
        LostItemResponse failureResponse = new LostItemResponse(false, "Invalid request");
        when(lostItemService.update(request)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.update(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void delete_ExistingItemId_ReturnsOk() {

        Long itemId = 1L;
        LostItemResponse successResponse = new LostItemResponse(true, "Deleted successfully");
        when(lostItemService.delete(itemId)).thenReturn(successResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.delete(itemId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_NonExistingItemId_ReturnsBadRequest() {

        Long itemId = 2L;
        LostItemResponse failureResponse = new LostItemResponse(false, "Item not found");
        when(lostItemService.delete(itemId)).thenReturn(failureResponse);

        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.delete(itemId);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

//    @Test
//    void getByCoordinates_ExistingCoordinates_ReturnsOk() {
//
//        LostItemRequest request = SampleTestObjects.createLostItemRequest();
//        LostItemResponse successResponse = new LostItemResponse(true);
//        when(lostItemService.getByCoordinates(request)).thenReturn(successResponse);
//
//        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.getByCoordinates(request);
//
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assertions.assertEquals(successResponse, responseEntity.getBody());
//    }
//
//    @Test
//    void getByCoordinates_NonExistingCoordinates_ReturnsNotFound() {
//
//        LostItemRequest request = new LostItemRequest();
//        LostItemResponse failureResponse = new LostItemResponse(false, "Item not found by coordinates");
//        when(lostItemService.getByCoordinates(request)).thenReturn(failureResponse);
//
//        ResponseEntity<LostItemResponse> responseEntity = lostItemAdminController.getByCoordinates(request);
//
//        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        Assertions.assertEquals(failureResponse, responseEntity.getBody());
//    }
}
