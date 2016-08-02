package com.rizal.perpusonline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rizal.perpusonline.adapter.BukuAdapter;
import com.rizal.perpusonline.model.BukuModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListBukuFragment extends Fragment {

    private static final String KODE_BUKU = "kdBuku";
    private RecyclerView rBuku;
    private SwipeRefreshLayout rf;
    private BukuAdapter bukuAdapter;
    private ArrayList<BukuModel> listBuku = new ArrayList<>();
    public static ListBukuFragment newInstance(){
        return new ListBukuFragment();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list_buku, container, false);
        prepareData();
        rBuku = (RecyclerView)view.findViewById(R.id.recycler);
        rBuku.setHasFixedSize(true);
        rBuku.setLayoutManager(new LinearLayoutManager(getContext()));
        rBuku.setItemAnimator(new DefaultItemAnimator());
        bukuAdapter = new BukuAdapter(listBuku,getContext());
        rBuku.setAdapter(bukuAdapter);
        FloatingActionButton ib= (FloatingActionButton)view.findViewById(R.id.inputBuku);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),InputBuku.class);
                startActivity(i);
            }
        });
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);

        toolbar.setTitle("Buku");


        return view;
    }
    private void setRecyclerView(View v) {

        //refresh();
    }



    private void prepareData() {
        OkHttpClient okhtpp = new OkHttpClient();
        Call call = getData("http://bukuapi.azurewebsites.net/api/buku",okhtpp);
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

                        for (int i = 0;i<jArray.length();i++){
                            JSONObject jsonObject = jArray.getJSONObject(i);
                            BukuModel bm = new BukuModel(
                                    jsonObject.getString("kdBuku"),
                                    jsonObject.getString("judulBuku"),
                                    jsonObject.getString("pengarang"),
                                    jsonObject.getString("penerbit"),
                                    jsonObject.getInt("harga"));

                            listBuku.add(bm);


                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                bukuAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),"gagal ambil data",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private Call getData(String url, OkHttpClient client) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        return client.newCall(request);
    }


}
