package com.sonusourav.sadak.dao;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CommentsDao implements Serializable {

  @SerializedName("comment")
  private String comment;
  @SerializedName("by")
  private String by;
  @SerializedName("date")
  private String date;
  @SerializedName("url")
  private String profileUrl;
  @SerializedName("_id")
  private String _id;
  @SerializedName("email")
  private String email;

  public CommentsDao(String comment, String by, String date,
      String profileUrl, String _id, String email) {
    this.comment = comment;
    this.by = by;
    this.date = date;
    this.profileUrl = profileUrl;
    this._id = _id;
    this.email = email;
  }

  public CommentsDao() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getBy() {
    return by;
  }

  public void setBy(String by) {
    this.by = by;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getProfileUrl() {
    return profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }
}
