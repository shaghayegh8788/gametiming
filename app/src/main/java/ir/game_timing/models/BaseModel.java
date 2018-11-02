package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class BaseModel {

    @SerializedName("Id")
    public  int Id;


    @SerializedName("Path")
    public  String Path;

    @SerializedName("Title")
    public  String Title;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
