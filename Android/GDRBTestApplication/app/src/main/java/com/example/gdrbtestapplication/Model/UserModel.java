package com.example.gdrbtestapplication.Model;

import android.os.Parcelable;

import java.io.Serializable;

public class UserModel implements Serializable {
    String status;
    String message;
    String token;
    String userId;
    String firstName;
    String lastName;
    String email;
    String phone;
    String blocked;
    String deactivated;
    String approved;
    String userProfile;
    String country;
    String state;
    String city;
    String packageName;
    String packageValidity;
    String remainingChat;
    String remainingInterest;
    String remainingContactView;
    String autoProfileMatch;
    String profileHighlight;
    String imageUrl;

    public UserModel(String status, String message, String token, String userId, String firstName, String lastName, String email, String phone, String blocked, String deactivated, String approved, String userProfile, String country, String state, String city, String packageName, String packageValidity, String remainingChat, String remainingInterest, String remainingContactView, String autoProfileMatch, String profileHighlight, String imageUrl) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.blocked = blocked;
        this.deactivated = deactivated;
        this.approved = approved;
        this.userProfile = userProfile;
        this.country = country;
        this.state = state;
        this.city = city;
        this.packageName = packageName;
        this.packageValidity = packageValidity;
        this.remainingChat = remainingChat;
        this.remainingInterest = remainingInterest;
        this.remainingContactView = remainingContactView;
        this.autoProfileMatch = autoProfileMatch;
        this.profileHighlight = profileHighlight;
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBlocked() {
        return blocked;
    }

    public String getDeactivated() {
        return deactivated;
    }

    public String getApproved() {
        return approved;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageValidity() {
        return packageValidity;
    }

    public String getRemainingChat() {
        return remainingChat;
    }

    public String getRemainingInterest() {
        return remainingInterest;
    }

    public String getRemainingContactView() {
        return remainingContactView;
    }

    public String getAutoProfileMatch() {
        return autoProfileMatch;
    }

    public String getProfileHighlight() {
        return profileHighlight;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
