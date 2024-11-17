package com.example.ormmahasiswa;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    EditText namaEdit,NIMedit,Alamatedit,AsalSekolahedit;
    Button btnSave,btnGet;
    TextView textViewDisplay;

    MahasiswaDatabase mahasiswaDB;

    List<Mahasiswa> mahasiswaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        namaEdit = findViewById(R.id.editNama);
        NIMedit = findViewById(R.id.editNIM);
        Alamatedit = findViewById(R.id.editAlamat);
        AsalSekolahedit = findViewById(R.id.editAsalSekolah);

        btnSave = findViewById(R.id.buttonSave);
        btnGet = findViewById(R.id.buttonGet);

        textViewDisplay = findViewById(R.id.textView);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        mahasiswaDB = Room.databaseBuilder(getApplicationContext(),MahasiswaDatabase.class,"MahasiswaDB").addCallback(myCallBack).build();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = namaEdit.getText().toString();
                String nim = NIMedit.getText().toString();
                String alamat = Alamatedit.getText().toString();
                String asal_sekolah = AsalSekolahedit.getText().toString();

                Mahasiswa m1 = new Mahasiswa(nama,nim,alamat,asal_sekolah);

                addMahasiswaInBackground(m1);

            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getMahasiswaListInBackground();


            }
        });
    }

    public void addMahasiswaInBackground(Mahasiswa mahasiswa){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mahasiswaDB.getMahasiswaDAO().addMahasiswa(mahasiswa);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Added to Database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getMahasiswaListInBackground(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                mahasiswaList = mahasiswaDB.getMahasiswaDAO().getAllMahasiswa();

                //on finishing task
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        for (Mahasiswa m : mahasiswaList){
                            sb.append("Nama: ").append(m.getNama()).append("\n")
                            .append("NIM: ").append(m.getNim()).append("\n")
                            .append("Alamat: ").append(m.getAlamat()).append("\n")
                            .append("Asal Sekolah: ").append(m.getAsalSekolah()).append("\n\n");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textViewDisplay.setText(sb.toString());
                            }
                        });
                    }
                });
            }
        });
    }
}