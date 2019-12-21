package com.sonusourav.sadak.api;

import com.google.gson.annotations.SerializedName;
import com.sonusourav.sadak.dao.postDao;
import java.io.Serializable;

public class ApiResponse implements Serializable {

  @SerializedName("posts")
  private postDao[] posts;

  public postDao[] getPosts() {
    return posts;
  }

  public void setPost(postDao[] post) {
    this.posts = post;
  }
}
