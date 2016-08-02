package com.rizal.perpusonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rizal.perpusonline.DetailBuku;
import com.rizal.perpusonline.R;
import com.rizal.perpusonline.model.BukuModel;

import java.util.ArrayList;

/**
 * Created by Rizal Y on 7/27/2016.
 */
public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ViewHolder> {
    private ArrayList<BukuModel> listBuku = new ArrayList<>();
    Context context;
    private String kodeBuku;

    public BukuAdapter(ArrayList<BukuModel> listBuku, Context context) {
        this.listBuku = listBuku;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);

        return new ViewHolder(view);
    }
    Intent[] i;
    Bundle b = new Bundle();
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BukuModel bm = listBuku.get(position);
        kodeBuku = bm.getKdBuku();
        holder.judul.setText(bm.getJudulBuku());
        holder.pengarang.setText(bm.getPengarang());
        holder.harga.setText(String.valueOf(bm.getHarga()));

    }

    @Override
    public int getItemCount() {
        return listBuku.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView judul;
    TextView pengarang;
    TextView harga;
    Button detail;
    public ViewHolder(View itemView) {
        super(itemView);
        judul = (TextView)itemView.findViewById(R.id.judul_buku);
        pengarang = (TextView)itemView.findViewById(R.id.pengarang);
        harga = (TextView)itemView.findViewById(R.id.harga);
        detail = (Button)itemView.findViewById(R.id.detail);
        final Intent[] intent = new Intent[1];
        final Bundle b = new Bundle();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent[0] =  new Intent(view.getContext(),DetailBuku.class);
                DetailBuku.kdBuku = listBuku.get(getAdapterPosition()).getKdBuku();
                b.putString("kdBuku",listBuku.get(getAdapterPosition()).getKdBuku());
                intent[0].putExtras(b);
                context.startActivity(intent[0]);

            }
        });
    }

}

}
