package com.roboautomator.component.controller;

import com.roboautomator.component.MainApplication;
import com.roboautomator.component.controller.HealthController;
import com.roboautomator.component.service.HealthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc
public class HealthControllerTest {

    private HealthService healthService;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {

        healthService = mock(HealthService.class);
        HealthController healthController = new HealthController(healthService);
        mockMvc = MockMvcBuilders.standaloneSetup(healthController).build();
    }

    @Test
    public void checkHealthIsNotOk() throws Exception {

        doReturn(false).when(healthService).isHealthOk();

        mockMvc.perform(get("/health"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("{}"));
    }

    @Test
    public void checkHealthIsOk() throws Exception {

        doReturn(true).when(healthService).isHealthOk();

        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("{}"));
    }
}