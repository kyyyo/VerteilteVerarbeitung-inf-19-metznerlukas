package vvproject.restful.Client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vvproject.restful.Client.ClientExceptions.SellingErrorException;
import vvproject.restful.Client.ClientExceptions.TransactionFailedException;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;

import java.util.Arrays;
import java.util.List;

public class ClothingManagement {

    private final String serviceURI;

    public ClothingManagement(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public List<Clothing> searchAllClothing() {
        String url = this.serviceURI + "/clothing";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Clothing[]> clothings =
                restTemplate.getForEntity(url, Clothing[].class);
        if (clothings.getStatusCode().equals(HttpStatus.OK)) {
            return Arrays.asList(clothings.getBody());
        } else
            return null;
    }

    public Clothing findById(Long id) throws ClothingNotFoundException {
        String url = this.serviceURI + "/clothing/" + id.toString();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Clothing> found = restTemplate.getForEntity(url, Clothing.class);
        if (found.getStatusCode().equals(HttpStatus.OK)) {
            return found.getBody();
        } else {
            throw new ClothingNotFoundException("Could not find clothing in database");
        }
    }

    public void buyClothing(Long id, String username, String password) throws TransactionFailedException {
        String url = this.serviceURI + "/clothing/" + id.toString() + "/buy";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().set("username", username);
            httpRequest.getHeaders().set("password", password);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (!response.getStatusCode().equals(HttpStatus.OK))
            throw new TransactionFailedException(response.getBody());
    }

    public void sellClothing(Clothing toSell, String username, String password) throws SellingErrorException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().set("username", username);
            httpRequest.getHeaders().set("password", password);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
        String url = this.serviceURI + "/clothing/sell";
        ResponseEntity<String> response = restTemplate.postForEntity(url, toSell, String.class);
        if (response.getStatusCode().equals(HttpStatus.OK))
            return;
        else throw new SellingErrorException(response.getBody());
    }
}
