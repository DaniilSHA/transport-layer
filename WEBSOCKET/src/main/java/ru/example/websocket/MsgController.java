package ru.example.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MsgController {

    @MessageMapping("/chatting")
    @SendTo("/chat/common")
    public ClientMsg handleMsg(ClientMsg msg) throws Exception {
        return msg;
    }

}
