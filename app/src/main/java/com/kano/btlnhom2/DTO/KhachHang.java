package com.kano.btlnhom2.DTO;

public class KhachHang {
    private int id ;
    private String name ;
    private String phone ;
    private String birthday ;

    public KhachHang() {
    }

    public KhachHang(int id, String name, String phone, String birthday) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
