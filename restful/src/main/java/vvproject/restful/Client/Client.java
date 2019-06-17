package vvproject.restful.Client;

import org.springframework.http.HttpStatus;
import vvproject.restful.Client.ClientExceptions.TransactionFailedException;
import vvproject.restful.Client.Management.ClothingManagement;
import vvproject.restful.Client.Management.MemberManagement;
import vvproject.restful.Client.Management.TransactionManagement;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;
import vvproject.restful.Server.Enums.Gender;
import vvproject.restful.Server.Enums.Size;
import vvproject.restful.Server.Enums.Type;
import vvproject.restful.Server.Member.Member;
import vvproject.restful.Server.Transaction.Transaction;
import vvproject.restful.Server.Transaction.TransactionException.TransactionNotFoundException;

import java.util.List;

public class Client {

    private final MemberManagement memberManagement;
    private final ClothingManagement clothingManagement;
    private final TransactionManagement transactionManagement;

    public Client(String serviceURL) {
        this.clothingManagement = new ClothingManagement(serviceURL);
        this.memberManagement = new MemberManagement(serviceURL);
        this.transactionManagement = new TransactionManagement(serviceURL);
    }

    public HttpStatus register(
            String username,
            String password,
            String preName,
            String lastName,
            String eMail,
            String postTown,
            String address,
            int postCode,
            float accountBalance) {
        Member toRegister = new Member(
                username,
                password,
                preName,
                lastName,
                eMail,
                postTown,
                address,
                postCode,
                0L,
                accountBalance
        );
        return this.memberManagement.register(toRegister);
    }

    public List<Clothing> findAllClothing() {
        return this.clothingManagement.findAllClothing();
    }

    public Clothing findClothingById(Long id) throws ClothingNotFoundException {
        return this.clothingManagement.findById(id);
    }

    public HttpStatus buyClothing(Long id, String username, String password) throws TransactionFailedException {
        return this.clothingManagement.buyClothing(id, username, password);
    }

    public HttpStatus sellClothing(
            Gender gender,
            Type type,
            Size size,
            float originalPrice,
            float exchangePrice,
            String username,
            String password
    ) {
        Clothing toSell = new Clothing(
                gender, type, size, originalPrice, exchangePrice
        );
        return this.clothingManagement.sellClothing(toSell, username, password);
    }

    public List<Transaction> findAllTransactions() {
        return this.transactionManagement.findAllTransactions();
    }

    public Transaction findTransaction(Long id) throws TransactionNotFoundException {
        return this.transactionManagement.findTransaction(id);
    }

    public void updateClothing(Clothing c, String username, String password) {
        this.clothingManagement.updateClothing(c, username, password);
    }

    public void deleteClothing(Long id, String username, String password) {
        this.clothingManagement.deleteClothing(id, username, password);
    }
}
