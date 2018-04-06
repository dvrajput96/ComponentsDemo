package com.example.pc.componentsdemo.Others;

import java.sql.Blob;

/**
 * Created by pc on 12/8/17.
 */

public class Users {

    private Integer userid;
    private String Username;
    private String Password;
    private String Email;
    private String Gender;
    private String Hobbies;
    private String Birthdate;
    private String Birthtime;
    private String State;
    private String Nation;
    private String Address;
    private String seekpercentage;
    private String ratingvalue;
    private String switchvalue;
    private String mobileno;
    private byte[] blob;
    private int result = 0;

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getSeekpercentage() {
        return seekpercentage;
    }

    public void setSeekpercentage(String seekpercentage) {
        this.seekpercentage = seekpercentage;
    }

    public String getRatingvalue() {
        return ratingvalue;
    }

    public void setRatingvalue(String ratingvalue) {
        this.ratingvalue = ratingvalue;
    }

    public String getSwitchvalue() {
        return switchvalue;
    }

    public void setSwitchvalue(String switchvalue) {
        this.switchvalue = switchvalue;
    }

    public Users() {
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHobbies() {
        return Hobbies;
    }

    public void setHobbies(String hobbies) {
        Hobbies = hobbies;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getBirthtime() {
        return Birthtime;
    }

    public void setBirthtime(String birthtime) {
        Birthtime = birthtime;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        Nation = nation;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }



}
