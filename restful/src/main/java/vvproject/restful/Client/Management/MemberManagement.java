package vvproject.restful.Client.Management;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vvproject.restful.Server.Member.Member;

import java.util.ArrayList;

public class MemberManagement {

    private final String serviceURI;

    public MemberManagement(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public HttpStatus register(Member toRegister) {
        toRegister.setOwnedClothing(new ArrayList<>());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Member> response = restTemplate.postForEntity(this.serviceURI + "/register", toRegister, Member.class);
        return response.getStatusCode();
    }


}
