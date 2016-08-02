package com.rizal.perpusonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rizal.perpusonline.utility.OkHttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DetailBuku extends AppCompatActivity {
    public static String kdBuku = "";
    TextView judul;
    TextView pengarangs;
    TextView penerbit;
    TextView tahun;
    Button updates;
    String juduls;
    String pengarang;
    String penerbits;
    String url = "";
    int tahuns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_buku);
        kdBuku = getIntent().getExtras().getString("kdBuku");
        url = "http://bukuapi.azurewebsites.net/api/buku/"+kdBuku;

        judul = (TextView)findViewById(R.id.judul_bukus);
        pengarangs = (TextView)findViewById(R.id.spengarang);
        penerbit = (TextView)findViewById(R.id.spenerbit);
        tahun = (TextView)findViewById(R.id.stahun);
        updates = (Button)findViewById(R.id.edit);
        prepareData();
        setupTool();

        Button hapus = (Button)findViewById(R.id.hapus);
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    hapus();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void prepareData() {
        OkHttpClient okhtpp = new OkHttpClient();
        Call call = getData(url,okhtpp);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    try {

                        JSONObject obeject = new JSONObject(response.body().string());
                        JSONArray jArray = new JSONArray(obeject.getString("result"));

                            JSONObject jsonObject = jArray.getJSONObject(0);

                                    String kdBuku = jsonObject.getString("kdBuku");
                                    juduls = jsonObject.getString("judulBuku");
                                    pengarang=jsonObject.getString("pengarang");
                                    penerbits=jsonObject.getString("penerbit");
                                    tahuns = jsonObject.getInt("harga");


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(),"gagal ambil data",Toast.LENGTH_LONG).show();
                    }
                    DetailBuku.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            judul.setText(juduls);
                            pengarangs.setText(pengarang);
                            penerbit.setText(penerbits);
                            tahun.setText(String.valueOf(tahuns));
                            updates.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle b = new Bundle();
                                    b.putString("kode_buku",kdBuku);
                                    UpdateBuku.kdBuku = kdBuku;
                                    Intent i = new Intent(getBaseContext(),UpdateBuku.class);
                                    i.putExtras(b);
                                    startActivity(i);
                                }
                            });
                        }
                    });
                }
            }
        });
    }
    private void hapus() throws IOException {
        OkHttpRequest.deleteDataToServer(url).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String pesan = "";
                try {
                    JSONObject j = new JSONObject(response.body().string());
                    pesan = j.optString("message");
                    //Toast.makeText(getBaseContext(),pesan,Toast.LENGTH_LONG).show();
                    Intent i = new Intent(DetailBuku.this,MainActivity.class);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    final String finalpesan = pesan;
                    DetailBuku.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(),finalpesan,Toast.LENGTH_LONG).show();
                        }
                    });
                }

        });
    }
    private void setupTool(){
        Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Buku");


    }
    private Call getData(String url, OkHttpClient client) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request);
    }
}
