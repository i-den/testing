package com.idenchev.testing;

import lombok.RequiredArgsConstructor;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/some")
@RequiredArgsConstructor
public class SomeController {

    private final SomeService service;

    @RequestMapping
    public String someMethod() {
        return service.someMethod();
    }
}
