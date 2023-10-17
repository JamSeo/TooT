package com.realfinal.toot.api.chatbot.controller;

import com.realfinal.toot.api.chatbot.dto.Ibubble;
import com.realfinal.toot.api.chatbot.service.ChatbotService;
import com.realfinal.toot.common.model.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatbot")
@RestController
public class ChatbotController {

    private final ChatbotService chatbotService;
    private final String SUCCESS = "success";

    /**
     * 채팅 내역 저장. 리스트로 request.
     *
     * @param accessToken 누구 채팅 내역인지 확인용
     * @param chatList    저장할 채팅 리스트
     * @return success - success
     */
    @PostMapping("/")
    public CommonResponse<?> saveChat(@RequestHeader(value = "accesstoken") String accessToken,
            @RequestBody List<Ibubble> chatList) {
        log.info("ChatbotController_saveChat_start: accessToken - " + accessToken);
        chatbotService.saveChatData(accessToken, chatList);
        log.info("ChatbotController_saveChat_end: chat saved ");
        return CommonResponse.success(SUCCESS);

    }

    /**
     * 채팅 내역 불러오기
     *
     * @param accessToken 불러올 유저의 토큰
     * @return 지금까지 저장된 채팅 내역
     */
    @GetMapping("/")
    public CommonResponse<?> readChat(@RequestHeader(value = "accesstoken") String accessToken) {
        log.info("ChatbotController_readChat_start: " + accessToken);
        List<Ibubble> chatList = chatbotService.getChatData(accessToken);
        log.info("ChatbotController_readChat_end: " + chatList);
        return CommonResponse.success(chatList);
    }

    /**
     * 채팅 내역 삭제
     *
     * @param accessToken 대화 내역 삭제할 유저의 토큰
     * @return success
     */
    @DeleteMapping("/")
    public CommonResponse<?> deleteChat(@RequestHeader(value = "accesstoken") String accessToken) {
        log.info("ChatbotController_deleteChat_start: " + accessToken);
        chatbotService.deleteChatData(accessToken);
        log.info("ChatbotController_deleteChat_end: success");
        return CommonResponse.success(SUCCESS);
    }
}
