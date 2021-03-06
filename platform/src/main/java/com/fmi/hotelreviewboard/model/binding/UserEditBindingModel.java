package com.fmi.hotelreviewboard.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserEditBindingModel {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private MultipartFile profilePictureFile;

    public UserEditBindingModel() {
    }

    @NotEmpty(message = "{com.fmi.hotelreviewboard.validation.Field.empty}")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty(message = "{com.fmi.hotelreviewboard.validation.Field.empty}")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Pattern(regexp = "[0-9]{0,10}", message = "{com.fmi.hotelreviewboard.validation.PhoneNumber.default}")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public MultipartFile getProfilePictureFile() {
        return profilePictureFile;
    }

    public void setProfilePictureFile(MultipartFile profilePictureFile) {
        this.profilePictureFile = profilePictureFile;
    }
}
