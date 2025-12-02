package org.moufid.statistiqueservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class YoutubeApiResponse {
    private List<Item> items;

    @Data
    public static class Item {
        private Id id;
        private Snippet snippet;
    }

    @Data
    public static class Id {
        @JsonProperty("videoId")
        private String videoId;
    }

    @Data
    public static class Snippet {
        private String title;
        private String description;
        private Thumbnails thumbnails;
    }

    @Data
    public static class Thumbnails {

        @JsonProperty("default")
        private Thumbnail defaultThumbnail;

        private Thumbnail medium;

        private Thumbnail high;
    }

    @Data
    public static class Thumbnail {
        private String url;
        private int width;
        private int height;
    }
}
