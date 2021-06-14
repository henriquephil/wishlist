package com.henriquephil.wishlist.controller;

import com.henriquephil.wishlist.controller.dto.WishDto;
import com.henriquephil.wishlist.controller.dto.WishlistContainsResponse;
import com.henriquephil.wishlist.domain.Wish;
import com.henriquephil.wishlist.service.WishlistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("wishlist")
@Api("Wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{productId}")
    @ApiOperation("Adiciona um produto à wishlist do usuário. Se o produto já estiver na lista, atualiza a data de modificação")
    public ResponseEntity<Void> addOrUpdate(@PathVariable String productId, @ApiIgnore Principal principal) {
        Wish wish = wishlistService.addOrUpdate(principal.getName(), productId);
        return ResponseEntity.created(URI.create(String.format("/wishlist/%s", wish.getProductId()))).build();
    }

    @DeleteMapping("/{productId}")
    @ApiOperation("Remove um produto da wishlist do usuário")
    public void remove(@PathVariable String productId, @ApiIgnore Principal principal) {
        wishlistService.remove(principal.getName(), productId);
    }

    @GetMapping
    @ApiOperation("Consulta todos os produtos da wishlist do usuário")
    public List<WishDto> getAll(@ApiIgnore Principal principal) {
        return wishlistService.getAll(principal.getName()).stream()
                .map(wish -> new WishDto(wish.getProductId(), wish.getDate()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{productId}")
    @ApiOperation("Consulta se a wishlist contém o produto")
    public WishlistContainsResponse contains(@PathVariable String productId, @ApiIgnore Principal principal) {
        return wishlistService.findByProduct(principal.getName(), productId)
                .map(wish -> new WishlistContainsResponse(true, wish.getDate()))
                .orElse(new WishlistContainsResponse(false, null));
    }
}
