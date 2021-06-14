package com.henriquephil.wishlist.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Getter
@Document
@CompoundIndex(name = "user_product", def = "{'username': 1, 'productId': 1}", unique = true)
public class Wish {
    @Id
    private String id;
    private String username; // usaria um melhor identificador num cenário real
    private String productId;
    private LocalDateTime date;

    public Wish(String username, String productId) {
        this.username = username;
        this.productId = productId;
        this.date = LocalDateTime.now();
    }

    public void update() {
        this.date = LocalDateTime.now();
    }
}
