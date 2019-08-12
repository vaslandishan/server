package ir.server.vaslandishan.repository;

import ir.server.vaslandishan.models.Product;
import ir.server.vaslandishan.payload.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface StoredProcedureRepository extends CrudRepository<Product, Long>, StoredProcedureRepositoryBase {

    Page<Product> getAllProducts();
    Page<TransactionResponse> getAllTransactionByProductCode(String productCode,
                                                             Date fromDate,
                                                             Date toDate,
                                                             int page, int size);
}
