package com.vmet;

class Model_chat {
    private Integer peopleImage;
    private String peopleName,chatting,lastMsgTime;

    public Model_chat(Integer peopleImage, String peopleName, String chatting, String lastMsgTime) {
        this.peopleImage = peopleImage;
        this.peopleName = peopleName;
        this.chatting = chatting;
        this.lastMsgTime = lastMsgTime;
    }

    public Integer getPeopleImage() {
        return peopleImage;
    }

    public void setPeopleImage(Integer peopleImage) {
        this.peopleImage = peopleImage;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getChatting() {
        return chatting;
    }

    public void setChatting(String chatting) {
        this.chatting = chatting;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    @Override
    public String toString() {
        return "Model_chat{" +
                "peopleImage=" + peopleImage +
                ", peopleName='" + peopleName + '\'' +
                ", chatting='" + chatting + '\'' +
                ", lastMsgTime='" + lastMsgTime + '\'' +
                '}';
    }
}
