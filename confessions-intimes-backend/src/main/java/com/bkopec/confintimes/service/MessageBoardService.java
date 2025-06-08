package com.bkopec.confintimes.service;

import com.bkopec.confintimes.dto.CreateMessageDto;
import com.bkopec.confintimes.dto.CreateTopicDto;
import com.bkopec.confintimes.dto.MessageDto;
import com.bkopec.confintimes.dto.TopicDto;
import com.bkopec.confintimes.entity.Message;
import com.bkopec.confintimes.entity.Topic;
import com.bkopec.confintimes.exception.ResourceNotFoundException;
import com.bkopec.confintimes.repository.MessageRepository;
import com.bkopec.confintimes.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageBoardService {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageBoardService(TopicRepository topicRepository, MessageRepository messageRepository) {
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
    }


    @Transactional
    public TopicDto createTopic(CreateTopicDto createDto) {
        Topic topic = new Topic(createDto.getSubject(), createDto.getPosterName());
        Topic savedTopic = topicRepository.save(topic);

        Message initialMessage = new Message(createDto.getContent(), createDto.getPosterName(), savedTopic);
        Message savedMessage = messageRepository.save(initialMessage);

        savedTopic.setLastPostAt(savedMessage.getCreatedAt());
        topicRepository.save(savedTopic);

        return mapToDto(savedTopic);
    }

    @Transactional(readOnly = true)
    public Page<TopicDto> getTopics(Pageable pageable) {
        return topicRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    @Transactional(readOnly = true)
    public TopicDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + id));
        return mapToDto(topic);
    }

    @Transactional
    public MessageDto addReply(Long topicId, CreateMessageDto createDto) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));

        Message message = new Message(createDto.getContent(), createDto.getAuthorName(), topic);
        Message savedMessage = messageRepository.save(message);

        topic.setLastPostAt(savedMessage.getCreatedAt());
        topic.setRepliesCount(topic.getRepliesCount() + 1);
        topicRepository.save(topic);

        return mapToDto(savedMessage);
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> getMessagesInTopic(Long topicId, Pageable pageable) {
        if (!topicRepository.existsById(topicId)) {
            throw new ResourceNotFoundException("Topic not found with id: " + topicId);
        }
        return messageRepository.findByTopicIdOrderByCreatedAtAsc(topicId, pageable)
                .map(this::mapToDto);
    }

    private TopicDto mapToDto(Topic topic) {
        return new TopicDto(
                topic.getId(),
                topic.getSubject(),
                topic.getPosterName(),
                topic.getCreatedAt(),
                topic.getLastPostAt(),
                topic.getRepliesCount()
        );
    }

    private MessageDto mapToDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getTopic().getId(),
                message.getContent(),
                message.getAuthorName(),
                message.getCreatedAt()
        );
    }
}