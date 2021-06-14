package com.henriquephil.wishlist.repository;

import com.henriquephil.wishlist.domain.Wish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishRepository extends MongoRepository<Wish, String> {
    List<Wish> findAllByUsername(String username);
    Long countByUsername(String username);
    Optional<Wish> findOneByUsernameAndProductId(String username, String productId);
    Optional<Wish> deleteByUsernameAndProductId(String username, String productId);
}
