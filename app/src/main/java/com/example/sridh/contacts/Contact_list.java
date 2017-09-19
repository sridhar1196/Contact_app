package com.example.sridh.contacts;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Collections;

/**
 * Created by sridh on 9/16/2017.
 */

public class Contact_list implements Parcelable,Comparable<Contact_list> {
    String image_id;
    String first_name;
    String last_name;
    String company;
    String phone_no;
    String email_id;
    String url;
    String address;
    String birthday;
    String nickname;
    String fackbook_profile;
    String twitter;
    String skype;
    String youtube;

    public Contact_list(String image_id, String first_name, String last_name, String company, String phone_no, String email_id, String url, String address, String birthday, String nickname, String fackbook_profile, String twitter, String skype, String youtube) {
        super();
        this.image_id = image_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.company = company;
        this.phone_no = phone_no;
        this.email_id = email_id;
        this.url = url;
        this.address = address;
        this.birthday = birthday;
        this.nickname = nickname;
        this.fackbook_profile = fackbook_profile;
        this.twitter = twitter;
        this.skype = skype;
        this.youtube = youtube;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFackbook_profile() {
        return fackbook_profile;
    }

    public void setFackbook_profile(String fackbook_profile) {
        this.fackbook_profile = fackbook_profile;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(image_id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(company);
        dest.writeString(phone_no);
        dest.writeString(email_id);
        dest.writeString(url);
        dest.writeString(address);
        dest.writeString(birthday);
        dest.writeString(nickname);
        dest.writeString(fackbook_profile);
        dest.writeString(twitter);
        dest.writeString(skype);
        dest.writeString(youtube);
    }

    public static final Parcelable.Creator<Contact_list> CREATOR
            = new Parcelable.Creator<Contact_list>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Contact_list createFromParcel(Parcel in) {
            return new Contact_list(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Contact_list[] newArray(int size) {
            return new Contact_list[size];
        }
    };

    @Override
    public String toString() {
        return "Contact_list{" +
                "image_id='" + image_id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", company='" + company + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", email_id='" + email_id + '\'' +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", nickname='" + nickname + '\'' +
                ", fackbook_profile='" + fackbook_profile + '\'' +
                ", twitter='" + twitter + '\'' +
                ", skype='" + skype + '\'' +
                ", youtube='" + youtube + '\'' +
                '}';
    }

    private Contact_list(Parcel in){
        this.image_id = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.company = in.readString();
        this.phone_no = in.readString();
        this.email_id = in.readString();
        this.url = in.readString();
        this.address = in.readString();
        this.birthday = in.readString();
        this.nickname = in.readString();
        this.fackbook_profile = in.readString();
        this.twitter = in.readString();
        this.skype = in.readString();
        this.youtube = in.readString();
    }

    @Override
    public int compareTo(@NonNull Contact_list contact_list) {
        return this.first_name.compareTo(contact_list.getFirst_name());
    }
}
