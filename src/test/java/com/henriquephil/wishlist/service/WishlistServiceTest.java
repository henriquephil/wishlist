package com.henriquephil.wishlist.service;

import com.henriquephil.wishlist.repository.WishRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class WishlistServiceTest {
    @Mock
    private WishRepository wishRepository;
    @InjectMocks
    private WishlistService wishlistService;

    @Test
    public void cantAddMoreToFullWishlist() {
        when(wishRepository.countByUsername("user")).thenReturn(20L);

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                wishlistService.addOrUpdate("user", "60c3c118d64b414c3d7abd5e"));
    }
}