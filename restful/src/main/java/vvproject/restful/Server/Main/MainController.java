package vvproject.restful.Server.Main;

import io.swagger.annotations.Api;
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

import java.util.List;
import java.util.Map;

/**
 * Main controller contains all REST interfaces
 * @author Lukas Metzner, sINFlumetz
 */
@Api
@RestController
public class MainController {
    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(
            value = "/clothing",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Clothing>> findAll() {
        return this.mainService.findAllClothing();
    }

    @RequestMapping(
            value = "/clothing/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Clothing> findById(@PathVariable Long id) throws ClothingNotFoundException {
        return this.mainService.findClothingById(id);
    }

    @RequestMapping(
            value = "/clothing/buy/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> buyClothing(@PathVariable Long id, @RequestHeader Map<String, String> headers) throws ClothingNotFoundException, InsufficientFundsException, MemberNotFoundException, WrongLoginException {
        return this.mainService.buyClothing(id, headers.get("username"), headers.get("password"));
    }

    @RequestMapping(
            value = "/clothing/sell",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> sellClothing(@RequestBody Clothing c, @RequestHeader Map<String, String> headers) throws MemberNotFoundException, MaxSellingSizeException, WrongPricingException, WrongLoginException {
        return this.mainService.sellClothing(c, headers.get("username"), headers.get("password"));
    }

    @RequestMapping(
            value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> register(@RequestBody Member m) {
        return this.mainService.register(m);
    }

    @RequestMapping(
            value = "/clothing/remove/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> deleteClothing(@RequestBody String username, @RequestBody String password, @PathVariable Long id) throws MemberNotFoundException, WrongLoginException, ClothingNotFoundException {
        return this.mainService.removeClothingFromAccount(id, username, password);
    }

    @RequestMapping(
            value = "/transactions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Transaction>> getTransactions() {
        return this.mainService.getAllTransactions();
    }
}
