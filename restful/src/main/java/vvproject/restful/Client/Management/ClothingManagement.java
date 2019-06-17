package vvproject.restful.Client.Management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vvproject.restful.Client.ClientExceptions.TransactionFailedException;
import vvproject.restful.Server.Clothing.Clothing;
import vvproject.restful.Server.Clothing.ClothingExceptions.ClothingNotFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ClothingManagement {

    private final String serviceURI;

    public ClothingManagement(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public List<Clothing> findAllClothing() {
        String url = this.serviceURI + "/clothing";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Clothing[]> clothings =
                restTemplate.getForEntity(url, Clothing[].class);
        if (clothings.getStatusCode().equals(HttpStatus.OK)) {
            return Arrays.asList(clothings.getBody());
        } else
            return null;
    }

    public void deleteClothing(Long id, String username, String password) {
        String url = this.serviceURI + "/clothing/" + id.toString();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().set("username", username);
            httpRequest.getHeaders().set("password", password);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
        restTemplate.delete(url);
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

    public HttpStatus buyClothing(Long id, String username, String password) throws TransactionFailedException {
        String url = this.serviceURI + "/clothing/" + id.toString() + "/buy";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().set("username", username);
            httpRequest.getHeaders().set("password", password);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode();
    }

    public HttpStatus sellClothing(Clothing toSell, String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().set("username", username);
            httpRequest.getHeaders().set("password", password);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
        String url = this.serviceURI + "/clothing/sell";
        ResponseEntity<Clothing> response = restTemplate.postForEntity(url, toSell, Clothing.class);
        return response.getStatusCode();
    }

    public void updateClothing(Clothing toUpdate, String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().set("username", username);
            httpRequest.getHeaders().set("password", password);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });
        String url = this.serviceURI + "/clothing/" + toUpdate.getId();
        restTemplate.put(url, toUpdate, new HashMap<>());
    }
}
