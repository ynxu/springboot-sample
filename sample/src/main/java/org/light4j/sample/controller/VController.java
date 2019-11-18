package org.light4j.sample.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("v")
public class VController<V extends VController.Base> {

    @PostMapping("/test")
    public String v(@RequestBody V v){
        log.info(v.toString());
        return v.toString();
    }

    public static class Base{}

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class A extends Base{
        private String a;
    }

    public static class B extends Base{
        private String b;
    }

}
