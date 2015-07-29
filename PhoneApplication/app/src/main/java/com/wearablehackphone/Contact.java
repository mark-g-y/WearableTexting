package com.wearablehackphone;

/**
 * Created by Mark on 13/12/2014.
 */
public class Contact {

    private String displayName;
    private String phoneNumber;
    private String organization;

    public Contact (String displayName, String phoneNumber) {
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
    }

    public Contact (String displayName, String phoneNumber, String organization) {
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
        this.organization = organization;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOrganization() {
        return organization;
    }
}
