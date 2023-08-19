package com.sunny.maven.middle.websocket.controller;

import com.sunny.maven.middle.websocket.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/15 12:27
 */
@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) throws Exception {
        return message;
    }

    /**
     * 功能 同greeting函数
     * @param message
     * @throws Exception
     */
    @MessageMapping("/hello1")
    public void greeting1(Message message) throws Exception {
        messagingTemplate.convertAndSend("/topic/greetings", message);
    }

}
