package Models;

public class StoriesModel {
    String story_title;
    String stories_dis;
    String stories_hastags;
    byte[] image;
    //BLOB
    public String getStory_title() {
        return story_title;
    }

    public void setStory_title(String story_title) {
        this.story_title = story_title;
    }

    public String getStories_dis() {
        return stories_dis;
    }

    public void setStories_dis(String stories_dis) {
        this.stories_dis = stories_dis;
    }

    public String getStories_hastags() {
        return stories_hastags;
    }

    public void setStories_hastags(String stories_hastags) {
        this.stories_hastags = stories_hastags;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }







}
