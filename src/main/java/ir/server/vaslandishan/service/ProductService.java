package ir.server.vaslandishan.service;

import ir.server.vaslandishan.repository.StoredProcedureRepository;
import org.springframework.stereotype.Service;
import ir.server.vaslandishan.exception.BadRequestException;
import ir.server.vaslandishan.exception.ResourceNotFoundException;
import ir.server.vaslandishan.models.*;
import ir.server.vaslandishan.payload.PagedResponse;
import ir.server.vaslandishan.payload.ProductRequest;
import ir.server.vaslandishan.payload.ProductResponse;
import ir.server.vaslandishan.repository.ProductRepository;
import ir.server.vaslandishan.repository.UserRepository;
import ir.server.vaslandishan.security.UserPrincipal;
import ir.server.vaslandishan.util.AppConstants;
import ir.server.vaslandishan.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
//import org.apache.logging.log4j.Logger;

@Service
public class ProductService {

    //static Logger log = Logger.getLogger(ProductService.class.getName());

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoredProcedureRepository storedProcedureRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Product createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setTitle(productRequest.getTitle());
        product.setDescription(productRequest.getDescription());
        logger.info(productRequest.getTitle());

        return productRepository.save(product);
    }

    public PagedResponse<ProductResponse> getAllProducts(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrdieve Products
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Product> products = productRepository.findAll(pageable);

        if(products.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), products.getNumber(),
                    products.getSize(), products.getTotalElements(), products.getTotalPages(), products.isLast());
        }

        // Map Products to ProductResponses containing vote counts and product creator details
        List<Long> productIds = products.map(Product::getId).getContent();
        Map<Long, User> creatorMap = getProductCreatorMap(products.getContent());

        List<ProductResponse> productResponses = products.map(product -> {
            return ModelMapper.mapProductToProductResponse(product,
                    creatorMap.get(product.getCreatedBy()));
        }).getContent();

        return new PagedResponse<>(productResponses, products.getNumber(),
                products.getSize(), products.getTotalElements(), products.getTotalPages(), products.isLast());
    }

    public ProductResponse getProductById(Long productId, UserPrincipal currentUser) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId));

        // Retrieve product creator details
        User creator = userRepository.findById(product.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", product.getCreatedBy()));


        return ModelMapper.mapProductToProductResponse(product, creator);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    Map<Long, User> getProductCreatorMap(List<Product> products) {
        logger.info("S01");
        // Get Product Creator details of the given list of products
        List<Long> creatorIds = products.stream()
                .map(Product::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());
        logger.info("S02");
        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        logger.info("S03");
        return creatorMap;
    }

    public PagedResponse<ProductResponse> getAllProductsBySp(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);


        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
         Page<Product> products = (Page<Product>)storedProcedureRepository.getAllProducts();
         Page<Product> products2 = productRepository.findAll(pageable);

        products2 = products;

         if(products.equals(products2))
         {
             logger.info("is equeal ?????????????????????????????????????????");
         }

        logger.info("getAllProductsBySp"+products2.getTotalPages());

        List<Product> p = products2.getContent();
        logger.info("l1:"+p.size());

        //List<Long> productIds = products.map(Product::getId).getContent();
        Map<Long, User> creatorMap = getProductCreatorMap(products2.getContent());

        logger.info("l2:"+products2.getTotalPages());
        List<ProductResponse> productResponses = products2.map(product -> {
            return ModelMapper.mapProductToProductResponse(product,
                    creatorMap.get(product.getCreatedBy()));
        }).getContent();


        return new PagedResponse<>(productResponses, products2.getNumber(),
                products2.getSize(), products2.getTotalElements(), products2.getTotalPages(), products2.isLast());
    }
}
