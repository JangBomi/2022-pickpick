package com.pickpick.service;

import com.pickpick.controller.dto.SlackMessageResponse;
import com.pickpick.controller.dto.SlackMessageResponses;
import com.pickpick.entity.Message;
import com.pickpick.entity.QMessage;
import com.pickpick.exception.MessageNotFoundException;
import com.pickpick.repository.MessageRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional(readOnly = true)
@Service
public class MessageService {

    private final EntityManager entityManager;
    private final MessageRepository messageRepository;
    private JPAQueryFactory jpaQueryFactory;

    public MessageService(final EntityManager entityManager, final MessageRepository messageRepository) {
        this.entityManager = entityManager;
        this.messageRepository = messageRepository;
    }

    public SlackMessageResponses find(final String keyword, final LocalDateTime date, final Long channelId,
                                      final boolean needPastMessage, final Long messageId,
                                      final int messageCount) {

        jpaQueryFactory = new JPAQueryFactory(entityManager);

        BooleanBuilder builder = createBooleanBuilder(keyword, date, needPastMessage, messageId);

        List<Message> messages = jpaQueryFactory
                .selectFrom(QMessage.message)
                .leftJoin(QMessage.message.member)
                .fetchJoin()
                .distinct()
                .where(QMessage.message.channel.id.eq(channelId))
                .where(builder)
                .orderBy(QMessage.message.postedDate.desc())
                .limit(messageCount)
                .fetch();

        return new SlackMessageResponses(messages.stream()
                .map(SlackMessageResponse::from)
                .collect(Collectors.toList()));
    }

    //TODO https://whitepro.tistory.com/450 BooleanBuilder 리팩터링 참고
    private BooleanBuilder createBooleanBuilder(final String keyword, final LocalDateTime date,
                                                final boolean needPastMessage, final Long messageId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(keyword)) {
            builder.and(QMessage.message.text.contains(keyword));
        }

        if (Objects.nonNull(date)) {
            if (needPastMessage) {
                builder.and(
                        QMessage.message.postedDate.eq(date)
                                .or(QMessage.message.postedDate.before(date))
                );
                builder.and(QMessage.message.postedDate.before(date));
            } else {
                builder.and(
                        QMessage.message.postedDate.eq(date)
                                .or(QMessage.message.postedDate.after(date))
                );
            }
        }

        if (Objects.nonNull(messageId)) {
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(MessageNotFoundException::new);

            LocalDateTime messageDate = message.getPostedDate();

            if (needPastMessage) {
                builder.and(QMessage.message.postedDate.before(messageDate));
            } else {
                builder.and(QMessage.message.postedDate.after(messageDate));
            }
        }
        return builder;
    }
}
