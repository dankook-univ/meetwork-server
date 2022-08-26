package com.github.dankook_univ.meetwork.slack.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SlackServiceImplTest {

    @Autowired
    private SlackServiceImpl slackService;

    @Test
    @DisplayName("슬랙 메세지를 전송할 수 있어요.")
    public void sendSlackMessage() {
        assertDoesNotThrow(
            () -> slackService.sendMessage("Slack Test Message")
        );
    }
}