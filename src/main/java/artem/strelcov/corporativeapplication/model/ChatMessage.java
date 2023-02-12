package artem.strelcov.corporativeapplication.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;

}
