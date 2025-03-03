package com.Greeting.greet.Controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping
    public Map<String, String> getGreeting() {
        return Map.of("message", "Hello from GET!");
    }

    @PostMapping
    public Map<String, String> postGreeting() {
        return Map.of("message", "Hello from POST!");
    }

    @PutMapping
    public Map<String, String> putGreeting() {
        return Map.of("message", "Hello from PUT!");
    }

    @DeleteMapping
    public Map<String, String> deleteGreeting() {
        return Map.of("message", "Hello from DELETE!");
    }
}
