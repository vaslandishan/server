package ir.server.vaslandishan.repository;

import ir.server.vaslandishan.models.Product;
import ir.server.vaslandishan.payload.TransactionResponse;
import ir.server.vaslandishan.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StoredProcedureRepositoryImpl implements StoredProcedureRepositoryBase {

    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Override
    public Page<Product> getAllProducts() {

        StoredProcedureQuery query = em.createStoredProcedureQuery("GetAllProducts");
        query.execute();


        List<Product[]> rows = query.getResultList();
        List<Product> result = new ArrayList<>(rows.size());
        for (Object[] row : rows) {

/*
            logger.info("row[0]"+row[0]);
            logger.info("row[1]"+row[1]);
            logger.info("row[2]"+row[2]);
            logger.info("row[3]"+row[3]);
            logger.info("row[4]"+row[4]);
            logger.info("row[5]"+row[5]);
            logger.info("row[6]"+row[6]);
*/



            result.add(new Product(
                    (String) row[0],
                    (String) row[1],
                    (Double) row[2],
                    Long.parseLong(row[3].toString()),
                    Long.parseLong(row[4].toString()),
                    Long.parseLong(row[5].toString()),
                    Long.parseLong(row[6].toString()))
            );
        }

        return new PageImpl<Product>(result);
    }

    @Override
    public Page<TransactionResponse> getAllTransactionByProductCode(String productCode,
                                                                    java.sql.Date fromDate,
                                                                    java.sql.Date toDate,
                                                                    int page, int size) {

        StoredProcedureQuery query = em.createStoredProcedureQuery("GetAllTransactionByProductCode");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);

        query.setParameter(1, productCode);
        query.setParameter(2, fromDate);
        query.setParameter(3, toDate);
        query.setParameter(4, page);
        query.setParameter(5, size);

        query.execute();


        List<TransactionResponse[]> rows = query.getResultList();
        List<TransactionResponse> result = new ArrayList<>(rows.size());
        for (Object[] row : rows) {

/*            logger.info("row[0]"+row[0]);
            logger.info("row[1]"+row[1]);
            logger.info("row[2]"+row[2]);
            logger.info("row[3]"+row[3]);
            logger.info("row[4]"+row[4]);
            logger.info("row[5]"+row[5]);
            logger.info("row[6]"+row[6]);
            logger.info("row[7]"+row[7]);
            logger.info("row[8]"+row[8]);
            logger.info("row[9]"+row[9]);
            logger.info("row[10]"+row[10]);
            logger.info("row[11]"+row[11]);
            logger.info("row[12]"+row[12]);
            logger.info("row[13]"+row[13]);
            logger.info("row[14]"+row[14]);
            logger.info("row[15]"+row[15]);
            logger.info("row[16]"+row[16]);*/



            result.add(new TransactionResponse(
             Long.parseLong(row[0].toString()),
            (Integer) row[1],
            (String) row[2],
                            (Timestamp)row[3],
            (Integer ) row[4],
                            (Timestamp)row[5],
            (String) row[6],
            (String) row[7],
            (String) row[8],
            (String) row[9],
            (String) row[10],
            (Integer) row[11],
            (String) row[12],
            (Integer) row[13],
                            (Timestamp)row[14],
            (String) row[15],
            (String) row[16]
                    )
            );
        }

        return new PageImpl<TransactionResponse>(result);
    }



}
