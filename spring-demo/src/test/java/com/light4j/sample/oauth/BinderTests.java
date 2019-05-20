package com.light4j.sample.oauth;

import com.light4j.sample.BaseTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.Optional;

public class BinderTests extends BaseTests {

    @Autowired
    Environment environment;

    @Test
    public void testBinder() {
        Binder binder = Binder.get(environment);
        System.out.println(binder);
        String binder1 = binder.bind("authentication.oauth.client-id", String.class).get();
        System.out.println(binder1);
    }
}
