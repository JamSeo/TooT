package com.realfinal.toot.api.chatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinal.toot.api.chatbot.dto.Ibubble;
import com.realfinal.toot.common.exception.chatbot.ChatJsonfyException;
import com.realfinal.toot.common.util.JwtProviderUtil;
import com.realfinal.toot.common.util.RedisUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatbotServiceImpl implements ChatbotService {

    private final RedisUtil redisUtil;
    private final JwtProviderUtil jwtProviderUtil;
    private final ObjectMapper objectMapper;

    /**
     * 채팅 내역 이어서 저장
     *
     * @param accessToken 채팅 내역 저장할 유저
     * @param chatList    새로 저장할 채팅 내용
     */
    @Override
    public void saveChatData(String accessToken, List<Ibubble> chatList) {
        log.info("ChatbotServiceImpl_saveChatData_start: accessToken - " + accessToken);
        try {
            Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
            String key = "chat" + userId;
            // 기존 채팅 내용 읽어오기
            String existingChatJson = redisUtil.getChatData(key);
            List<Ibubble> existingChatList = new ArrayList<>();
            if (existingChatJson != null && !existingChatJson.isEmpty()) {
                existingChatList = objectMapper.readValue(existingChatJson, new TypeReference<>() {
                });
            }
            // 기존 채팅 내역에 새로운 채팅 내용 추가
            existingChatList.addAll(chatList);
            String chatJson = objectMapper.writeValueAsString(existingChatList);
            // 전체 채팅 내역 다시 저장
            redisUtil.setChatDataWithExpire(key, chatJson, 86400L); // (60 * 60 * 24)
            log.info("ChatbotServiceImpl_saveChatData_end: Data saved successfully");
        } catch (JsonProcessingException e) {
            log.error("ChatbotServiceImpl_saveChatData_mid: Serialization error ", e);
            throw new ChatJsonfyException();
        }
    }


    /**
     * 채팅 내역 불러오기
     *
     * @param accessToken 채팅 내역 가져올 유저의 토큰
     * @return 지금까지 저장되어있는 채팅 내역
     */
    @Override
    public List<Ibubble> getChatData(String accessToken) {
        log.info("ChatbotServiceImpl_getChatData_start: accessToken - " + accessToken);
        try {
            Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
            String key = "chat" + userId;
            String chatJson = redisUtil.getChatData(key);
            if (chatJson == null) {
                log.warn("ChatbotServiceImpl_getChatData_mid: No chat data found for user " + userId);
                return Collections.emptyList(); // No chat data found
            }
            log.info("ChatbotServiceImpl_getChatData_end: Data retrieved successfully");
            // Use TypeReference to inform Jackson about the List type (ibubble 리스트)
            return objectMapper.readValue(chatJson, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("ChatbotServiceImpl_getChatData_mid: Deserialization error ", e);
            return Collections.emptyList(); //채팅 내역 없음
        }
    }

    /**
     * 채팅 내역 삭제
     *
     * @param accessToken 삭제할 유저의 토큰
     */
    @Override
    public void deleteChatData(String accessToken) {
        log.info("ChatbotServiceImpl_deleteChatData_start: accessToken - " + accessToken);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        String key = "chat" + userId;
        redisUtil.deleteChatData(key);
        log.info("ChatbotServiceImpl_deleteChatData_end: Data deleted successfully");
    }

}
