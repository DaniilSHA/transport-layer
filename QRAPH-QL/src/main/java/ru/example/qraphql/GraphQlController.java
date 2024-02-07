package ru.example.qraphql;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class GraphQlController {

    @Autowired
    private Store store;

    @QueryMapping
    public Video readVideo(@Argument int id) {
        return store.readVideo(id);
    }

    @QueryMapping
    public List<Video> readAllVideo() {
        return store.readAllVideos();
    }

    @MutationMapping
    public int createVideo(
            @Argument String title,
            @Argument List<String> tags,
            @Argument int duration,
            @Argument int likeCount,
            @Argument boolean isPublish,
            @Argument String authorFirstName,
            @Argument String authorLastName
    ) {
        return store.createVideo(
                title,
                tags,
                duration,
                likeCount,
                isPublish,
                authorFirstName,
                authorLastName
        );
    }

    @MutationMapping
    public int updateVideo(
            @Argument int id,
            @Argument String title,
            @Argument List<String> tags,
            @Argument int duration,
            @Argument int likeCount,
            @Argument boolean isPublish,
            @Argument String authorFirstName,
            @Argument String authorLastName
    ) {
        return store.updateVideo(
                id,
                title,
                tags,
                duration,
                likeCount,
                isPublish,
                authorFirstName,
                authorLastName
        );
    }

    @MutationMapping
    public int deleteVideo(@Argument int id) {
        return store.deleteVideo(id);
    }

//    @SchemaMapping
//    public Author author(Video video) {
//        return store.readAuthor(video.getAuthorId());
//    }

    @BatchMapping
    public Mono<Map<Video, Author>> author(List<Video> videos) {
        List<Author> authors = store.readAllAuthors();
        Map<Integer, Author> authorMap = authors.stream().collect(Collectors.toMap(Author::getId, author -> author));
        Map<Video, Author> videoAuthorMap = videos.stream().collect(Collectors.toMap(video -> video, video -> authorMap.get(video.getAuthorId())));
        return Mono.just(videoAuthorMap);
    }

    @SubscriptionMapping
    public Flux<Video> like() {
        return Flux.fromStream(Stream.generate(() -> 1))
                .delayElements(Duration.ofSeconds(2))
                .map(integer -> {
                    List<Video> videos = store.readAllVideos();
                    if (videos.isEmpty())
                        return Optional.<Video>empty();
                    else
                    {
                        Video video = videos.get(getRandomNumber(0, videos.size() - 1));
                        video.setLikeCount(video.getLikeCount() + 1);
                        return Optional.of(video);
                    }

                })
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
