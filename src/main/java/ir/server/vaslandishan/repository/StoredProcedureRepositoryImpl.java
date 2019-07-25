package ir.server.vaslandishan.repository;

import ir.server.vaslandishan.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class StoredProcedureRepositoryImpl implements StoredProcedureRepositoryBase {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Product> getAllProducts() {

       // StoredProcedureQuery findByYearProcedure =
       //         em.createNamedStoredProcedureQuery("GetAllProducts");

        StoredProcedureQuery findByYearProcedure = em.createStoredProcedureQuery("GetAllProducts");
        findByYearProcedure.execute();


        //List<Product> products = (List<Product>)findByYearProcedure.getResultList();

        //Page pageable = new PageImpl(findByYearProcedure.getResultList());

        //int start = 1;
        //int end = (start + pageable.getTotalPages()) > products.size() ? products.size() : (start + pageable.getTotalPages());

        //Page pages = new PageImpl<Product>(products.subList(start, end), pageable, products.size());


        return new PageImpl<Product>(findByYearProcedure.getResultList());

    }
}
