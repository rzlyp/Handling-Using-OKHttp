package com.rizal.perpusonline;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rizal.perpusonline.model.BukuModel;
import com.rizal.perpusonline.utility.OkHttpRequest;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class InputBuku extends AppCompatActivity {
     String kdBuku;
     String judulBuku;
     String pengarang;
     String penerbit;
     String tahun;
     String harga;
     String stok;
    Button simpan;
    EditText kd;
    EditText judul;
    EditText pengarangs;
    EditText penebits;
    EditText thn;
    EditText hrg;
    EditText stk;
    private final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_buku);
        setupTool();

        simpan = (Button)findViewById(R.id.tambah);


        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    deklarasi();
                    setIsi();
                    post();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });

    }



    private void deklarasi() {
        kd = (EditText)findViewById(R.id.txtKodeBuku);
        judul = (EditText)findViewById(R.id.txtJudulBuku);
        pengarangs = (EditText)findViewById(R.id.txtPengarangBk);
        penebits = (EditText)findViewById(R.id.penerbitBk);
        thn = (EditText)findViewById(R.id.txtTahunBk);
        hrg = (EditText)findViewById(R.id.hargaBk);
        stk = (EditText)findViewById(R.id.stokBk);
    }
    private void setIsi() {
        kdBuku = kd.getText().toString();
        judulBuku = judul.getText().toString();
        pengarang = pengarangs.getText().toString();
        penerbit = penebits.getText().toString();
        tahun = thn.getText().toString();
        harga = hrg.getText().toString();
        stok = stk.getText().toString();
    }

    public void post() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("kode_buku",kdBuku)
                .add("judul_buku",judulBuku)
                .add("pengarang",pengarang)
                .add("penerbit",penerbit)
                .add("tahun",tahun)
                .add("stok",stok)
                .add("harga",harga)
                .build();

        Log.d("Tahun : ",tahun);
        String url = "http://bukuapi.azurewebsites.net/api/buku/";
        try {
            OkHttpRequest.postDataToServer(url,requestBody).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("Error : ",e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String pesan = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        pesan = jsonObject.optString("message");
                        Intent i = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(i);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    final String finalPesan = pesan;
                    InputBuku.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(),finalPesan,Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void setupTool() {
        Toolbar tol = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tol);
        getSupportActionBar().setTitle("Input Buku");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
