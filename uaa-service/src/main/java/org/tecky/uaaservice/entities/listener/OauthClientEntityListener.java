package org.tecky.uaaservice.entities.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tecky.uaaservice.services.FetchClientScrect;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

@Component
public class OauthClientEntityListener {

    @PostPersist
    @PostRemove
    public void postPersist(Object entity) throws Exception {

        RestTemplate res = new RestTemplate();

        Thread thread = new Thread(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                res.getForEntity("http://127.0.0.1:9003/fetch", String.class);
            }
        });

        thread.start();
    }

}
