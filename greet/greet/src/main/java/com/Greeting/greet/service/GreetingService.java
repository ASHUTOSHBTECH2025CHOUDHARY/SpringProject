package com.Greeting.greet.service;

import com.Greeting.greet.model.Greeting;
import org.springframework.stereotype.Service;
import com.Greeting.greet.repository.GreetingRepository;
import java.util.List;

@Service
public class GreetingService {

    public String getGreetingMessage() {
        return "Hello World from UC2";
    }
    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    public Greeting saveGreeting(String message) {
        Greeting greeting = new Greeting();
        greeting.setMessage(message);
        return greetingRepository.save(greeting);
    }

    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }
}
