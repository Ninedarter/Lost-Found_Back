package com.example.LostAndFoundApp.item.found;

import com.example.LostAndFoundApp.item.ItemCategory;
import com.example.LostAndFoundApp.item.coordinates.Coordinates;
import com.example.LostAndFoundApp.item.found.request.FoundItemRequest;
import com.example.LostAndFoundApp.item.found.request.GetByCategoryRequest;
import com.example.LostAndFoundApp.item.found.response.FoundItemResponse;
import com.example.LostAndFoundApp.user.User;
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
public class FoundItemControllerTest {

    @Mock
    private FoundItemService foundItemService;

    @InjectMocks
    private FoundItemController foundItemController;

    @Test
    void getAll_ReturnsOk() {
        FoundItem foundItem1 = FoundItem.builder()
                .id(1L)
                .category(ItemCategory.PHONE)
                .title("Item 1")
                .dateFound(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        FoundItem foundItem2 = FoundItem.builder()
                .id(2L)
                .category(ItemCategory.KEYS)
                .title("Item 2")
                .dateFound(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        List<FoundItem> foundItems = Arrays.asList(foundItem1, foundItem2);

        when(foundItemService.getAll()).thenReturn(foundItems);

        ResponseEntity<List<FoundItem>> responseEntity = foundItemController.getAll();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(foundItems, responseEntity.getBody());
    }

    @Test
    void add_ValidRequest_ReturnsCreated() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItemResponse successResponse = new FoundItemResponse(true, "Created successfully");
        when(foundItemService.add(request)).thenReturn(successResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.add(request);

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void add_InvalidRequest_ReturnsBadRequest() {

        FoundItemRequest request = new FoundItemRequest();
        FoundItemResponse failureResponse = new FoundItemResponse(false, "Invalid request");
        when(foundItemService.add(request)).thenReturn(failureResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.add(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void getById_ExistingItemId_ReturnsOk() {

        Long itemId = 1L;
        FoundItemResponse successResponse = new FoundItemResponse(true, new FoundItem());
        when(foundItemService.getById(itemId)).thenReturn(successResponse);


        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.getById(itemId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void getById_NonExistingItemId_ReturnsNotFound() {

        Long itemId = 2L;
        FoundItemResponse failureResponse = new FoundItemResponse(false, "Item not found");
        when(foundItemService.getById(itemId)).thenReturn(failureResponse);


        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.getById(itemId);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void update_ValidRequest_ReturnsOk() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItemResponse successResponse = new FoundItemResponse(true, "Updated successfully");
        when(foundItemService.update(request)).thenReturn(successResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.update(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void update_InvalidRequest_ReturnsBadRequest() {

        FoundItemRequest request = new FoundItemRequest();
        FoundItemResponse failureResponse = new FoundItemResponse(false, "Invalid request");
        when(foundItemService.update(request)).thenReturn(failureResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.update(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void delete_ExistingItemId_ReturnsOk() {

        Long itemId = 1L;
        FoundItemResponse successResponse = new FoundItemResponse(true, "Deleted successfully");
        when(foundItemService.delete(itemId)).thenReturn(successResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.delete(itemId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void delete_NonExistingItemId_ReturnsBadRequest() {

        Long itemId = 2L;
        FoundItemResponse failureResponse = new FoundItemResponse(false, "Item not found");
        when(foundItemService.delete(itemId)).thenReturn(failureResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.delete(itemId);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void getByCoordinates_ExistingCoordinates_ReturnsOk() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItemResponse successResponse = new FoundItemResponse(true, SampleTestObjects.createFoundItem(new User(), new Coordinates()));
        when(foundItemService.getByCoordinates(request)).thenReturn(successResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.getByCoordinates(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(successResponse, responseEntity.getBody());
    }

    @Test
    void getByCoordinates_NonExistingCoordinates_ReturnsNotFound() {

        FoundItemRequest request = new FoundItemRequest();
        FoundItemResponse failureResponse = new FoundItemResponse(false, "Item not found by coordinates");
        when(foundItemService.getByCoordinates(request)).thenReturn(failureResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.getByCoordinates(request);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void getByUserId_ValidRequest_ReturnsFoundItems() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItem foundItem1 = FoundItem.builder()
                .id(1L)
                .category(ItemCategory.PHONE)
                .title("Item 1")
                .dateFound(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        FoundItem foundItem2 = FoundItem.builder()
                .id(2L)
                .category(ItemCategory.KEYS)
                .title("Item 2")
                .dateFound(LocalDate.now())
                .creationTime(LocalDateTime.now())
                .build();

        List<FoundItem> foundItems = Arrays.asList(foundItem1, foundItem2);

        when(foundItemService.getAllUserFoundItems(request.getEmail())).thenReturn(foundItems);

        List<FoundItem> result = foundItemController.getByUserId(request);

        Assertions.assertEquals(foundItems, result);
    }

    @Test
    void updateUserFoundItem_ValidRequest_ReturnsOk() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItemResponse successResponse = new FoundItemResponse(true, "Updated successfully");
        when(foundItemService.updateUserFoundItem(request)).thenReturn(successResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.updateUserFoundItem(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void updateUserFoundItem_InvalidRequest_ReturnsBadRequest() {

        FoundItemRequest request = new FoundItemRequest();
        FoundItemResponse failureResponse = new FoundItemResponse(false, "Invalid request");
        when(foundItemService.updateUserFoundItem(request)).thenReturn(failureResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.updateUserFoundItem(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }

    @Test
    void deleteUserFoundItem_ValidRequest_ReturnsOk() {

        FoundItemRequest request = SampleTestObjects.createFoundItemRequest();
        FoundItemResponse successResponse = new FoundItemResponse(true, "Deleted successfully");
        when(foundItemService.deleteUserFoundItem(request)).thenReturn(successResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.deleteUserFoundItem(request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteUserFoundItem_InvalidRequest_ReturnsBadRequest() {

        FoundItemRequest request = new FoundItemRequest();
        FoundItemResponse failureResponse = new FoundItemResponse(false, "Invalid request");
        when(foundItemService.deleteUserFoundItem(request)).thenReturn(failureResponse);

        ResponseEntity<FoundItemResponse> responseEntity = foundItemController.deleteUserFoundItem(request);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertEquals(failureResponse, responseEntity.getBody());
    }


}
