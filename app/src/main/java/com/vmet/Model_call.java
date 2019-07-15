package com.vmet;

class Model_call {

    private Integer peopleImage,callArrowImage,callTypeImage;
    private String peopleName,date,lastMsgTime;

    public Model_call(Integer peopleImage, Integer callArrowImage, Integer callTypeImage, String peopleName, String date, String lastMsgTime) {
        this.peopleImage = peopleImage;
        this.callArrowImage = callArrowImage;
        this.callTypeImage = callTypeImage;
        this.peopleName = peopleName;
        this.date = date;
        this.lastMsgTime = lastMsgTime;
    }

    public Integer getPeopleImage() {
        return peopleImage;
    }

    public void setPeopleImage(Integer peopleImage) {
        this.peopleImage = peopleImage;
    }

    public Integer getCallArrowImage() {
        return callArrowImage;
    }

    public void setCallArrowImage(Integer callArrowImage) {
        this.callArrowImage = callArrowImage;
    }

    public Integer getCallTypeImage() {
        return callTypeImage;
    }

    public void setCallTypeImage(Integer callTypeImage) {
        this.callTypeImage = callTypeImage;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    @Override
    public String toString() {
        return "Model_call{" +
                "peopleImage=" + peopleImage +
                ", callArrowImage=" + callArrowImage +
                ", callTypeImage=" + callTypeImage +
                ", peopleName='" + peopleName + '\'' +
                ", date='" + date + '\'' +
                ", lastMsgTime='" + lastMsgTime + '\'' +
                '}';
    }
}
