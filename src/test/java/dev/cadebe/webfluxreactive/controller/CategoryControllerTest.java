package dev.cadebe.webfluxreactive.controller;

import dev.cadebe.webfluxreactive.domain.Category;
import dev.cadebe.webfluxreactive.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test CategoryController")
class CategoryControllerTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController categoryController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    @DisplayName("Test get all categories")
    void getAllCategories() {
        given(categoryRepository.findAll()).willReturn(Flux.just(Category.builder().description("cat1").build(),
                Category.builder().description("cat2").build()));

        webTestClient.get()
                .uri(getBaseURL())
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    @DisplayName("Test get category by ID")
    void getCategoryById() {
        given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("cat1").build()));

        webTestClient.get()
                .uri(getBaseURL() + "/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    @DisplayName("Test create new category")
    void createNewCategory() {
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> vendorToSaveMono = Mono.just(Category.builder()
                .id("111")
                .description("First Name")
                .build());

        webTestClient.post()
                .uri(getBaseURL())
                .body(vendorToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("Test update existing category")
    void updateExistingCategory() {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().id("11").build()));

        Mono<Category> vendorMonoToUpdate = Mono.just(Category.builder().build());

        webTestClient.put()
                .uri(getBaseURL() + "/111")
                .body(vendorMonoToUpdate, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Test patch existing category")
    void patchExistingCategory() {
        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().id("111").description("cat1").build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().id("111").description("cat1").build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("New Description").build());

        webTestClient.patch()
                .uri(getBaseURL() + "/111")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    private String getBaseURL() {
        return CategoryController.CATEGORIES_BASE_URL + "/";
    }
}
