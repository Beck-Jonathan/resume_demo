package com.beck.javaiii_kirkwood.crrg.models;

/**
 * @ author Jonathan Beck
 * @ version 1.0
 * @ since 1.0
 */

public class Picture {
  private Integer Picture_ID;
  private Integer Album_ID;
  private Integer Contributor_ID;
  private String Web_Address;
  private String description;
  private boolean Is_Active;
  private boolean is_Approved;

  public Picture(){}

  public Picture(Integer Picture_ID, Integer Album_ID, Integer Contributor_ID, String Web_Address, String description, boolean Is_Active, boolean is_Approved) {

    this.Picture_ID = Picture_ID;
    this.Album_ID = Album_ID;
    this.Contributor_ID = Contributor_ID;
    this.Web_Address = Web_Address;
    this.description = description;
    this.Is_Active = Is_Active;
    this.is_Approved = is_Approved;
  }

  public Picture(Integer Picture_ID) {

    this.Picture_ID = Picture_ID;
  }
  public Integer getPicture_ID() {
    return Picture_ID;
  }
  public void setPicture_ID(Integer Picture_ID) {
    this.Picture_ID = Picture_ID;
  }
  public Integer getAlbum_ID() {
    return Album_ID;
  }
  public void setAlbum_ID(Integer Album_ID) {
    this.Album_ID = Album_ID;
  }
  public Integer getContributor_ID() {
    return Contributor_ID;
  }
  public void setContributor_ID(Integer Contributor_ID) {
    this.Contributor_ID = Contributor_ID;
  }
  public String getWeb_Address() {
    return Web_Address;
  }
  public void setWeb_Address(String Web_Address) {
    Web_Address = Web_Address.replaceAll("^[A-Za-z0-9]+^/.*_","");
    if(Web_Address.length()<4){
      throw new IllegalArgumentException("Web_Address is too short.");
    }
    if(Web_Address.length()>255){
      throw new IllegalArgumentException("Web_Address is too long.");
    }
    this.Web_Address = Web_Address;
  }
  public String getdescription() {
    return description;
  }
  public void setdescription(String description) {
    description = description.replaceAll("[^.\"'A-Za-z0-9 - ]","");
    if(description.length()<4&&!description.isEmpty()){
      throw new IllegalArgumentException("description is too short.");
    }
    if(description.length()>100){
      throw new IllegalArgumentException("description is too long.");
    }
    this.description = description;
  }
  public boolean getIs_Active() {
    return Is_Active;
  }
  public void setIs_Active(boolean Is_Active) {
    this.Is_Active = Is_Active;
  }
  public boolean getis_Approved() {
    return is_Approved;
  }
  public void setis_Approved(boolean is_Approved) {
    this.is_Approved = is_Approved;
  }

}

