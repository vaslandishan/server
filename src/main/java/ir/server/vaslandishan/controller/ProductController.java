package ir.server.vaslandishan.controller;

import ir.server.vaslandishan.models.Product;
import ir.server.vaslandishan.payload.ApiResponse;
import ir.server.vaslandishan.payload.ProductRequest;
import ir.server.vaslandishan.payload.ProductResponse;
import ir.server.vaslandishan.payload.PagedResponse;
import ir.server.vaslandishan.repository.ProductRepository;
import ir.server.vaslandishan.repository.UserRepository;
import ir.server.vaslandishan.security.CurrentUser;
import ir.server.vaslandishan.security.UserPrincipal;
import ir.server.vaslandishan.service.ProductService;
import ir.server.vaslandishan.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ir.server.vaslandishan.repository.StoredProcedureRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private StoredProcedureRepository storedProcedureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{productId}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "product Created Successfully"));
    }

    @GetMapping("/getall")
    public PagedResponse<ProductResponse> getProduct(@CurrentUser UserPrincipal currentUser,
                                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return productService.getAllProducts(currentUser, page, size);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long productId) {
        return productService.getProductById(productId, currentUser);
    }

    @GetMapping("/getallBySp")
    public PagedResponse<ProductResponse> getProductBySp(@CurrentUser UserPrincipal currentUser) {
        int page = 1;
        int size  = 10;
        return productService.getAllProductsBySp(currentUser, page, size);
        //return products;

/*        List<Product> target = new ArrayList<>();
        products.forEach(target::add);
        return new ResponseEntity<>(target, HttpStatus.OK);*/
        //return productService.getProductById(, currentUser);
    }

}