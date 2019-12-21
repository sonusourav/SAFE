package com.sonusourav.sadak.dao;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class postDao implements Serializable {

  @SerializedName("date")
  private String date;
  @SerializedName("by")
  private String by;
  @SerializedName("docTitle")
  private String caption;
  @SerializedName("profileUrl")
  private String profileUrl;
  @SerializedName("roadPic")
  private String roadPic;
  @SerializedName("length")
  private String length;
  @SerializedName("location")
  private String location;
  @SerializedName("commentsList")
  private int commentsList;
  @SerializedName("comments")
  private CommentsDao[] comments;
  @SerializedName("__v")
  private String __v;
  @SerializedName("upvotes")
  private int upvotes;
  @SerializedName("downvotes")
  private  int downvotes;
  @SerializedName("_id")
  private String _id;
  @SerializedName("file")
  private String file;
  @SerializedName("width")
  private String width;
  @SerializedName("material")
  private String material;
  @SerializedName("_ids")
  private String[] ids;

  public postDao(String date, String by, String caption, String profileUrl, String roadPic,
      String length, String location, int commentsList, CommentsDao[] comments, String __v,
      int upvotes,int downvotes, String _id, String file, String width, String material, String[] ids) {
    this.date = date;
    this.by = by;
    this.caption = caption;
    this.profileUrl = profileUrl;
    this.roadPic = roadPic;
    this.length = length;
    this.location = location;
    this.commentsList = commentsList;
    this.comments = comments;
    this.__v = __v;
    this.upvotes = upvotes;
    this.downvotes = downvotes;
    this._id = _id;
    this.file = file;
    this.width = width;
    this.material = material;
    this.ids = ids;
  }

  public String[] getIds() {
    return ids;
  }

  public void setIds(String[] ids) {
    this.ids = ids;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getMaterial() {
    return material;
  }

  public void setMaterial(String material) {
    this.material = material;
  }

  public postDao() {
  }

  public String get__v() {
    return __v;
  }

  public void set__v(String __v) {
    this.__v = __v;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getBy() {
    return by;
  }

  public void setBy(String by) {
    this.by = by;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String desc) {
    this.caption = desc;
  }

  public String getProfileUrl() {
    return profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public String getRoadPic() {
    return roadPic;
  }

  public void setRoadPic(String roadPic) {
    this.roadPic = roadPic;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public CommentsDao[] getComments() {
    return comments;
  }

  public void setComments(CommentsDao[] comments) {
    this.comments = comments;
  }

  public int getUpvotes() {
    return upvotes;
  }

  public void setUpvotes(int upvotes) {
    this.upvotes = upvotes;
  }

  public int getDownvotes() {
    return downvotes;
  }

  public void setDownvotes(int downvotes) {
    this.downvotes = downvotes;
  }

  public int getCommentsList() {
    return commentsList;
  }

  public void setCommentsList(int commentsList) {
    this.commentsList = commentsList;
  }
}
