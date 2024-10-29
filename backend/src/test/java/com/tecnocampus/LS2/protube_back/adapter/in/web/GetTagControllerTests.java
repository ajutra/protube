package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetTagUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetTagControllerTests {
    private MockMvc mockMvc;

    @Mock
    GetTagUseCase getTagUseCase;

    @InjectMocks
    GetTagController getTagController;

    @BeforeEach
    void setUp() throws Exception {
        try (var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(getTagController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void getTagReturnsTagCommand() throws Exception {
        String tagName = "validTag";
        GetTagCommand getTagCommand = new GetTagCommand(tagName);
        when(getTagUseCase.getTag(tagName)).thenReturn(getTagCommand);

        mockMvc.perform(get("/api/tags/{tagName}", tagName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(getTagCommand)));
    }

    @Test
    void getTagReturnsNotFoundForNonExistentTag() throws Exception {
        String tagName = "nonExistentTag";
        when(getTagUseCase.getTag(tagName)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/api/tags/{tagName}", tagName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTagReturnsErrorForBlankTagName() throws Exception {
        mockMvc.perform(get("/api/tags/{tagName}", " ")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}