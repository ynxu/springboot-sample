package com.light4j.sample.service;

import com.light4j.sample.BaseTests;
import org.junit.Test;
import org.light4j.xml.OuterService;
import org.springframework.beans.factory.annotation.Autowired;

public class XmlBeanTests extends BaseTests {

    @Autowired
    OuterService outerService;

    @Test
    public void xmlBeanTest(){
        outerService.print();
    }
}
