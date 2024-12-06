package com.tecnocampus.LS2.protube_back.functional.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnocampus.LS2.protube_back.TestObjectFactory;
import com.tecnocampus.LS2.protube_back.functional.SpringFunctionalTesting;
import com.tecnocampus.LS2.protube_back.functional.TestContext;
import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class VideoStepDefs extends SpringFunctionalTesting {
    private final TestContext testContext;
    private String currentUser;
    private String videoId;
    private String commentId;
    private MvcResult currentUserResult;
    public VideoStepDefs(TestContext testContext) {
        this.testContext = testContext;
    }


    @Given("a user with username {string} exists")
    public void aUserWithUsernameExists(String username) {
        this.currentUser = username;
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
        this.currentUser = user;
    }

    @And("an existing video")
    public void theVideoId() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/videos"))
                .andReturn();

        videoId = currentUserResult.getResponse().getContentAsString().split("\"videoId\"\\s*:\\s*\"")[1].split("\"")[0];
    }

    @When("this user likes this video")
    public void thisUserLikesThisVideo() throws Exception {
        currentUserResult =
                mockMvc.perform(post("/api/users/" + currentUser + "/videos/" + videoId + "/like"))
                        .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user dislikes this video")
    public void thisUserDislikesThisVideo() throws Exception {
        currentUserResult =
                mockMvc.perform(post("/api/users/" + currentUser + "/videos/" + videoId + "/dislike"))
                        .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user deletes the like or dislike from this video")
    public void thisUserDeletesTheLikeOrDislikeFromThisVideo() throws Exception {
        currentUserResult =
                mockMvc.perform(delete("/api/users/" + currentUser + "/videos/" + videoId + "/likes"))
                        .andReturn();
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
        currentUserResult = mockMvc.perform(get("/api/videos/search/" + text))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);
    }

    @When("this user comments on this video")
    public void thisUserCommentsOnThisVideo() throws Exception {
        String json = """
                {
                    "videoId":""" + "\"" + videoId + "\"," + """
                    "username":""" + "\"" + currentUser + "\"," + """
                    "text":"new comment"
                }
                
                """;
        currentUserResult = mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
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
        Files.deleteIfExists(Paths.get("c:", "Thumbnail_File_Name_1"));
        Files.deleteIfExists(Paths.get("c:", "Video_File_Name_1"));
        Files.deleteIfExists(Paths.get("c:"));
    }

    @When("we query for the acceptance ratio of this video")
    public void weQueryForTheAcceptanceRatioOfThisVideo() throws Exception {
        currentUserResult = mockMvc.perform(get("/api/users/"+ currentUser + "/videos/" + videoId + "/like-status"))
                .andReturn();
        testContext.setCurrentResult(currentUserResult);

    }
}
