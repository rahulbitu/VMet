package com.vmet;

class Model_newBroadcast {
    private Integer peopleImageView;
    private String peopleName,status;

    public Model_newBroadcast(Integer peopleImageView, String peopleName, String status) {
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
        return "Model_newBroadcast{" +
                "peopleImageView=" + peopleImageView +
                ", peopleName='" + peopleName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
