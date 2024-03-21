package se.stykle.chatbot.service;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final ProducerTemplate producerTemplate;

    public ChatService(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    public String chatWithGPT(String userMessage) {
        try {
            String jsonPayload = String.format("""
                    {
                        "model": "gpt-3.5-turbo",
                        "messages": [
                            {"role": "user", "content": "%s"}
                        ]
                    }""", userMessage.replace("\"", "\\\""));

            return producerTemplate.requestBody("direct:chatGPT", jsonPayload, String.class);
        } catch (Exception e) {
            logger.error("Error sending message to OpenAI", e);
            throw new RuntimeException("Error communicating with OpenAI API", e);
        }
    }

}