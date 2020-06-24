package com.roboautomator.component.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.google.common.collect.ImmutableList;
import com.roboautomator.component.model.HealthEntity;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HealthRepositoryTestIT {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private HealthRepository healthRepository;

    private HealthEntity healthEntity;

    @BeforeEach
    void seedTestData() {

        healthEntity = HealthEntity.builder()
            .build();

        healthRepository.save(healthEntity);
    }

    @Test
    void shouldInjectAllComponentsCorrectly() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(healthRepository).isNotNull();
    }

    @Test
    void shouldFindEntityById() {
        var message = healthRepository.findById(healthEntity.getId());

        assertThat(message).isPresent();

        assertThat(message.get().getUpdatedAt()).isEqualTo(healthEntity.getUpdatedAt());
        assertThat(message.get().getCreatedAt()).isEqualTo(healthEntity.getCreatedAt());
    }

    @Test
    void shouldReturnOnlyOneEntityById() {
        var messages = healthRepository.findAllById(ImmutableList.of(healthEntity.getId()));

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0).getId()).isEqualTo(healthEntity.getId());
        assertThat(messages.get(0).getCreatedAt()).isNotNull();
        assertThat(messages.get(0).getUpdatedAt()).isNotNull();
    }
}
