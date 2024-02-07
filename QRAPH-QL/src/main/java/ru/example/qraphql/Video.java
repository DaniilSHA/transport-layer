package ru.example.qraphql;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Video {
    private int id;
    private String title;
    private List<String> tags;
    private int duration;
    private int likeCount;
    private boolean isPublish;
    private int authorId;
}
