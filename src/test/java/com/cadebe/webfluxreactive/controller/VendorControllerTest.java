package com.cadebe.webfluxreactive.controller;

import com.cadebe.webfluxreactive.domain.Category;
import com.cadebe.webfluxreactive.domain.Vendor;
import com.cadebe.webfluxreactive.repository.VendorRepository;
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
@DisplayName("Test VendorController")
class VendorControllerTest {

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorController vendorController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    @DisplayName("Test get all vendors")
    void getAllVendors() {
        given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().firstName("vendor 1").build(),
                Vendor.builder().firstName("vendor 2").build()));

        webTestClient.get()
                .uri(getBaseURL())
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    @DisplayName("Test get vendor by ID")
    void getVendorById() {
        given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("vendor 1").build()));

        webTestClient.get()
                .uri(getBaseURL() + "/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    @DisplayName("Test create new vendor")
    void createVendor() {
        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("First Name")
                .lastName("Last Name").build());

        webTestClient.post()
                .uri(getBaseURL())
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("Test update existing vendor")
    void updateVendor() {
        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().id("11").build()));

        Mono<Vendor> vendorMonoToUpdate = Mono.just(Vendor.builder().build());

        webTestClient.put()
                .uri(getBaseURL() + "/111")
                .body(vendorMonoToUpdate, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("Test patch existing vendor")
    void patchVendor() {
        given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().id("111").firstName("cat1").build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().id("111").firstName("cat1").build()));

        Mono<Vendor> catToUpdateMono = Mono.just(Vendor.builder().firstName("New Description").build());

        webTestClient.patch()
                .uri(getBaseURL() + "/111")
                .body(catToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    private String getBaseURL() {
        return VendorController.VENDORS_BASE_URL + "/";
    }
}