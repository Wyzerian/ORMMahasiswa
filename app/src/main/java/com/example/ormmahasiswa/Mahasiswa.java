package com.example.ormmahasiswa;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Mahasiswa")
public class Mahasiswa {

    @ColumnInfo (name = "mahasiswa_id")
            @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo (name = "nama")
    String nama;

    @ColumnInfo (name = "nim")
    String nim;

    @ColumnInfo (name = "alamat")
    String alamat;

    @ColumnInfo (name = "asal_sekolah")
    String asalSekolah;

    @Ignore
    public Mahasiswa (){

    }

    public Mahasiswa(String nama, String nim, String alamat, String asalSekolah) {
        this.nama = nama;
        this.nim = nim;
        this.alamat = alamat;
        this.asalSekolah = asalSekolah;
        this.id = 0;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAsalSekolah() {
        return asalSekolah;
    }

    public void setAsalSekolah(String asalSekolah) {
        this.asalSekolah = asalSekolah;
    }
}

