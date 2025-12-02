package org.moufid.statistiqueservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YouTubeVideo {

    private String videoId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String channelTitle;
}