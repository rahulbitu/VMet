package com.vmet;

import android.widget.TextView;

class Model_newGroup {

    private Integer peopleImageView;
    private String peopleName,status;

    public Model_newGroup(Integer peopleImageView, String peopleName, String status) {
        this.peopleImageView = peopleImageView;
        this.peopleName = peopleName;
        this.status = status;
    }

    public Integer getPeopleImageView() {
        return peopleImageView;
    }

    public void setPeopleImageView(Integer peopleImageView) {
        this.peopleImageView = peopleImageView;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Model_newGroup{" +
                "peopleImageView=" + peopleImageView +
                ", peopleName='" + peopleName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
