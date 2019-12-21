package com.sonusourav.sadak.User;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserClass implements Serializable {
    private String name;
    private String email;
    private String pass;
    private String gender;
    private String phone;
    private String fcmToken;
    private String profilePic;
    private String coverPic;
    private int level;

    public UserClass(){}

  public UserClass(String userEmail, String uname, String userPic) {

    this.email = userEmail;
    this.name = uname;
    this.profilePic = userPic;
  }

    public UserClass(String userEmail) {

        email = userEmail;
        pass = "fa31b7bcb9e0d9ad4ab7e94e0230f2af7";
        name = "";
        phone = "";
        gender = "";
        profilePic="";
        coverPic="";
    }

  public UserClass(String uname, String userPhone,String fcmToken, String userGender) {
        name = uname;
        phone = userPhone;
        gender = userGender;
        this.fcmToken = fcmToken;
    }

  public UserClass(String userEmail, String uname,int level,
      String userPhone, String fcmToken, String userGender,
      String userPic, String userCoverPic) {
        email = userEmail;
        name = uname;
        this.level=level;
        phone = userPhone;
        gender = userGender;
        profilePic=userPic;
        coverPic=userCoverPic;
        this.fcmToken = fcmToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public String getFcmToken() {
    return fcmToken;
  }

  public void setFcmToken(String fcmToken) {
    this.fcmToken = fcmToken;
  }
}
