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
        private String videoId;
    }

    @Data
    public static class Snippet {
        private String title;
        private String description;
        private String channelTitle;
        private Thumbnails thumbnails;
    }

    @Data
    public static class Thumbnails {
        private Medium medium;
    }

    @Data
    public static class Medium {
        private String url;
    }
}
