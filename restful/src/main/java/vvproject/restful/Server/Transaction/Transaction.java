package vvproject.restful.Server.Transaction;

import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Member.Member;

import javax.persistence.*;

/**
 * A transaction stores the buyer and seller including
 * the clothing. Every transaction gets a unique random
 * generated ID.
 * @author Lukas Metzner, sINFlumetz
 */
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;

    @ManyToOne
    private Clothing boughtClothing;

    @ManyToOne
    private Member buyer;
    @ManyToOne
    private Member seller;

    public Transaction() {

    }

    public Transaction(Clothing boughtClothing, Member buyer, Member seller) {
        this.boughtClothing = boughtClothing;
        this.buyer = buyer;
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public Clothing getBoughtClothing() {
        return boughtClothing;
    }

    public void setBoughtClothing(Clothing boughtClothing) {
        this.boughtClothing = boughtClothing;
    }

    public Member getBuyer() {
        return buyer;
    }

    public void setBuyer(Member buyer) {
        this.buyer = buyer;
    }

    public Member getSeller() {
        return seller;
    }

    public void setSeller(Member seller) {
        this.seller = seller;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
