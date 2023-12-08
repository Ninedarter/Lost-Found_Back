package com.example.LostAndFoundApp.demo;

import com.example.LostAndFoundApp.item.found.FoundItem;
import com.example.LostAndFoundApp.item.found.FoundItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class DemoControllerTest {

    @Mock
    private FoundItemService foundItemService;

    @InjectMocks
    private DemoController demoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSayHello() {
        ResponseEntity<String> responseEntity = demoController.sayHello();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals("All good", responseEntity.getBody());
    }

    @Test
    void testGetAll() {

        List<FoundItem> mockItems = Arrays.asList();

        when(foundItemService.getAll()).thenReturn(mockItems);

        ResponseEntity<List<FoundItem>> responseEntity = demoController.getAll();

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(mockItems, responseEntity.getBody());

        verify(foundItemService, times(1)).getAll();
    }

    @Test
    void testGetById() {

        Long itemId = 1L;
        FoundItem mockItem = new FoundItem();

        when(foundItemService.testFindById(itemId)).thenReturn(mockItem);

        ResponseEntity<FoundItem> responseEntity = demoController.getById(itemId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());

        verify(foundItemService, times(1)).testFindById(itemId);
    }
}
