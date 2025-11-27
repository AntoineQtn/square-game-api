package org.example.squaregameapi.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class UserClientService {

    private final RestClient restClient;

    public UserClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    public boolean verifyUserExists(String username){
        try {
            restClient.get()
                    .uri("/users/{username}", username)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (HttpClientErrorException.NotFound e){
            return false;
        }
    }

}
