package com.example.lphver3;

public class Task {
    private int _Id;
    private String NOIDUNG;
    private String NGAY;
    private String GIO;
    private int TINHTRANG;

    public Task(int _Id, String NOIDUNG, String NGAY, String GIO, int TINHTRANG) {
        this._Id = _Id;
        this.NOIDUNG = NOIDUNG;
        this.NGAY = NGAY;
        this.GIO = GIO;
        this.TINHTRANG = TINHTRANG;
    }

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }

    public String getNOIDUNG() {
        return NOIDUNG;
    }

    public void setNOIDUNG(String NOIDUNG) {
        this.NOIDUNG = NOIDUNG;
    }

    public String getNGAY() {
        return NGAY;
    }

    public void setNGAY(String NGAY) {
        this.NGAY = NGAY;
    }

    public String getGIO() {
        return GIO;
    }

    public void setGIO(String GIO) {
        this.GIO = GIO;
    }

    public int getTINHTRANG() {
        return TINHTRANG;
    }

    public void setTINHTRANG(int TINHTRANG) {
        this.TINHTRANG = TINHTRANG;
    }
}
