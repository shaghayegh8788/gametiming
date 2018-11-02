package ir.game_timing.models;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class ChildModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("ParentId")
    private int ParentId;

    @SerializedName("FullName")
    private String FullName;

    @SerializedName("GenderId")
    private int GenderId;

    @SerializedName("Relation")
    private int Relation;

    @SerializedName("BirthDate")
    private String BirthDate;

    @SerializedName("NationalCode")
    private String NationalCode;

    @SerializedName("ImagePath")
    private String ImagePath;

    private Uri local_Image_Uri;

    public Uri getLocal_Image_Uri() {
        return local_Image_Uri;
    }

    public void setLocal_Image_Uri(Uri local_Image_Uri) {
        this.local_Image_Uri = local_Image_Uri;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getRelation() {
        return Relation;
    }

    public void setRelation(int relation) {
        Relation = relation;
    }

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
        GenderId = genderId;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getNationalCode() {
        return NationalCode;
    }

    public void setNationalCode(String nationalCode) {
        NationalCode = nationalCode;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
