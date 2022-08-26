package com.github.dankook_univ.meetwork.slack.application;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    @Value("${slack.token}")
    String token;

    @Value("${slack.channel-id}")
    String channelId;

    @Override
    public void sendMessage(String message) {
        MethodsClient client = Slack.getInstance().methods();

        try {
            client.chatPostMessage(req -> req
                .token(token)
                .channel(channelId)
                .text(message)
            );

            log.info("[SlackServiceImpl Info] 슬랙 알림이 전송되었습니다.");
        } catch (IOException | SlackApiException e) {
            log.error("[SlackServiceImpl Error] 알림 전송 실패 ({})", e.getMessage());
        }
    }
}
