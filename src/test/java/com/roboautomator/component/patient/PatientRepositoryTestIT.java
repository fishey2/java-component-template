package com.roboautomator.component.patient;

import static org.assertj.core.api.Assertions.assertThat;
import com.google.common.collect.ImmutableList;
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
class PatientRepositoryTestIT {

    private static final String PATIENT_NUMBER = "1234567890";

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    private PatientEntity patientEntity;

    @BeforeEach
    void seedTestData() {

        patientEntity = PatientEntity.builder().number(PATIENT_NUMBER).build();

        patientRepository.save(patientEntity);
    }

    @Test
    void shouldInjectAllComponentsCorrectly() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(patientRepository).isNotNull();
    }

    @Test
    void shouldFindEntityById() {
        var patient = patientRepository.findById(patientEntity.getId());

        assertThat(patient).isPresent();

        assertThat(patient.get().getId()).isEqualTo(patientEntity.getId());
    }

    @Test
    void shouldReturnOnlyOneEntityById() {
        var messages = patientRepository.findAllById(ImmutableList.of(patientEntity.getId()));

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0).getNumber()).isEqualTo(PATIENT_NUMBER);
        assertThat(messages.get(0).getCreatedAt()).isNotNull();
        assertThat(messages.get(0).getUpdatedAt()).isNotNull();
    }
}
