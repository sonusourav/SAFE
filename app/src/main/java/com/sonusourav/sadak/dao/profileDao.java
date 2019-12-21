package com.sonusourav.sadak.dao;

public class profileDao {
  String profilePic,CoverPic,UserName,topBatch;
  int batches;
  int reputation;

  public profileDao(String profilePic, String coverPic, String userName, String topBatch,
      int batches, int reputation) {
    this.profilePic = profilePic;
    CoverPic = coverPic;
    UserName = userName;
    this.topBatch = topBatch;
    this.batches = batches;
    this.reputation = reputation;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public String getCoverPic() {
    return CoverPic;
  }

  public void setCoverPic(String coverPic) {
    CoverPic = coverPic;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getTopBatch() {
    return topBatch;
  }

  public void setTopBatch(String topBatch) {
    this.topBatch = topBatch;
  }

  public int getBatches() {
    return batches;
  }

  public void setBatches(int batches) {
    this.batches = batches;
  }

  public int getReputation() {
    return reputation;
  }

  public void setReputation(int reputation) {
    this.reputation = reputation;
  }
}
