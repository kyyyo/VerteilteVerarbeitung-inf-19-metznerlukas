package vvproject.restful.Server.Main;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;
import vvproject.restful.Server.Clothing.ClothingExceptions.InsufficientFundsException;
import vvproject.restful.Server.Main.MainExceptions.MaxSellingSizeException;
import vvproject.restful.Server.Main.MainExceptions.WrongPricingException;
import vvproject.restful.Server.Member.Member;
import vvproject.restful.Server.Member.MemberExceptions.MemberNotFoundException;
import vvproject.restful.Server.Member.MemberExceptions.WrongLoginException;
import vvproject.restful.Server.Transaction.Transaction;
import vvproject.restful.Server.Transaction.TransactionException.TransactionNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Main controller contains all REST interfaces
 *
 * @author Lukas Metzner, sINFlumetz
 */
@Api
@RequestMapping("/api/v1")
@RestController
public class MainController {
    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @ApiOperation(
            value = "Returns every piece of clothing from the database",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/clothing",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Clothing>> findAll() {
        return this.mainService.findAllClothing();
    }

    @ApiOperation(
            value = "Returns a clothing with a specific id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/clothing/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Clothing> findById(@PathVariable Long id) throws ClothingNotFoundException {
        return this.mainService.findClothingById(id);
    }

    @ApiOperation(
            value = "Buy a specific clothing",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/clothing/{id}/buy",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> buyClothing(@PathVariable Long id, @RequestHeader Map<String, String> headers) throws ClothingNotFoundException, InsufficientFundsException, MemberNotFoundException, WrongLoginException {
        return this.mainService.buyClothing(id, headers.get("username"), headers.get("password"));
    }

    @ApiOperation(
            value = "Offer a new clothing",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/clothing/sell",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Clothing> sellClothing(@RequestBody Clothing c, @RequestHeader Map<String, String> headers) throws MemberNotFoundException, MaxSellingSizeException, WrongPricingException, WrongLoginException {
        return this.mainService.sellClothing(c, headers.get("username"), headers.get("password"));
    }

    @ApiOperation(
            value = "Register a new member",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Member> register(@RequestBody Member m) {
        return this.mainService.register(m);
    }

    @ApiOperation(
            value = "Delete a specific clothing",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/clothing/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteClothing(@PathVariable Long id, @RequestHeader Map<String, String> headers) throws MemberNotFoundException, WrongLoginException, ClothingNotFoundException {
        return this.mainService.removeClothingFromAccount(id, headers.get("username"), headers.get("password"));
    }

    @ApiOperation(
            value = "Returns every transaction from the database",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/transactions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Transaction>> getTransactions() {
        return this.mainService.getAllTransactions();
    }

    @ApiOperation(
            value = "Returns a transaction with a specific id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/transactions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) throws TransactionNotFoundException {
        return this.mainService.getTransaction(id);
    }

    @ApiOperation(
            value = "Updates clothing in database",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RequestMapping(
            value = "/clothing/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> updateClothing(@RequestBody Clothing c, @RequestHeader Map<String, String> headers) throws MemberNotFoundException, WrongLoginException, ClothingNotFoundException {
        return this.mainService.updateClothing(c, headers.get("username"), headers.get("password"));
    }
}
