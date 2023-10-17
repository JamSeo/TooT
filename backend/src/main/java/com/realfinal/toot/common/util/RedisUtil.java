package com.realfinal.toot.common.util;

import com.realfinal.toot.common.exception.user.RedisNotDeletedException;
import com.realfinal.toot.common.exception.user.RedisNotSavedException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 토큰 레디스에서 가져요기. 없을 경우 null return
     *
     * @param key redis 키 ("token 분류" + userId)
     * @return 값 (토큰)
     */
    public String getData(String key) {
        log.info("RedisUtil_getData_start: " + key);
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String token = valueOperations.get(key);
        log.info("RedisUtil_getData_end: " + token);
        return token;
    }

    /**
     * 토큰 저장할때 사용. 이건 시간 지나면 알아서 redis에서 삭제됨. 없어서 요청 들어오면 null 리턴
     *
     * @param key      redis 키 ("token 분류" + userId)
     * @param value    redis value (token)
     * @param duration redis 만료기간
     */
    public void setDataWithExpire(String key, String value, Long duration) {
        log.info("RedisUtil_setDataWithExpire_start: " + key + " " + value + " " + duration);
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
        // 저장된 값을 검증
        String storedValue = valueOperations.get(key);
        if (storedValue == null || !storedValue.equals(value)) {
            throw new RedisNotSavedException();
        }
        log.info("RedisUtil_setDataWithExpire_end: token saved in redis");
    }

    /**
     * redis에서 데이터 삭제. 문제 발생시 에러
     *
     * @param key "token 분류" + userId
     */
    public void deleteData(String key) {
        log.info("RedisUtil_deleteData_start: " + key);
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            throw new RedisNotDeletedException();
        }
        log.info("RedisUtil_deleteData_end: token deleted from redis");
    }

    /**
     * 주어진 키로 레디스에서 채팅 데이터를 조회
     *
     * @param key the Redis key ("chat" + userId)
     * @return the chat data in String format
     */
    public String getChatData(String key) {
        log.info("RedisUtil_getChatData_start: " + key);
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String chatData = valueOperations.get(key);
        log.info("RedisUtil_getChatData_end: " + chatData);
        return chatData;
    }

    /**
     * 주어진 키, 값, 만료 시간으로 레디스에 채팅 데이터를 저장
     *
     * @param key      the Redis key ("chat" + userId)
     * @param value    the chat data in String format
     * @param duration expiration time in seconds
     */
    public void setChatDataWithExpire(String key, String value, Long duration) {
        log.info("RedisUtil_setChatDataWithExpire_start: " + key + " " + value + " " + duration);
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
        // Validate saved value
        String storedValue = valueOperations.get(key);
        if (storedValue == null || !storedValue.equals(value)) {
            log.warn("RedisUtil_setChatDataWithExpire_mid: problem while saving chat to redis");
            throw new RedisNotSavedException();
        }
        log.info("RedisUtil_setChatDataWithExpire_end: chat data saved in redis");
    }

    /**
     * 주어진 키의 레디스 채팅 데이터를 삭제
     *
     * @param key the Redis key ("chat" + userId)
     */
    public void deleteChatData(String key) {
        log.info("RedisUtil_deleteChatData_start: " + key);
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            throw new RedisNotDeletedException();
        }
        log.info("RedisUtil_deleteChatData_end: chat data deleted from redis");
    }
}