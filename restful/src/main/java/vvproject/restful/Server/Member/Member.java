package vvproject.restful.Server.Member;

import vvproject.restful.Server.Clothing.Clothing;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.util.List;
import java.util.Objects;

@Entity
public class Member {
    @OneToMany
    List<Clothing> ownedClothing;
    @Id
    private String username;
    private String password;
    @Version
    private Long version;
    private float accountBalance;
    private String preName;
    private String lastName;
    private String eMail;
    private String postTown;
    private String address;
    private int postCode;

    public Member() {

    }

    public Member(String username, String password, String preName, String lastName, String eMail, String postTown, String address, int postCode, Long version, float accountBalance) {
        this.username = username;
        this.password = password;
        this.preName = preName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.postTown = postTown;
        this.address = address;
        this.postCode = postCode;
        this.version = version;
        this.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return username.equals(member.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void addBalance(float value) {
        this.accountBalance += value;
    }

    public void removeBalance(float value) {
        this.accountBalance -= value;
    }

    public void addClothing(Clothing c) {
        this.ownedClothing.add(c);
    }

    public void removeClothing(Clothing c) {
        this.ownedClothing.remove(c);
    }

    public int getOwnedClothingSize() {
        return this.ownedClothing.size();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPostTown() {
        return postTown;
    }

    public void setPostTown(String postTown) {
        this.postTown = postTown;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }
}
