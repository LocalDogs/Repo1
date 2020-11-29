package com.example.localdogs.data;

public class MatchesData {
    private String matchedEmail;
    private String matchedDogName;
    private String matchedContactInfo;


    public MatchesData(String matchedEmail, String matchedDogName, String matchedContactInfo) {
        this.matchedEmail = matchedEmail;
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
