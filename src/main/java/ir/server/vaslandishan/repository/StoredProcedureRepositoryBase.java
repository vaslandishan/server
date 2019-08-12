package ir.server.vaslandishan.repository;

import ir.server.vaslandishan.models.Product;
import ir.server.vaslandishan.payload.TransactionResponse;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;

public interface StoredProcedureRepositoryBase {

    Page<Product> getAllProducts();
    Page<TransactionResponse> getAllTransactionByProductCode(String productCode,
                                                             Date fromDate,
                                                             Date toDate,
                                                             int page, int size);

}
