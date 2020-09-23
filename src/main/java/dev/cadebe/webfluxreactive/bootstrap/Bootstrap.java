package dev.cadebe.webfluxreactive.bootstrap;

import dev.cadebe.webfluxreactive.domain.Category;
import dev.cadebe.webfluxreactive.domain.Vendor;
import dev.cadebe.webfluxreactive.repository.CategoryRepository;
import dev.cadebe.webfluxreactive.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) {
            loadCategories();
            loadVendors();
        }
    }

    private void loadCategories() {
        categoryRepository.save(Category.builder()
                .description("Fruits").build()).block();

        categoryRepository.save(Category.builder()
                .description("Nuts").build()).block();

        categoryRepository.save(Category.builder()
                .description("Dried").build()).block();

        categoryRepository.save(Category.builder()
                .description("Fresh").build()).block();

        categoryRepository.save(Category.builder()
                .description("Exotic").build()).block();

        System.out.println("Loaded Categories: " + categoryRepository.count().block());
    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder()
                .firstName("Joe")
                .lastName("Buck").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Micheal")
                .lastName("Weston").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Jessie")
                .lastName("Newman").build()).block();

        System.out.println("Loaded Vendors: " + vendorRepository.count().block());
    }
}
