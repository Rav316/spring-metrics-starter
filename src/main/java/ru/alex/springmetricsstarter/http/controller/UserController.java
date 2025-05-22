package ru.alex.springmetricsstarter.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Random random = new Random();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Integer, String> users = IntStream.rangeClosed(1, 20)
            .boxed()
            .collect(Collectors.toMap(
                    i -> i,
                    i -> "user" + i
            ));

    @GetMapping
    public Map<Integer, String> findAll() {
        int randomNumber = random.nextInt(6);
        if(randomNumber % 2 == 0) {
            log.error("findAll error log");
        } else {
            log.info("findAll info log");
        }
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable("id") Integer id) {
        if(!users.containsKey(id)) {
            log.warn("warn log");
            return ResponseEntity.notFound().build();
        }
        log.info("findById log");
        return new ResponseEntity<>(users.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> create() {
        throw new RuntimeException("test exception");
    }
}
