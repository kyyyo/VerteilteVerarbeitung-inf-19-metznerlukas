package vvproject.restful.Client;

import org.springframework.web.client.RestTemplate;
import vvproject.restful.Server.Member.Member;

import java.net.URI;

public class MemberManagement {

    private final String serviceURI;

    public MemberManagement(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    public void register(Member toRegister) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(this.serviceURI + "/register", toRegister, Member.class);
        System.out.println("Location: " + uri.toASCIIString());
    }


}
