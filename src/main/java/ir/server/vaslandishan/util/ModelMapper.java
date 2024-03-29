package ir.server.vaslandishan.util;

import ir.server.vaslandishan.models.Booth;
import ir.server.vaslandishan.models.Product;
import ir.server.vaslandishan.models.User;
import ir.server.vaslandishan.payload.BoothResponse;
import ir.server.vaslandishan.payload.ProductResponse;
import ir.server.vaslandishan.payload.TransactionResponse;
import ir.server.vaslandishan.payload.UserSummary;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static BoothResponse mapBoothToBoothResponse(Booth booth, User creator) {
        BoothResponse boothResponse = new BoothResponse();
        boothResponse.setId(booth.getId());
        boothResponse.setTitle(booth.getTitle());
        boothResponse.setCreationDateTime(booth.getCreatedAt());
       // boothResponse.setExpirationDateTime(booth.getExpirationDateTime());
        //Instant now = Instant.now();
        //boothResponse.setExpired(booth.getExpirationDateTime().isBefore(now));

       List<ProductResponse> productResponses = booth.getProducts().stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setTitle(product.getTitle());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setFileId(product.getFileId());

            /*if(choiceVotesMap.containsKey(choice.getId())) {
                productResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                productResponse.setVoteCount(0);
            }*/
            return productResponse;
        }).collect(Collectors.toList());

        //boothResponse.setProducts(productResponses);
        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        boothResponse.setCreatedBy(creatorSummary);
        boothResponse.setProducts(productResponses);

/*
        if(userVote != null) {
            boothResponse.setSelectedProduct(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);
*/

        return boothResponse;
    }


    public static ProductResponse mapProductToProductResponse(Product product, User creator) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setTitle(product.getTitle());
        productResponse.setDescription(product.getDescription());
        productResponse.setCreationDateTime(product.getCreatedAt());

        //UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        //productResponse.setCreatedBy(creatorSummary);

        return productResponse;
    }

    public static TransactionResponse mapTransactionToTransactionResponse(TransactionResponse transaction) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setClient_ip(transaction.getClient_ip());
        transactionResponse.setCp(transaction.getCp());
        transactionResponse.setCreated_at(transaction.getCreated_at());
        transactionResponse.setMobile_no(transaction.getMobile_no());
        transactionResponse.setCreated_at_day(transaction.getCreated_at_day());
        transactionResponse.setDescr(transaction.getDescr());
        transactionResponse.setDescr2(transaction.getDescr2());
        transactionResponse.setOrderid(transaction.getOrderid());
        transactionResponse.setPrice(transaction.getPrice());
        transactionResponse.setPcode(transaction.getPcode());
        transactionResponse.setResult(transaction.getResult2());
        transactionResponse.setSent_to_sdp(transaction.getSent_to_sdp());
        transactionResponse.setTel(transaction.getTel());
        transactionResponse.setTx_debug(transaction.getTx_debug());
        transactionResponse.setTx_ref(transaction.getTx_ref());


        //UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        //productResponse.setCreatedBy(creatorSummary);

        return transactionResponse;
    }
}
