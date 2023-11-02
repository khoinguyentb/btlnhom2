package com.kano.btlnhom2.DTO;

public class Phong {
    private int id;
    private String name;
    private int room_type_id;
    private int price;
    private int status;

    public Phong() {
    }

    public Phong(int id, String name, int room_type_id, int price, int status) {
        this.id = id;
        this.name = name;
        this.room_type_id = room_type_id;
        this.price = price;
        this.status = status;
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

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}



