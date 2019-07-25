package ir.server.vaslandishan.repository;

import ir.server.vaslandishan.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface StoredProcedureRepository extends CrudRepository<Product, Long>, StoredProcedureRepositoryBase {

    Page<Product> getAllProducts();
}
