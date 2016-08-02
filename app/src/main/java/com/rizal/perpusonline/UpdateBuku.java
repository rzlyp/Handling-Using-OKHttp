package com.rizal.perpusonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rizal.perpusonline.utility.OkHttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateBuku extends AppCompatActivity {
        public static String kdBuku = "";
        String url = "";
        String judulBuku;
        String pengarang;
        String penerbit;
        String tahun;
        String harga;
        String stok;

        EditText edJudul;
        EditText edPengarang;
        EditText edPenerbit;
        EditText edTahun;
        EditText edHarga;
        EditText edStok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_buku);
        kdBuku = getIntent().getExtras().getString("kode_buku");
        url = "http://bukuapi.azurewebsites.net/api/buku/"+kdBuku;
        Toast.makeText(UpdateBuku.this, "Loading " + kdBuku, Toast.LENGTH_LONG).show();
        //setupTool();

            setDeklarasi();
            getDataId();

        Button simpan = (Button)findViewById(R.id.update);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setDeklarasi();
                    setVal();
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void setupTool(){
        Toolbar tool = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Buku");


    }
    private void setVal() {
        judulBuku = edJudul.getText().toString();
        penerbit = edPenerbit.getText().toString();
        pengarang = edPengarang.getText().toString();
        tahun = edTahun.getText().toString();
        harga = edHarga.getText().toString();
        stok = edStok.getText().toString();
    }

    private void setDeklarasi() {
        edJudul = (EditText)findViewById(R.id.edJudul);
        edPengarang = (EditText)findViewById(R.id.edPengarang);
        edPenerbit = (EditText)findViewById(R.id.edPenerbit);
        edTahun = (EditText)findViewById(R.id.edTahun);
        edHarga = (EditText)findViewById(R.id.edHarga);
        edStok = (EditText)findViewById(R.id.edStok);


    }

    public void getDataId() {
                OkHttpClient ok = new OkHttpClient();
                Call call = getData(url,ok);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject object = new JSONObject(response.body().string());
                                JSONArray jArray = object.getJSONArray("result");
                                JSONObject gets = jArray.getJSONObject(0);
                                judulBuku = gets.getString("judulBuku");
                                pengarang = gets.getString("pengarang");
                                penerbit = gets.getString("penerbit");
                                tahun = gets.getString("tahun");
                                harga = gets.getString("harga");
                                stok = gets.getString("stok");

                            } catch (JSONException e) {
                                e.printStackTrace();
                               // Toast.makeText(UpdateBuku.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                            UpdateBuku.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   // setDeklarasi();
                                    edJudul.setText(judulBuku);
                                    edPengarang.setText(pengarang);
                                    edPenerbit.setText(penerbit);
                                    edTahun.setText(tahun);
                                    edHarga.setText(harga);
                                    edStok.setText(stok);
                                }
                            });
                        }
                    }
                });


                }

    public void update() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("judul_buku",judulBuku)
                .add("pengarang",pengarang)
                .add("penerbit",penerbit)
                .add("tahun",tahun)
                .add("stok",stok)
                .add("harga",harga)
                .build();

        OkHttpRequest.putDataToServer(url,requestBody).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String pesan = "";

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    pesan = jsonObject.optString("message");
                    Bundle b = new Bundle();
                    b.putString("kdBuku",kdBuku);
                    Intent i = new Intent(getBaseContext(),DetailBuku.class);
                    i.putExtras(b);
                    startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();

                }final String finalPesan = pesan;
                UpdateBuku.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),finalPesan,Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private Call getData(String url, OkHttpClient client) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request);
    }
}
