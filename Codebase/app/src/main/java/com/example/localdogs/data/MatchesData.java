package com.example.localdogs.data;

public class MatchesData {
    private String matchedEmail;
    private String matchedFirstName;
    private String matchedDogName;
    private String matchedContactInfo;


    public MatchesData(String matchedEmail, String matchedFirstName, String matchedDogName, String matchedContactInfo) {
        this.matchedEmail = matchedEmail;
        this.matchedFirstName = matchedFirstName;
        this.matchedDogName = matchedDogName;
        this.matchedContactInfo = matchedContactInfo;
    }

    public MatchesData() {
        this.matchedEmail = "";
        this.matchedDogName = "";
        this.matchedContactInfo = "";
    }

    public String getMatchedEmail() {
        return matchedEmail;
    }

    public void setMatchedEmail(String matchedEmail) {
        this.matchedEmail = matchedEmail;
    }

    public String getMatchedFirstName() {
        return matchedFirstName;
    }

    public void setMatchedFirstName(String matchedFirstName) {
        this.matchedFirstName = matchedFirstName;
    }

    public String getMatchedDogName() {
        return matchedDogName;
    }

    public void setMatchedDogName(String matchedDogName) {
        this.matchedDogName = matchedDogName;
    }

    public String getMatchedContactInfo() {
        return matchedContactInfo;
    }

    public void setMatchedContactInfo(String matchedContactInfo) {
        this.matchedContactInfo = matchedContactInfo;
    }
}
