package com.Greeting.greet.Controller;

import org.springframework.web.bind.annotation.*;
import com.Greeting.greet.service.GreetingService;
import java.util.Map;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
    //This is work for UC1
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
    // UC2 Endpoints (Using Service Layer)
    @GetMapping("/uc2")
    public Map<String, String> uc2GetGreeting() {
        return Map.of("message", greetingService.getGreetingMessage());
    }

    @PostMapping("/uc2")
    public Map<String, String> uc2PostGreeting(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.getOrDefault("name", "Guest");
        return Map.of("message", "Hello, " + name + "is a UC2 POST request.");
    }

    @PutMapping("/uc2")
    public Map<String, String> uc2PutGreeting() {
        return Map.of("message", "from UC2 PUT");
    }

    @DeleteMapping("/uc2")
    public Map<String, String> uc2DeleteGreeting() {
        return Map.of("message", "from UC2 DELETE");
    }
}
