package com.sonusourav.sadak.dao;

import com.google.gson.annotations.SerializedName;

public class postContractor {

  @SerializedName("docTitle")
  private String docTitle;
  @SerializedName("document")
  private String document;
  @SerializedName("length")
  private String length;
  @SerializedName("width")
  private String width;
  @SerializedName("material")
  private String material;
  @SerializedName("location")
  private String location;

  public postContractor(String docTitle,String length, String width,
      String material, String location ,String document) {
    this.docTitle = docTitle;
    this.document = document;
    this.length = length;
    this.width = width;
    this.material = material;
    this.location = location;
  }

  public postContractor(String docTitle, String length, String width,
      String material, String location) {
    this.docTitle = docTitle;
    this.length = length;
    this.width = width;
    this.material = material;
    this.location = location;
  }


  public postContractor() {
  }

  public String getDocTitle() {
    return docTitle;
  }

  public void setDocTitle(String docTitle) {
    this.docTitle = docTitle;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
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

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
