package com.evdokimoveu.openhouse;


public class Realtor {
    private int imageId;
    private String name;
    private String switchId;
    private boolean selected;
    private String email;
    private String phone;
    private String date;
    private int id;

    public Realtor(int imageId, String name, String switchId, boolean selected, String email, String phone, String date, int id) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
        this.switchId = switchId;
        this.selected = selected;
        this.email = email;
        this.phone = phone;
        this.date = date;
    }

    public int getImageId() {
        return imageId;
    }

    public String getName() {
        return name;
    }

    public String getSwitchId() {
        return switchId;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSwitchId(String switchId) {
        this.switchId = switchId;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Realtor{" +
                "imageId=" + imageId +
                ", name='" + name + '\'' +
                ", switchId='" + switchId + '\'' +
                ", selected=" + selected +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", date='" + date + '\'' +
                ", id=" + id +
                '}';
    }
}
