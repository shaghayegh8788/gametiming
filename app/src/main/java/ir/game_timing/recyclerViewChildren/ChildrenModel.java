package ir.game_timing.recyclerViewChildren;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class ChildrenModel {

    @SerializedName("FullName")
    private String child_fullName;

    private  String Child_firstName;
    private  String Child_lastName;

    @SerializedName("CodeMelli")
    private String child_national_code;

    @SerializedName("BirthDate")
    private String child_date_born;

    @SerializedName("GenderId")
    private String child_gender;

    @SerializedName("RelationId")
    private String child_relation;

    @SerializedName("ImagePath")
    private String child_image;
    @SerializedName("age")
    private  int Child_age_int;

    public int getChild_age_int() {
        return Child_age_int;
    }

    public void setChild_age_int(int child_age_int) {
        Child_age_int = child_age_int;
    }

    private  String childAge;

    public String getChildAge() {

        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }

    private Uri local_Image_Uri;

    public String getChild_firstName() {
        return Child_firstName;
    }

    public void setChild_firstName(String child_firstName) {
        Child_firstName = child_firstName;
    }

    public String getChild_lastName() {
        return Child_lastName;
    }

    public void setChild_lastName(String child_lastName) {
        Child_lastName = child_lastName;
    }

    public String getChild_relation() {
        return child_relation;
    }

    public void setChild_relation(String child_relation) {
        this.child_relation = child_relation;
    }

    public Uri getLocal_Image_Uri() {
        return local_Image_Uri;
    }

    public void setLocal_Image_Uri(Uri local_Image_Uri) {
        this.local_Image_Uri = local_Image_Uri;
    }

    public String getChild_fullName() {
        return getChild_firstName()+" "+getChild_lastName();
    }

    public void setChild_fullName(String child_fullName) {
        this.child_fullName = child_fullName;
    }



    public String getChild_gender() {
        return child_gender;
    }

    public void setChild_gender(String child_gender) {
        this.child_gender = child_gender;
    }

    public String getChild_national_code() {
        return child_national_code;
    }

    public void setChild_national_code(String child_national_code) {
        this.child_national_code = child_national_code;
    }



    public String getChild_date_born() {
        return child_date_born;
    }

    public void setChild_date_born(String child_date_born) {
        this.child_date_born = child_date_born;
    }

    public String getChild_image() {
        return child_image;
    }

    public void setChild_image(String child_image) {
        this.child_image = child_image;
    }
}
