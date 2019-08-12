package ir.server.vaslandishan.controller;
import ir.server.vaslandishan.models.Product;
import ir.server.vaslandishan.payload.*;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private StoredProcedureRepository storedProcedureRepository;

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/getAllTransactionByProductCode")
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<TransactionResponse> getAllTransactionByProductCode(@CurrentUser UserPrincipal currentUser,
                                                                             @RequestParam String productCode,
                                                                             @RequestParam Date fromDate,
                                                                             @RequestParam Date toDate,
                                                                             @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                             @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
/*        logger.info("param productCode"+productCode);
        logger.info("param fromDate"+fromDate);
        logger.info("param toDate"+toDate);*/

        return productService.getAllTransactionByProductCode(currentUser,productCode, fromDate, toDate,  page, size);
    }
}
