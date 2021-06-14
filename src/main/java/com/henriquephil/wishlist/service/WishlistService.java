package com.henriquephil.wishlist.service;

import com.henriquephil.wishlist.domain.Wish;
import com.henriquephil.wishlist.repository.WishRepository;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {
    private final WishRepository wishRepository;
    private final Long maxWishlistSize;

    public WishlistService(WishRepository wishRepository, @Value("${app.max-wishlist-size}") Long maxWishlistSize) {
        this.wishRepository = wishRepository;
        this.maxWishlistSize = maxWishlistSize;
    }

    public List<Wish> getAll(String user) {
        return wishRepository.findAllByUsername(user);
    }

    public Optional<Wish> findByProduct(String user, String productId) {
        return wishRepository.findOneByUsernameAndProductId(user, productId);
    }

    public Wish addOrUpdate(String user, String productId) {
        Optional<Wish> optionalWish = findByProduct(user, productId);
        if (optionalWish.isPresent()) {
            Metrics.counter("wishlist.update").increment();
            Wish wish = optionalWish.get();
            wish.update();
            return wishRepository.save(wish);
        } else {
            Metrics.counter("wishlist.insert").increment();
            Assert.isTrue(wishRepository.countByUsername(user) < maxWishlistSize, String.format("Limite máximo de %s itens atingido", maxWishlistSize));
            return wishRepository.insert(new Wish(user, productId));
        }
    }

    public void remove(String user, String productId) {
        Metrics.counter("wishlist.remove").increment();
        wishRepository.deleteByUsernameAndProductId(user, productId);
    }
}
