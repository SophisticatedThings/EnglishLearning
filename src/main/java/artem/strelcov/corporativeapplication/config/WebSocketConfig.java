package artem.strelcov.corporativeapplication.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{
    /**
    * provides an endpoint to which users will connect to use webSocket
    */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/ws").withSockJS();
    }

    /**
     * routes a message from some user to another user(broadcasting)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
       registry.setApplicationDestinationPrefixes("/app");
       registry.enableSimpleBroker("/topic");
    }
}
