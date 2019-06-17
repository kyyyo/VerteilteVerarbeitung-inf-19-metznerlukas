package vvproject.restful.Server.Main;

import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;
import vvproject.restful.Server.Clothing.ClothingService;
import vvproject.restful.Server.Enums.ClothingStatus;
import vvproject.restful.Server.Main.MainExceptions.MaxSellingSizeException;
import vvproject.restful.Server.Main.MainExceptions.WrongPricingException;
import vvproject.restful.Server.Member.Member;
import vvproject.restful.Server.Member.MemberExceptions.MemberNotFoundException;
import vvproject.restful.Server.Member.MemberExceptions.WrongLoginException;
import vvproject.restful.Server.Member.MemberService;
import vvproject.restful.Server.Transaction.Transaction;
import vvproject.restful.Server.Transaction.TransactionException.TransactionNotFoundException;
import vvproject.restful.Server.Transaction.TransactionService;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Main services contains the main logic of the Server.
 * Combines all the subservices.
 *
 * @author Lukas Metzner, sINFlumetz
 */
@Service("MainService")
public class MainService {
    private MemberService memberService;
    private TransactionService transactionService;
    private ClothingService clothingService;

    @Autowired
    public MainService(MemberService memberService, TransactionService transactionService, ClothingService clothingService) {
        this.memberService = memberService;
        this.transactionService = transactionService;
        this.clothingService = clothingService;
    }

    public ResponseEntity<Void> removeClothingFromAccount(Long clothingId, String username, String password) throws MemberNotFoundException, WrongLoginException, ClothingNotFoundException {
        Member owner = this.memberService.login(username, password);
        Clothing toRemove = this.clothingService.findById(clothingId);
        owner.removeClothing(toRemove);
        this.memberService.updateMember(owner);
        this.clothingService.deleteClothing(toRemove);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Clothing> findClothingById(Long id) throws ClothingNotFoundException {
        return new ResponseEntity<>(this.clothingService.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<List<Clothing>> findAllClothing() {
        return new ResponseEntity<>(this.clothingService.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<String> buyClothing(Long id, String username, String password) {
        Member buyer = null;
        try {
            buyer = this.memberService.login(username, password);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (WrongLoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
        Clothing clothingToBePurchased = null;
        try {
            clothingToBePurchased = this.clothingService.findById(id);
        } catch (ClothingNotFoundException e) {
            return new ResponseEntity<>("Clothing you want to buy does not exist.", HttpStatus.NOT_FOUND);
        }
        Member seller = clothingToBePurchased.getOwner();
        if (buyer.getAccountBalance() < (clothingToBePurchased.getExchangePrice() + 0.5f))
            return new ResponseEntity<>("Insufficient funds", HttpStatus.PAYMENT_REQUIRED);
        if (clothingToBePurchased.getClothingStatus().equals(ClothingStatus.SOLD))
            return new ResponseEntity<>("Item already sold", HttpStatus.GONE);

        buyer.removeBalance(clothingToBePurchased.getExchangePrice() - 0.5f);
        seller.addBalance(clothingToBePurchased.getExchangePrice() - 0.5f);
        seller.removeClothing(clothingToBePurchased);


        clothingToBePurchased.setClothingStatus(ClothingStatus.SOLD);
        Transaction transaction = new Transaction(clothingToBePurchased, buyer, seller);

        this.clothingService.updateClothing(clothingToBePurchased);
        try {
            this.memberService.updateMember(buyer);
            this.memberService.updateMember(seller);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        this.transactionService.saveTransaction(transaction);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Clothing> sellClothing(Clothing c, String username, String password) throws WrongPricingException, MaxSellingSizeException, MemberNotFoundException, WrongLoginException {
        Member seller = this.memberService.login(username, password);
        if ((c.getExchangePrice() / c.getOriginalPrice()) > 0.5 || (c.getExchangePrice() / c.getOriginalPrice()) < 0.1)
            throw new WrongPricingException("Exchange price should be between 10 and 50 percent of the original price.");

        if (seller.getOwnedClothingSize() >= 10)
            throw new MaxSellingSizeException("You can only sell 10 items at a time.");

        Clothing clothing = new Clothing(
                c.getGender(),
                c.getType(),
                c.getSize(),
                c.getOriginalPrice(),
                c.getExchangePrice(),
                seller
        );

        seller.addClothing(clothing);
        this.clothingService.saveClothing(clothing);
        this.memberService.updateMember(seller);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    public ResponseEntity<Member> register(Member m) {
        String hashedPw = Hashing.sha256().hashString(m.getPassword(), StandardCharsets.UTF_8).toString();
        m.setPassword(hashedPw);
        this.memberService.saveMember(m);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(this.transactionService.findAllTransactions(), HttpStatus.OK);
    }

    public ResponseEntity<Transaction> getTransaction(Long id) throws TransactionNotFoundException {
        return new ResponseEntity<>(this.transactionService.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<Void> updateClothing(Clothing c, String username, String password) throws MemberNotFoundException, WrongLoginException, ClothingNotFoundException {
        Member m = this.memberService.login(username, password);
        if (m == null)
            throw new WrongLoginException("Wrong login credentials");
        this.clothingService.updateClothing(c);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
