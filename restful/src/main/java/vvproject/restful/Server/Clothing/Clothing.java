package vvproject.restful.Server.Clothing;

import com.fasterxml.jackson.annotation.JsonBackReference;
import vvproject.restful.Server.Enums.ClothingStatus;
import vvproject.restful.Server.Enums.Gender;
import vvproject.restful.Server.Enums.Size;
import vvproject.restful.Server.Enums.Type;
import vvproject.restful.Server.Member.Member;

import javax.persistence.*;
import java.util.Objects;

/**
 * The clothing entity generates a unique Id and
 * stores all important information about the clothing like
 * fitting, type, gender, etc. Including the owner.
 */
@Entity
public class Clothing {
    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Long version;

    private Gender gender;
    private Type type;
    private Size size;

    private float originalPrice;
    private float exchangePrice;

    private ClothingStatus clothingStatus;

    @ManyToOne
    @JsonBackReference
    private Member owner;

    public Clothing() {
        this.clothingStatus = ClothingStatus.INSHOP;
    }

    public Clothing(Gender gender, Type type, Size size, float originalPrice, float exchangePrice) {
        this.gender = gender;
        this.type = type;
        this.size = size;
        this.originalPrice = originalPrice;
        this.exchangePrice = exchangePrice;
        this.clothingStatus = ClothingStatus.INSHOP;
    }

    public Clothing(Gender gender, Type type, Size size, float originalPrice, float exchangePrice, Member owner) {
        this.gender = gender;
        this.type = type;
        this.size = size;
        this.originalPrice = originalPrice;
        this.exchangePrice = exchangePrice;
        this.owner = owner;
        this.clothingStatus = ClothingStatus.INSHOP;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothing clothing = (Clothing) o;
        return id.equals(clothing.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public float getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(float exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public ClothingStatus getClothingStatus() {
        return clothingStatus;
    }

    public void setClothingStatus(ClothingStatus clothingStatus) {
        this.clothingStatus = clothingStatus;
    }
}
