package com.example.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class toDoAdapter extends RecyclerView.Adapter{

    private final Context ctx;
    private ArrayList<toDo> ToDo;
    public toDoAdapter(Context ctx, ArrayList<toDo> ToDo) {
        this.ctx = ctx;
        this.ToDo = ToDo;
    }

    class VHToDo extends RecyclerView.ViewHolder {
        private TextView tvKegiatan;
        private TextView tvWaktu;

        public VHToDo(View rowView) {
            super(rowView); // rowview: keseluruhan row
            this.tvKegiatan = rowView.findViewById(R.id.tvKegiatan);
            this.tvWaktu = rowView.findViewById(R.id.tvWaktu);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx).inflate(R.layout.card, parent, false);
        return new VHToDo(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VHToDo vh = (VHToDo) holder; //mengambil keranjangnya dan disimpan dalam variabel vh
        toDo m = ToDo.get(position); // sudah tau keranjang dan objek yang ada di posisi kemudian dibinding
        vh.tvWaktu.setText(" : "+m.waktu);
        vh.tvKegiatan.setText(m.kegiatan);
    }

    @Override
    public int getItemCount() {
        return ToDo.size();
    }
}
