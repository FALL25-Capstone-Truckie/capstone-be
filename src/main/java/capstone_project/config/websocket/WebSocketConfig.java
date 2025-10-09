package capstone_project.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple in-memory message broker for topics
        // Client subscribes to these destinations to receive messages
        config.enableSimpleBroker("/topic");

        // Prefix for client-to-server messages
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The endpoint that clients use to connect to our WebSocket server
        registry.addEndpoint("/ws")
                // Allow connections from specific origins, or use "*" for any origin
                .setAllowedOriginPatterns("*") // Use patterns instead of setAllowedOrigins for more flexibility
                // Enable SockJS fallback options for browsers that don't support WebSocket
                .withSockJS();
    }
}
