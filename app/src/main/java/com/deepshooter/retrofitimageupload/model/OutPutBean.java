
package com.deepshooter.retrofitimageupload.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutPutBean {

    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("_id")
    @Expose
    private Object id;
    @SerializedName("feedbackcomment")
    @Expose
    private Object feedbackcomment;
    @SerializedName("feedback")
    @Expose
    private Object feedback;
    @SerializedName("otherlocation")
    @Expose
    private Object otherlocation;
    @SerializedName("othercity")
    @Expose
    private Object othercity;
    @SerializedName("otherpincode")
    @Expose
    private Object otherpincode;
    @SerializedName("otherlandmark")
    @Expose
    private Object otherlandmark;
    @SerializedName("otheraddress")
    @Expose
    private Object otheraddress;
    @SerializedName("officelocation")
    @Expose
    private Object officelocation;
    @SerializedName("officecity")
    @Expose
    private Object officecity;
    @SerializedName("officepincode")
    @Expose
    private Object officepincode;
    @SerializedName("officelandmark")
    @Expose
    private Object officelandmark;
    @SerializedName("officeaddress")
    @Expose
    private Object officeaddress;
    @SerializedName("homelocation")
    @Expose
    private Object homelocation;
    @SerializedName("homecity")
    @Expose
    private Object homecity;
    @SerializedName("homepincode")
    @Expose
    private Object homepincode;
    @SerializedName("homelandmark")
    @Expose
    private Object homelandmark;
    @SerializedName("homeaddress")
    @Expose
    private Object homeaddress;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("profilePictureUrl")
    @Expose
    private Object profilePictureUrl;
    @SerializedName("isBlocked")
    @Expose
    private Boolean isBlocked;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("name")
    @Expose
    private Object name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OutPutBean() {
    }

    /**
     * 
     * @param homelandmark
     * @param phone
     * @param otherlocation
     * @param feedbackcomment
     * @param homecity
     * @param password
     * @param officelandmark
     * @param city
     * @param feedback
     * @param id
     * @param name
     * @param officecity
     * @param officepincode
     * @param otherlandmark
     * @param createdDate
     * @param otheraddress
     * @param isDeleted
     * @param homeaddress
     * @param isBlocked
     * @param officeaddress
     * @param officelocation
     * @param email
     * @param profilePictureUrl
     * @param othercity
     * @param homepincode
     * @param statuscode
     * @param otherpincode
     * @param homelocation
     */
    public OutPutBean(Integer statuscode, Object id, Object feedbackcomment, Object feedback, Object otherlocation, Object othercity, Object otherpincode, Object otherlandmark, Object otheraddress, Object officelocation, Object officecity, Object officepincode, Object officelandmark, Object officeaddress, Object homelocation, Object homecity, Object homepincode, Object homelandmark, Object homeaddress, Object city, Object profilePictureUrl, Boolean isBlocked, Boolean isDeleted, String createdDate, Object password, Object phone, Object email, Object name) {
        super();
        this.statuscode = statuscode;
        this.id = id;
        this.feedbackcomment = feedbackcomment;
        this.feedback = feedback;
        this.otherlocation = otherlocation;
        this.othercity = othercity;
        this.otherpincode = otherpincode;
        this.otherlandmark = otherlandmark;
        this.otheraddress = otheraddress;
        this.officelocation = officelocation;
        this.officecity = officecity;
        this.officepincode = officepincode;
        this.officelandmark = officelandmark;
        this.officeaddress = officeaddress;
        this.homelocation = homelocation;
        this.homecity = homecity;
        this.homepincode = homepincode;
        this.homelandmark = homelandmark;
        this.homeaddress = homeaddress;
        this.city = city;
        this.profilePictureUrl = profilePictureUrl;
        this.isBlocked = isBlocked;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getFeedbackcomment() {
        return feedbackcomment;
    }

    public void setFeedbackcomment(Object feedbackcomment) {
        this.feedbackcomment = feedbackcomment;
    }

    public Object getFeedback() {
        return feedback;
    }

    public void setFeedback(Object feedback) {
        this.feedback = feedback;
    }

    public Object getOtherlocation() {
        return otherlocation;
    }

    public void setOtherlocation(Object otherlocation) {
        this.otherlocation = otherlocation;
    }

    public Object getOthercity() {
        return othercity;
    }

    public void setOthercity(Object othercity) {
        this.othercity = othercity;
    }

    public Object getOtherpincode() {
        return otherpincode;
    }

    public void setOtherpincode(Object otherpincode) {
        this.otherpincode = otherpincode;
    }

    public Object getOtherlandmark() {
        return otherlandmark;
    }

    public void setOtherlandmark(Object otherlandmark) {
        this.otherlandmark = otherlandmark;
    }

    public Object getOtheraddress() {
        return otheraddress;
    }

    public void setOtheraddress(Object otheraddress) {
        this.otheraddress = otheraddress;
    }

    public Object getOfficelocation() {
        return officelocation;
    }

    public void setOfficelocation(Object officelocation) {
        this.officelocation = officelocation;
    }

    public Object getOfficecity() {
        return officecity;
    }

    public void setOfficecity(Object officecity) {
        this.officecity = officecity;
    }

    public Object getOfficepincode() {
        return officepincode;
    }

    public void setOfficepincode(Object officepincode) {
        this.officepincode = officepincode;
    }

    public Object getOfficelandmark() {
        return officelandmark;
    }

    public void setOfficelandmark(Object officelandmark) {
        this.officelandmark = officelandmark;
    }

    public Object getOfficeaddress() {
        return officeaddress;
    }

    public void setOfficeaddress(Object officeaddress) {
        this.officeaddress = officeaddress;
    }

    public Object getHomelocation() {
        return homelocation;
    }

    public void setHomelocation(Object homelocation) {
        this.homelocation = homelocation;
    }

    public Object getHomecity() {
        return homecity;
    }

    public void setHomecity(Object homecity) {
        this.homecity = homecity;
    }

    public Object getHomepincode() {
        return homepincode;
    }

    public void setHomepincode(Object homepincode) {
        this.homepincode = homepincode;
    }

    public Object getHomelandmark() {
        return homelandmark;
    }

    public void setHomelandmark(Object homelandmark) {
        this.homelandmark = homelandmark;
    }

    public Object getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(Object homeaddress) {
        this.homeaddress = homeaddress;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(Object profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

}
