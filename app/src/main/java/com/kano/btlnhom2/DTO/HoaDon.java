package com.kano.btlnhom2.DTO;

public class HoaDon {
    private int id;
    private String manager_id;
    private int guest_id;
    private int room_id;
    private String start_date,end_date;
    private int status;
    private String note;
    private String bill_date;
    private int lost_total,service_total,room_total,bill_total;

    public HoaDon() {
    }

    public HoaDon(int id, String manager_id, int guest_id, int room_id, String start_date, String end_date, int status, String note, String bill_date, int lost_total, int service_total, int room_total, int bill_total) {
        this.id = id;
        this.manager_id = manager_id;
        this.guest_id = guest_id;
        this.room_id = room_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.note = note;
        this.bill_date = bill_date;
        this.lost_total = lost_total;
        this.service_total = service_total;
        this.room_total = room_total;
        this.bill_total = bill_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public int getLost_total() {
        return lost_total;
    }

    public void setLost_total(int lost_total) {
        this.lost_total = lost_total;
    }

    public int getService_total() {
        return service_total;
    }

    public void setService_total(int service_total) {
        this.service_total = service_total;
    }

    public int getBill_total() {
        return bill_total;
    }

    public void setBill_total(int bill_total) {
        this.bill_total = bill_total;
    }

    public int getRoom_total() {
        return room_total;
    }

    public void setRoom_total(int room_total) {
        this.room_total = room_total;
    }
}
