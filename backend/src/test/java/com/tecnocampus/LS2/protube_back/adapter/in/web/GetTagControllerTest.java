package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllTagUseCase;
import io.cucumber.java.Before;
import io.cucumber.java.sl.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetTagControllerTest {
    private MockMvc mockMvc;

    @Mock
    private GetAllTagUseCase getAllTagUseCase;

    @InjectMocks
    private GetTagController getTagController;

    @BeforeEach
    void setUp() throws Exception{
        try(var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(getTagController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
        }
    }

    @Test
    void getAllTagReturnsListOfTags() throws Exception {
        Tag tag1 = TestObjectFactory.createDummyTag("1");
        Tag tag2 = TestObjectFactory.createDummyTag("2");
        List<Tag> tags = List.of(tag1, tag2);

        List<GetTagCommand> tagCommands = tags.stream().map(GetTagCommand::from).toList();

        when(getAllTagUseCase.getAllTags()).thenReturn(tagCommands);

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                        [
                          {
                            "tagName": "Tag name 1"
                          },
                          {
                            "tagName": "Tag name 2"
                          }
                        ]
                        """
                ));

    }

    @Test
    void getAllTagsReturnsEmptyListWhenNoTags() throws Exception{
        when(getAllTagUseCase.getAllTags()).thenReturn(List.of());

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllTagsHandlesException() throws Exception {
        when(getAllTagUseCase.getAllTags()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isInternalServerError());
    }
}
