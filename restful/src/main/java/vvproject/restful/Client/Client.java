package vvproject.restful.Client;

import vvproject.restful.Client.ClientExceptions.SellingErrorException;
import vvproject.restful.Client.ClientExceptions.TransactionFailedException;
import vvproject.restful.Client.ClientExceptions.TransactionManagement;
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

    public void register(
            String username,
            String password,
            String preName,
            String lastName,
            String eMail,
            String postTown,
            String address,
            int postCode) {
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
                0.0f
        );
        this.memberManagement.register(toRegister);
    }

    public List<Clothing> searchAllClothing() {
        return this.clothingManagement.searchAllClothing();
    }

    public Clothing findClothingById(Long id) throws ClothingNotFoundException {
        return this.clothingManagement.findById(id);
    }

    public void buyClothing(Long id, String username, String password) throws TransactionFailedException {
        this.clothingManagement.buyClothing(id, username, password);
    }

    public void sellClothing(
            Gender gender,
            Type type,
            Size size,
            float originalPrice,
            float exchangePrice,
            String username,
            String password
    ) throws SellingErrorException {
        Clothing toSell = new Clothing(
                gender, type, size, originalPrice, exchangePrice
        );
        this.clothingManagement.sellClothing(toSell, username, password);
    }

    public List<Transaction> findAllTransactions() {
        return this.transactionManagement.findAllTransactions();
    }

    public Transaction findTransaction(Long id) throws TransactionNotFoundException {
        return this.transactionManagement.findTransaction(id);
    }
}
