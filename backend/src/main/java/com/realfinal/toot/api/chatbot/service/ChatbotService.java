package com.realfinal.toot.api.chatbot.service;

import com.realfinal.toot.api.chatbot.dto.Ibubble;
import java.util.List;

public interface ChatbotService {

    void saveChatData(String accessToken, List<Ibubble> chatList);

    List<Ibubble> getChatData(String accessToken);

    void deleteChatData(String accessToken);
}
