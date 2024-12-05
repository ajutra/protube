package com.tecnocampus.LS2.protube_back.domain.model;

import com.tecnocampus.LS2.protube_back.port.in.command.StoreVideoCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Video {
        private String id;
        private int width;
        private int height;
        private int duration;
        private String title;
        private String description;
        private String username;
        private String videoFileName;
        private String thumbnailFileName;
        private int likes;
        private int dislikes;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Video video = (Video) o;

                return width == video.width
                        && height == video.height
                        && duration == video.duration
                        && title.equals(video.title)
                        && username.equals(video.username);
        }

        public static Video from(StoreVideoCommand storeVideoCommand, User user) {
                Video video = new Video();
                video.setWidth(storeVideoCommand.width());
                video.setHeight(storeVideoCommand.height());
                video.setDuration(storeVideoCommand.duration());
                video.setTitle(storeVideoCommand.title());
                video.setUsername(user.username());

                video.setDescription(
                        storeVideoCommand.description() == null ?
                                "" :
                                storeVideoCommand.description());

                video.setVideoFileName(
                        storeVideoCommand.videoFileName() == null ?
                                storeVideoCommand.title() + user.username() + ".mp4" :
                                storeVideoCommand.videoFileName());

                video.setThumbnailFileName(
                        storeVideoCommand.thumbnailFileName() == null ?
                                storeVideoCommand.title() + user.username() + ".webp" :
                                storeVideoCommand.thumbnailFileName());

                return video;
        }
}
