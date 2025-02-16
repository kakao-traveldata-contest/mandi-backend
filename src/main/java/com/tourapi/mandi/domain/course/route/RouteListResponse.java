package com.tourapi.mandi.domain.course.route;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteListResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Response {

        @JsonProperty("body")
        private Body body;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Body {
            @JsonProperty("items")
            private Items items;
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Items {

            private final List<Item> items;

            @JsonCreator
            Items(@JsonProperty("item") List<Item> items) {
                this.items = items;
            }

            Iterable<Item> routes() {
                return items;
            }
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Item {
            private String gpxpath;
        }
    }
}