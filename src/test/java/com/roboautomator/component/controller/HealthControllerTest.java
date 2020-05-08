package com.roboautomator.component.controller;

import com.roboautomator.component.MainApplication;
import com.roboautomator.component.service.health.HealthService;
import com.roboautomator.component.util.AbstractMockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@AutoConfigureMockMvc
public class HealthControllerTest extends AbstractMockMvcTest {

    private HealthService healthService;

    @BeforeEach
    void beforeEach() {

        healthService = mock(HealthService.class);
        var healthController = new HealthController(healthService);
        mockMvc = MockMvcBuilders.standaloneSetup(healthController).build();
    }

    @Test
    void checkHealthIsNotOk() throws Exception {

        doReturn(false).when(healthService).isHealthOk();

        mockMvc.perform(get("/health"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("{}"));
    }

    @Test
    void checkHealthIsOk() throws Exception {

        doReturn(true).when(healthService).isHealthOk();

        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }
}