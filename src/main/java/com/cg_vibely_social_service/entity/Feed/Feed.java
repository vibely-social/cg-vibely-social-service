package com.cg_vibely_social_service.entity.Feed;



import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "json",name = "feed_items")
    private String feedItem;

    public FeedItem getFeedItem() {
        return new Gson().fromJson(feedItem, FeedItem.class);
    }

    public void setFeedItem(FeedItem feedItem) {
        this.feedItem = new Gson().toJson(feedItem);
    }

//        public String getAuthorId() {
//        if (feedItem != null) {
//            JsonObject jsonObject = JsonParser.parseString(feedItem).getAsJsonObject();
//            return jsonObject.get("authorId").getAsString();
//        }
//        return null;
//    }
}
