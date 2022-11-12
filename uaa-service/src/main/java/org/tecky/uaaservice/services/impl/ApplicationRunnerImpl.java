package org.tecky.uaaservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.tecky.uaaservice.services.FetchClientScrect;

@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    FetchClientScrect fetchClientScrect;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        fetchClientScrect.fetch();
    }
}
