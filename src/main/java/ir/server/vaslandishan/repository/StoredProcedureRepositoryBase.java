package ir.server.vaslandishan.repository;

import ir.server.vaslandishan.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoredProcedureRepositoryBase {

    Page<Product> getAllProducts();

}
