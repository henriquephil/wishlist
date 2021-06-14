package com.henriquephil.wishlist.controller.dto;

import java.time.LocalDateTime;

public record WishDto(String productId, LocalDateTime date) {
}
