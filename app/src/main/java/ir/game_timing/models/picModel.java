package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class picModel {

    public picModel(int id, String name, String path) {
        Id = id;
        Name = name;
        Path = path;
    }

    @SerializedName("Id")
    private  int Id;

    @SerializedName("Name")
    private  String Name;

    @SerializedName("Path")
    private  String Path;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}
