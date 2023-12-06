package com.example.LostAndFoundApp.item.found;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FoundItemAdminControllerTest {

    @Mock
    private FoundItemService foundItemService;

    @InjectMocks
    private FoundItemAdminController foundItemAdminController;
}
