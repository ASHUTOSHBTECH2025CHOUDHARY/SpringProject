package com.Greeting.greet.service;

import com.Greeting.greet.model.Greeting;
import org.springframework.stereotype.Service;
import com.Greeting.greet.repository.GreetingRepository;
import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {

    //UC2- Get greeting message
    public String getGreetingMessage() {
        return "Hello World from UC2";
    }
    private final GreetingRepository greetingRepository;

    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }
    //UC4:- Save greeting
    public Greeting saveGreeting(String message) {
        Greeting greeting = new Greeting();
        greeting.setMessage(message);
        return greetingRepository.save(greeting);
    }

    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }
    //UC5: Find greeting By Id
    public Greeting getGreetingById(Long id) {
        Optional<Greeting> greeting = greetingRepository.findById(id);
        return greeting.orElse(null);
    }
    //UC:6 Find all greetings
    public List<Greeting> getAllGreetingsUC6() {
        return greetingRepository.findAll();
    }
    //UC7 Edit Greeting
    public Greeting updateGreeting(Long id, String newMessage) {
        Optional<Greeting> existingGreeting = greetingRepository.findById(id);
        if (existingGreeting.isPresent()) {
            Greeting greeting = existingGreeting.get();
            greeting.setMessage(newMessage);
            return greetingRepository.save(greeting);
        }
        return null;
    }
    //UC8: delete Greeting by ID
    public boolean deleteGreeting(Long id) {
        if (greetingRepository.existsById(id)) {
            greetingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
