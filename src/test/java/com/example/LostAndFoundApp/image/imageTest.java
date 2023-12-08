package com.example.LostAndFoundApp.image;

import com.example.LostAndFoundApp.item.image.Image;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class imageTest {

    @Test
    void testNoArgsConstructor() {
        Image image = new Image();
        Assertions.assertThat(image).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        Image image = new Image(1L, "https://example.com/image.jpg");
        Assertions.assertThat(image.getId()).isEqualTo(1L);
        Assertions.assertThat(image.getUrl()).isEqualTo("https://example.com/image.jpg");
    }

    @Test
    void testBuilder() {
        Image image = Image.builder()
                .id(1L)
                .url("https://example.com/image.jpg")
                .build();

        Assertions.assertThat(image.getId()).isEqualTo(1L);
        Assertions.assertThat(image.getUrl()).isEqualTo("https://example.com/image.jpg");
    }

    @Test
    void testSetterGetter() {
        Image image = new Image();

        image.setId(1L);
        image.setUrl("https://example.com/image.jpg");

        Assertions.assertThat(image.getId()).isEqualTo(1L);
        Assertions.assertThat(image.getUrl()).isEqualTo("https://example.com/image.jpg");
    }
}
