package com.tecnocampus.LS2.protube_back.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.domain.model.Tag;
import com.tecnocampus.LS2.protube_back.port.in.command.GetTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreTagCommand;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetAllTagsUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.GetTagUseCase;
import com.tecnocampus.LS2.protube_back.port.in.useCase.StoreTagUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TagRestControllerTests {
    private MockMvc mockMvc;

    @Mock
    private GetAllTagsUseCase getAllTagsUseCase;

    @Mock
    private GetTagUseCase getTagUseCase;

    @Mock
    StoreTagUseCase storeTagUseCase;

    @InjectMocks
    private TagRestController tagRestController;

    @BeforeEach
    void setUp() throws Exception{
        try(var ignored = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(tagRestController)
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

        when(getAllTagsUseCase.getAllTags()).thenReturn(tagCommands);

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(tagCommands)));
    }

    @Test
    void getAllTagsReturnsEmptyListWhenNoTags() throws Exception{
        when(getAllTagsUseCase.getAllTags()).thenReturn(List.of());

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void getAllTagsHandlesException() throws Exception {
        when(getAllTagsUseCase.getAllTags()).thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isInternalServerError());
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

    @Test
    void storeTagHandlesResourceAlreadyExistsException() throws Exception {
        StoreTagCommand storeTagCommand = TestObjectFactory.createDummyStoreTagCommand("1");
        doThrow(new IllegalArgumentException("Tag already exists")).when(storeTagUseCase).storeTag(any());

        mockMvc.perform(post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeTagCommand)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void storeTagReturnsCreated() throws Exception {
        StoreTagCommand storeTagCommand = TestObjectFactory.createDummyStoreTagCommand("1");

        mockMvc.perform(post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(storeTagCommand)))
                .andExpect(status().isCreated());
    }
}
