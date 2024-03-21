package se.stykle.chatbot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.stykle.chatbot.service.ChatService;


@RestController
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithGPT(@RequestBody String message) {
        try {
            String response = chatService.chatWithGPT(message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("An error occurred processing the chat request", e);
            return ResponseEntity.internalServerError().body("An error occurred processing your request.");
        }
    }

}
