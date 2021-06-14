package com.henriquephil.wishlist.controller.dto;

import java.time.LocalDateTime;

public record WishlistContainsResponse(boolean contains, LocalDateTime dateAdded) {
}
