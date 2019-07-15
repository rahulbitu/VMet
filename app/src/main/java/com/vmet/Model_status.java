package com.vmet;

class Model_status {

    private Integer peopleProfilePic;
    private String name;

    public Model_status(Integer peopleProfilePic, String name) {
        this.peopleProfilePic = peopleProfilePic;
        this.name = name;
    }

    public Integer getPeopleProfilePic() {
        return peopleProfilePic;
    }

    public void setPeopleProfilePic(Integer peopleProfilePic) {
        this.peopleProfilePic = peopleProfilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Model_status{" +
                "peopleProfilePic=" + peopleProfilePic +
                ", name='" + name + '\'' +
                '}';
    }
}
