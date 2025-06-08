package com.bkopec.confintimes.controller;

import com.bkopec.confintimes.dto.CreateMessageDto;
import com.bkopec.confintimes.dto.CreateTopicDto;
import com.bkopec.confintimes.dto.MessageDto;
import com.bkopec.confintimes.dto.TopicDto;
import com.bkopec.confintimes.service.MessageBoardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class MessageBoardController {

    private final MessageBoardService messageBoardService;

    @Autowired
    public MessageBoardController(MessageBoardService messageBoardService) {
        this.messageBoardService = messageBoardService;
    }

    @PostMapping("/topics")
    public ResponseEntity<TopicDto> createTopic(@Valid @RequestBody CreateTopicDto createTopicDto) {
        TopicDto createdTopic = messageBoardService.createTopic(createTopicDto);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @GetMapping("/topics")
    public ResponseEntity<Page<TopicDto>> getTopics(
            @PageableDefault(size = 10, sort = "lastPostAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable) {
        Page<TopicDto> topics = messageBoardService.getTopics(pageable);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable Long id) {
        TopicDto topic = messageBoardService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }

    @PostMapping("/topics/{topicId}/messages")
    public ResponseEntity<MessageDto> addReply(
            @PathVariable Long topicId,
            @Valid @RequestBody CreateMessageDto createMessageDto) {
        MessageDto createdMessage = messageBoardService.addReply(topicId, createMessageDto);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping("/topics/{topicId}/messages")
    public ResponseEntity<Page<MessageDto>> getMessagesInTopic(
            @PathVariable Long topicId,
            @PageableDefault(size = 5, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.ASC) Pageable pageable) {
        Page<MessageDto> messages = messageBoardService.getMessagesInTopic(topicId, pageable);
        return ResponseEntity.ok(messages);
    }
}