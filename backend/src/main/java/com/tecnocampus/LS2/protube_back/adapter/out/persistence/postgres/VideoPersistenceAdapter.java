package com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres;

import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.CategoryMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.TagMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.mapper.VideoMapper;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.CategoryJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.TagJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.UserJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.jpaEntity.VideoJpaEntity;
import com.tecnocampus.LS2.protube_back.adapter.out.persistence.postgres.repository.VideoRepository;
import com.tecnocampus.LS2.protube_back.domain.model.*;
import com.tecnocampus.LS2.protube_back.port.out.DeleteVideoPort;
import com.tecnocampus.LS2.protube_back.port.out.GetVideoPort;
import com.tecnocampus.LS2.protube_back.port.out.StoreVideoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Qualifier("postgresVideoPort")
public class VideoPersistenceAdapter implements GetVideoPort, StoreVideoPort, DeleteVideoPort {
    private final VideoRepository videoRepository;
    private final UserPersistenceAdapter userPersistenceAdapter;
    private final VideoMapper videoMapper;
    private final TagMapper tagMapper;
    private final CategoryMapper categoryMapper;
    private final CommentPersistenceAdapter commentPersistenceAdapter;

    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::toDomain)
                .toList();
    }

    @Override
    public Video getVideoByTitleAndUsername(String title, String username) {
        return videoRepository.findByTitleAndUserUsername(title, username)
                .map(videoMapper::toDomain)
                .orElseThrow(() -> new NoSuchElementException("Video not found"));
    }

    @Override
    public void storeVideo(Video video, Set<Tag> tags, Set<Category> categories) {
        storeAndGetVideo(video, tags, categories);
    }

    @Override
    public Video storeAndGetVideo(Video video, Set<Tag> tags, Set<Category> categories) {
        UserJpaEntity userJpaEntity = userPersistenceAdapter.findByUsername(video.getUsername());
        Set<TagJpaEntity> tagsJpa = tags.stream().map(tagMapper::toJpaEntity).collect(Collectors.toSet());
        Set<CategoryJpaEntity> categoriesJpa = categories.stream().map(categoryMapper::toJpaEntity).collect(Collectors.toSet());

        // We assume that the video doesn't exist and the user exists, as it's checked in the service
        VideoJpaEntity videoJpaEntity = videoMapper.toJpaEntity(video, userJpaEntity, tagsJpa, categoriesJpa);
        videoRepository.save(videoJpaEntity);

        return videoMapper.toDomain(videoJpaEntity);
    }

    @Override
    public void checkIfVideoExists(String videoId) {
        if (videoRepository.findById(videoId).isEmpty()) {
            throw new NoSuchElementException("Video not found with ID: " + videoId);
        }
    }

    private PlayerPageVideo getVideoWithFields(VideoJpaEntity videoJpaEntity, Set<Field> fields) {
        Video video = videoMapper.toDomain(videoJpaEntity);

        List<Tag> tags = fields.contains(Field.TAGS) ?
                videoJpaEntity.getTags().stream()
                        .map(tagMapper::toDomain)
                        .toList() :
                List.of();

        List<Category> categories = fields.contains(Field.CATEGORIES) ?
                videoJpaEntity.getCategories().stream()
                        .map(categoryMapper::toDomain)
                        .toList() :
                List.of();

        List<Comment> comments = fields.contains(Field.COMMENTS) ?
                commentPersistenceAdapter.getAllCommentsByVideo(videoJpaEntity) :
                List.of();

        return PlayerPageVideo.from(video, tags, categories, comments);
    }

    @Override
    public PlayerPageVideo getVideoWithFieldsById(String id, Set<Field> fields) {
        VideoJpaEntity videoJpaEntity = videoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Video not found with ID: " + id));

        return getVideoWithFields(videoJpaEntity, fields);

    }

    @Override
    @Transactional
    public void deleteVideo(String videoId) {
        VideoJpaEntity video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NoSuchElementException("Video not found with ID: " + videoId));

        commentPersistenceAdapter.deleteAllCommentsByVideo(video);

        videoRepository.deleteById(videoId);
    }

    @Override
    public List<PlayerPageVideo> getAllVideosWithFieldsByUsername(String username, Set<Field> fields) {
        return videoRepository.findAllByUserUsername(username)
                .stream()
                .map(video -> getVideoWithFields(video, fields))
                .toList();
    }

    @Override
    public List<PlayerPageVideo> getAllVideosWithFields(Set<Field> fields) {
        return videoRepository.findAll()
                .stream()
                .map(video -> getVideoWithFields(video, fields))
                .toList();
    }

    VideoJpaEntity findById(String id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Video not found with ID: " + id));
    }

  @Override
  public void editVideo(Video video) {
      VideoJpaEntity videoJpaEntity = videoRepository.findById(video.getId())
              .orElseThrow(() -> new NoSuchElementException("Video not found with ID: " + video.getId()));

      videoJpaEntity.setTitle(video.getTitle());
      videoJpaEntity.setDescription(video.getDescription());

      videoRepository.save(videoJpaEntity);
    }
}
