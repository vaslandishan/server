package ir.server.vaslandishan.payload;

import ir.server.vaslandishan.models.Booth;
import ir.server.vaslandishan.models.OrderDetail;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class TransactionResponse {

    private Long id;
    private Integer cp;
    private String pcode;


    private Timestamp created_at	;
    private Integer result	;

    private Timestamp sent_to_sdp	;
    private String tel	;
    private String orderid	;
    private String descr	;
    private String tx_ref	;
    private String client_ip	;
    private Integer price;
    private String descr2	;
    private Integer result2	;


    private Timestamp created_at_day	;
    private String mobile_no	;
    private String tx_debug	;

    public TransactionResponse()
    {

    };

    public TransactionResponse(long id, Integer cp, String pcode, Timestamp created_at, Integer result, Timestamp sent_to_sdp, String tel, String orderid, String descr, String tx_ref, String client_ip, Integer price, String descr2, Integer result2, Timestamp created_at_day, String mobile_no, String tx_debug) {
        this.id = id;
        this.cp = cp;
        this.pcode = pcode;
        this.created_at = created_at;
        this.result = result;
        this.sent_to_sdp = sent_to_sdp;
        this.tel = tel;
        this.orderid = orderid;
        this.descr = descr;
        this.tx_ref = tx_ref;
        this.client_ip = client_ip;
        this.price = price;
        this.descr2 = descr2;
        this.result2 = result2;
        this.created_at_day = created_at_day;
        this.mobile_no = mobile_no;
        this.tx_debug = tx_debug;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Timestamp getSent_to_sdp() {
        return sent_to_sdp;
    }

    public void setSent_to_sdp(Timestamp sent_to_sdp) {
        this.sent_to_sdp = sent_to_sdp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getTx_ref() {
        return tx_ref;
    }

    public void setTx_ref(String tx_ref) {
        this.tx_ref = tx_ref;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescr2() {
        return descr2;
    }

    public void setDescr2(String descr2) {
        this.descr2 = descr2;
    }

    public Integer getResult2() {
        return result2;
    }

    public void setResult2(Integer result2) {
        this.result2 = result2;
    }

    public Timestamp getCreated_at_day() {
        return created_at_day;
    }

    public void setCreated_at_day(Timestamp created_at_day) {
        this.created_at_day = created_at_day;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTx_debug() {
        return tx_debug;
    }

    public void setTx_debug(String tx_debug) {
        this.tx_debug = tx_debug;
    }
}
