package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class MessageModel {
    @SerializedName("Id")
    private  int Id;
    @SerializedName("Title")
   private String Tiltle;

    @SerializedName("Content")
  private   String Content;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTiltle() {
        return Tiltle;
    }

    public void setTiltle(String tiltle) {
        Tiltle = tiltle;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


}
