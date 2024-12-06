package com.tecnocampus.LS2.protube_back.functional.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.functional.SpringFunctionalTesting;
import com.tecnocampus.LS2.protube_back.functional.TestContext;
import com.tecnocampus.LS2.protube_back.port.in.command.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VideoStepDefs extends SpringFunctionalTesting {
    private final TestContext testContext;
    private static String currentUser;
    private static String searchedText;
    private static String videoId;
    private static String commentId;
    private MvcResult currentUserResult;
    public VideoStepDefs(TestContext testContext) {
        this.testContext = testContext;
    }


    @Given("a user with username {string} exists")
    public void aUserWithUsernameExists(String username) {
        currentUser = username;
    }

    @When("the user uploads a video file {string} and a thumbnail {string}")
    public void theUserUploadsAVideoFileAndAThumbnailWithTitle(String videoFileName, String thumbnailFileName) throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", videoFileName, MediaType.MULTIPART_FORM_DATA_VALUE, "video content".getBytes());
        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", thumbnailFileName, MediaType.MULTIPART_FORM_DATA_VALUE, "thumbnail content".getBytes());
        StoreVideoCommand storeVideoCommand = TestObjectFactory.createDummyStoreVideoCommand("1");
        MockMultipartFile storeVideoCommandPart = new MockMultipartFile("storeVideoCommand", "", MediaType.APPLICATION_JSON_VALUE, new ObjectMapper().writeValueAsString(storeVideoCommand).getBytes());
        Files.createDirectory(Paths.get("c:"));
        currentUserResult = mockMvc.perform(multipart("/api/videos")
                .file(file)
                .file(thumbnail)
                .file(storeVideoCommandPart))
                .andReturn();

        testContext.setCurrentResult(currentUserResult);
    }

    @Given("the user {string}")
    public void theUser(String user) {
        currentUser = user;
    }

    @And("an existing video")
    public void theVideoId() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/videos"))
                .andReturn();

        videoId = currentUserResult.getResponse().getContentAsString().split("\"videoId\"\\s*:\\s*\"")[1].split("\"")[0];
    }

    @When("this user likes this video")
    public void thisUserLikesThisVideo() throws Exception {
        int likes = Integer.parseInt(currentUserResult.getResponse().getContentAsString().split("\"likes\"\\s*:\\s*")[1].split(",")[0]);

        mockMvc.perform(post("/api/users/" + currentUser + "/videos/" + videoId + "/like"))
                .andReturn();
        currentUserResult = mockMvc.perform(get("/api/videos/" + videoId))
                .andReturn();
        int newLikes = Integer.parseInt(currentUserResult.getResponse().getContentAsString().split("\"likes\"\\s*:\\s*")[1].split(",")[0]);
        assertEquals(likes + 1, newLikes);
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user dislikes this video")
    public void thisUserDislikesThisVideo() throws Exception {
        int dislikes = Integer.parseInt(currentUserResult.getResponse().getContentAsString().split("\"dislikes\"\\s*:\\s*")[1].split(",")[0]);
        mockMvc.perform(post("/api/users/" + currentUser + "/videos/" + videoId + "/dislike"))
                .andReturn();
        currentUserResult = mockMvc.perform(get("/api/videos/" + videoId))
                .andReturn();
        int newDislikes = Integer.parseInt(currentUserResult.getResponse().getContentAsString().split("\"dislikes\"\\s*:\\s*")[1].split(",")[0]);
        assertEquals(dislikes + 1, newDislikes);
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user deletes the like or dislike from this video")
    public void thisUserDeletesTheLikeOrDislikeFromThisVideo() throws Exception {

        mockMvc.perform(delete("/api/users/" + currentUser + "/videos/" + videoId + "/likes"))
                .andReturn();
        currentUserResult = mockMvc.perform(get("/api/videos/" + videoId))
                .andReturn();
        int likes = Integer.parseInt(currentUserResult.getResponse().getContentAsString().split("\"likes\"\\s*:\\s*")[1].split(",")[0]);
        int dislikes = Integer.parseInt(currentUserResult.getResponse().getContentAsString().split("\"dislikes\"\\s*:\\s*")[1].split(",")[0]);
        assertEquals(0, likes);
        assertEquals(0, dislikes);
        testContext.setCurrentResult(currentUserResult);

    }

    @When("we query for all videos")
    public void weQueryForAllVideos() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/videos"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("we query for created video by id")
    public void weQueryForCreatedVideoById() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/videos"))
                .andReturn();

        videoId = currentUserResult.getResponse().getContentAsString().split("\"videoId\"\\s*:\\s*\"")[1].split("\"")[0];
        currentUserResult = mockMvc.perform(get("/api/videos/" + videoId))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("we query for videos by username")
    public void weQueryForVideosByUsername() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/users/" + currentUser + "/videos"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("we search for videos with search term {string}")
    public void weSearchForVideosWithSearchTerm(String text) throws Exception {
        searchedText = text;
        currentUserResult = mockMvc.perform(get("/api/videos/search/" + text))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user comments on this video")
    public void thisUserCommentsOnThisVideo() throws Exception {
        StoreCommentCommand storeCommentCommand = new StoreCommentCommand(videoId, currentUser, "new comment");
        currentUserResult = mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(storeCommentCommand)))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("we query for all comments of a video")
    public void weQueryForAllCommentsOfAVideo() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/videos/" + videoId + "/comments"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("we query for all comments of this user")
    public void weQueryForAllCommentsOfThisUser() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/users/" + currentUser + "/comments"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @And("a comment")
    public void aComment() throws Exception {
        commentId = currentUserResult.getResponse().getContentAsString().split("\"commentId\"\\s*:\\s*\"")[1].split("\"")[0];
    }

    @When("we delete the comment")
    public void weDeleteTheComment() throws Exception {
        currentUserResult = mockMvc.perform(delete("/api/comments/" + commentId))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user edits the comment")
    public void thisUserEditsTheComment() throws Exception {
        currentUserResult = mockMvc.perform(patch("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "commentId":""" + "\"" + commentId + "\"," + """
                            "text": "edited comment"
                        }
                        """))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user edits the video")
    public void thisUserEditsTheVideo() throws Exception {
        currentUserResult = mockMvc.perform(patch("/api/videos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "id":""" + "\"" + videoId + "\"," + """
                            "title": "edited title"
                        }
                        """))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("we delete the video")
    public void weDeleteTheVideo() throws Exception {
        currentUserResult = mockMvc.perform(delete("/api/videos/" + videoId))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
        assertThrows(IOException.class, () -> Files.delete(Paths.get("c:", "Thumbnail_File_Name_1")));
        assertThrows(IOException.class, () -> Files.delete(Paths.get("c:", "Video_File_Name_1")));
        Files.deleteIfExists(Paths.get("c:"));
    }

    @When("we query for the acceptance ratio of this video")
    public void weQueryForTheAcceptanceRatioOfThisVideo() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/users/"+ currentUser + "/videos/" + videoId + "/like-status"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);

    }

    @Then("we ensure the result is a list of all videos")
    public void weEnsureTheResultIsAListOfAllVideos() throws Exception {
        GetVideoCommand getVideoCommand = new GetVideoCommand(videoId, 1920, 1080, 300, "Title 1", currentUser, "Video_File_Name_1", "Thumbnail_File_Name_1", 0, 0, new GetVideoCommand.Meta("Description 1", List.of(), List.of(), List.of()));
        List<GetVideoCommand> videos = List.of(getVideoCommand);
        mockMvc.perform(get("/api/videos"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(videos)));
    }

    @Then("we ensure the result is the queried video")
    public void weEnsureTheResultIsTheQueriedVideo() throws Exception {
        GetVideoCommand getVideoCommand = new GetVideoCommand(videoId, 1920, 1080, 300, "Title 1", currentUser, "Video_File_Name_1", "Thumbnail_File_Name_1", 0, 0, new GetVideoCommand.Meta("Description 1", List.of(), List.of(), List.of()));
        mockMvc.perform(get("/api/videos/" + videoId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(getVideoCommand)));
    }

    @Then("we ensure the result is a list of videos by this user")
    public void weEnsureTheResultIsAListOfVideosByThisUser() throws Exception {
        mockMvc.perform(get("/api/users/" + currentUser + "/videos"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(List.of(new GetVideoCommand(videoId, 1920, 1080, 300, "Title 1", currentUser, "Video_File_Name_1", "Thumbnail_File_Name_1", 0, 0, new GetVideoCommand.Meta("Description 1", List.of(), List.of(), List.of()))))));
    }

    @Then("we ensure the result is a list of videos with the search term")
    public void weEnsureTheResultIsAListOfVideosWithTheSearchTerm() throws Exception {
        List<SearchVideoResultCommand> videos = List.of(
                new SearchVideoResultCommand(videoId, "Title 1"));
        mockMvc.perform(get("/api/videos/search/"+ searchedText))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(videos.getFirst().title()));
    }

    @Then("we ensure the result is a list of all comments of this video")
    public void weEnsureTheResultIsAListOfAllCommentsOfThisVideo() throws Exception{
        GetCommentCommand getCommentCommand = new GetCommentCommand(videoId, commentId, currentUser, "edited comment");
        List<GetCommentCommand> comments = List.of(getCommentCommand);
        mockMvc.perform(get("/api/videos/" + videoId + "/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(comments)));
    }

    @Then("we ensure the result is a list of all comments of this user")
    public void weEnsureTheResultIsAListOfAllCommentsOfThisUser() throws Exception{
        GetCommentCommand getCommentCommand = new GetCommentCommand(videoId, commentId, currentUser, "edited comment");
        List<GetCommentCommand> comments = List.of(getCommentCommand);
        mockMvc.perform(get("/api/videos/" + videoId + "/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(comments)));
    }
}
