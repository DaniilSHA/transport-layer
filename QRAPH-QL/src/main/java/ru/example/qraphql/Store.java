package ru.example.qraphql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class Store {
    private static int VIDEO_ID_EMITTER = 0;
    private static int AUTHOR_ID_EMITTER = 0;


    private List<Video> videos = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();


    private int createAuthor(String firstName, String lastName) {
        log.info("createAuthor called");
        Optional<Author> findAuthor = authors.stream()
                .filter(author -> author.getFirstName().equalsIgnoreCase(firstName) && author.getLastName().equalsIgnoreCase(lastName))
                .findFirst();

        if (findAuthor.isPresent())
            return findAuthor.get().getId();

        Author author = new Author(AUTHOR_ID_EMITTER++, firstName, lastName);
        authors.add(author);
        return author.getId();
    }

    public Author readAuthor(int id) {
        log.info("readAuthor called");
        return authors.stream()
                .filter(author -> author.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Author> readAllAuthors() {
        log.info("readAllAuthors called");
        return authors;
    }

    public int createVideo(
            String title,
            List<String> tags,
            int duration,
            int likeCount,
            boolean isPublish,
            String authorFirstName,
            String authorLastName
    ) {
        log.info("createVideo called");
        int authorId = createAuthor(authorFirstName, authorLastName);

        Video video = new Video(
                VIDEO_ID_EMITTER++,
                title,
                tags,
                duration,
                likeCount,
                isPublish,
                authorId
        );

        videos.add(video);
        return video.getId();
    }

    public int updateVideo(
            int id,
            String title,
            List<String> tags,
            int duration,
            int likeCount,
            boolean isPublish,
            String authorFirstName,
            String authorLastName
    ) {
        log.info("updateVideo called");
        Optional<Video> videoOptional = videos.stream().filter(video -> video.getId() == id).findFirst();

        if (videoOptional.isEmpty())
            return -1;

        int authorId = createAuthor(authorFirstName, authorLastName);

        Video video = videoOptional.get();

        video.setTitle(title);
        video.setTags(tags);
        video.setDuration(duration);
        video.setLikeCount(likeCount);
        video.setPublish(isPublish);
        video.setAuthorId(authorId);

        return video.getId();
    }

    public Video readVideo(int id) {
        log.info("readVideo called");
        return videos.stream()
                .filter(video -> video.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Video> readAllVideos() {
        log.info("readAllVideos called");
        return videos;
    }

    public int deleteVideo(int id) {
        log.info("deleteVideo called");
        Optional<Video> videoOptional = videos.stream()
                .filter(video -> video.getId() == id)
                .findFirst();

        if (videoOptional.isEmpty())
            return -1;

        Video video = videoOptional.get();
        videos.remove(video);
        return video.getId();
    }
}
