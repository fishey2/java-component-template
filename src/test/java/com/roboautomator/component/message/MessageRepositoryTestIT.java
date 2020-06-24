package com.roboautomator.component.message;

import com.google.common.collect.ImmutableList;
import com.roboautomator.component.message.MessageEntity;
import com.roboautomator.component.message.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRepositoryTestIT {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    private MessageEntity messageEntity;

    @BeforeEach
    void seedTestData() {

        messageEntity = MessageEntity.builder()
                .message("Hello")
                .build();

        messageRepository.save(messageEntity);
    }

    @Test
    void shouldInjectAllComponentsCorrectly() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(messageRepository).isNotNull();
    }

    @Test
    void shouldFindEntityById() {
        var message = messageRepository.findById(messageEntity.getId());

        assertThat(message).isPresent();

        assertThat(message.get().getMessage()).isEqualTo("Hello");
    }

    @Test
    void shouldReturnOnlyOneEntityById() {
        var messages = messageRepository.findAllById(ImmutableList.of(messageEntity.getId()));

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0).getId()).isEqualTo(messageEntity.getId());
        assertThat(messages.get(0).getCreatedAt()).isNotNull();
        assertThat(messages.get(0).getUpdatedAt()).isNotNull();
    }
}
