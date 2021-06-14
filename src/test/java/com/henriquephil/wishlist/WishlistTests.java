package com.henriquephil.wishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriquephil.wishlist.repository.WishRepository;
import com.henriquephil.wishlist.util.RandomHexIdGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@WithMockUser(username = "mock", password = "mock", roles = "USER")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Import(EmbeddedMongoAutoConfiguration.class)
class WishlistTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WishRepository wishRepository;

    private final String[] productIds = { RandomHexIdGenerator.generate(12), RandomHexIdGenerator.generate(12) };

    @Test
    @Order(1)
    public void addFirstProduct_shouldSucceed() throws Exception {
        mockMvc.perform(post("/wishlist/{productId}", productIds[0])
                /**/.with(SecurityMockMvcRequestPostProcessors.httpBasic("mock", "mock")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(2)
    public void addSecondProduct_shouldSucceed() throws Exception {
        mockMvc.perform(post("/wishlist/{productId}", productIds[1])
                /**/.with(SecurityMockMvcRequestPostProcessors.httpBasic("mock", "mock")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(3)
    public void addDuplicateProduct_shouldUpdate() throws Exception {
        mockMvc.perform(post("/wishlist/{productId}", productIds[0])
                /**/.with(SecurityMockMvcRequestPostProcessors.httpBasic("mock", "mock")))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(4)
    public void getAll_shouldReturnTwoRecords() throws Exception {
        String expectedReturn = objectMapper.createArrayNode()
                .add(objectMapper.createObjectNode().put("productId", productIds[0]))
                .add(objectMapper.createObjectNode().put("productId", productIds[1]))
                .toString();
        mockMvc.perform(get("/wishlist")
                /**/.with(SecurityMockMvcRequestPostProcessors.httpBasic("mock", "mock")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedReturn));
    }

    @Test
    @Order(5)
    public void getBeforeRemove_shouldReturnContainsTrue() throws Exception {
        String expectedReturn = objectMapper.createObjectNode().put("contains", true).toString();
        mockMvc.perform(get("/wishlist/{productId}", productIds[1])
                /**/.with(SecurityMockMvcRequestPostProcessors.httpBasic("mock", "mock")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedReturn));
    }

    @Test
    @Order(6)
    public void delete_shouldSucceed() throws Exception {
        mockMvc.perform(delete("/wishlist/{productId}", productIds[1])
                /**/.with(SecurityMockMvcRequestPostProcessors.httpBasic("mock", "mock")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(7)
    public void getAfterRemove_shouldReturnContainsFalse() throws Exception {
        String expectedReturn = objectMapper.createObjectNode().put("contains", false).toString();
        mockMvc.perform(get("/wishlist/{productId}", productIds[1])
                /**/.with(SecurityMockMvcRequestPostProcessors.httpBasic("mock", "mock")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedReturn));
    }

    @AfterAll
    public void clean() {
        for (String productId : productIds) {
            wishRepository.deleteByUsernameAndProductId("mock", productId);
        }
    }

}
