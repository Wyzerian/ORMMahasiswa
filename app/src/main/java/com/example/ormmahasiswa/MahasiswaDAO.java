package com.example.ormmahasiswa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface MahasiswaDAO {
    @Insert
    public void addMahasiswa(Mahasiswa mahasiswa);

    @Update
    public void updateMahasiswa(Mahasiswa mahasiswa);

    @Delete
    public void deleteMahasiswa(Mahasiswa mahasiswa);

    @Query("select * from mahasiswa")
    public List<Mahasiswa> getAllMahasiswa();

    @Query("select * from mahasiswa where mahasiswa_id==:mahasiswa_id")
    public Mahasiswa getMahasiswa(int mahasiswa_id);

}
