package com.Greeting.greet.Controller;

import com.Greeting.greet.model.Greeting;
import org.springframework.web.bind.annotation.*;
import com.Greeting.greet.service.GreetingService;
import java.util.Map;
import java.util.List;

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
    //UC3
    @GetMapping("/uc3")
    public Map<String, String> uc3GetGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        String message;
        if (firstName != null && lastName != null) {
            message = "Hello, " + firstName + " " + lastName;
        } else if (firstName != null) {
            message = "Hello, " + firstName ;
        } else if (lastName != null) {
            message = "Hello, " + lastName;
        } else {
            message = "Hello, World";
        }

        return Map.of("message", message);
    }
    // UC4 - Save Greeting Message
    @PostMapping("/save")
    public Greeting saveGreeting(@RequestBody Map<String, String> requestBody) {
        String message = requestBody.getOrDefault("message", "Hello, World!");
        return greetingService.saveGreeting(message);
    }
    // UC4 - Retrieve All Greetings additional route for getting all greetings
    @GetMapping("/all")
    public List<Greeting> getAllGreetings() {
        return greetingService.getAllGreetings();
    }
    //This is UC5 -:  Find Greeting by ID
    @GetMapping("/uc5/{id}")
    public Map<String, String> uc5FindGreetingById(@PathVariable Long id) {
        Greeting greeting = greetingService.getGreetingById(id);
        if (greeting != null) {
            return Map.of("message", greeting.getMessage());
        } else {
            return Map.of("error", "ID" + id);
        }
    }
    //This is UC6: Find all the greetings
    @GetMapping("/uc6")
    public List<Map<String, String>> getAllGreetingsUC6() {
        return greetingService.getAllGreetings().stream()
                .map(greet -> Map.of(
                        "id", greet.getId().toString(),
                        "message", greet.getMessage()))
                .toList();
    }
    //This is UC7:Edit the greeting
    @PutMapping("/uc7/{id}")
    public Map<String, String> uc7UpdateGreeting(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        String newMessage = requestBody.get("message");
        Greeting updatedGreeting = greetingService.updateGreeting(id, newMessage);
        if (updatedGreeting != null) {
            return Map.of("message", "updated successfully", "updatedMessage", updatedGreeting.getMessage());
        } else {
            return Map.of("error", "Greeting " + id);
        }
    }
    //UC8: Delete Greeting by ID
    @DeleteMapping("/uc8/{id}")
    public Map<String, String> uc8DeleteGreeting(@PathVariable Long id) {
        boolean isDeleted = greetingService.deleteGreeting(id);
        if (isDeleted) {
            return Map.of("message", "Greeting deleted successfully");
        } else {
            return Map.of("error", "Greeting not found for ID: " + id);
        }
    }
}