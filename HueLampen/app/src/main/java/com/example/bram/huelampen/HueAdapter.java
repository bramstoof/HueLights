package com.example.bram.huelampen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HueAdapter extends RecyclerView.Adapter<HueAdapter.Viewholder>{

    private static final String TAG = "HueAdapter";

    private ArrayList<Hue> allHueLamps;
    private Context context;
    private onItemClickListener mListener;

    public HueAdapter(ArrayList<Hue> allHueLamps, Context context) {
        this.allHueLamps = allHueLamps;
        this.context = context;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener clickListener){
        this.mListener = clickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_screen,viewGroup,false);
        Viewholder holder = new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Viewholder viewholder, final int i){
        Log.d(TAG, "onBlindViewHolder: called.");
        Hue hueLamp = allHueLamps.get(i);
        viewholder.ID.setText(Integer.toString(hueLamp.getId()));
        viewholder.LampColorImage.setColorFilter(hueLamp.getHueColor());
        if(hueLamp.isHueIsOn())
            viewholder.state.setText("ON");
        else
            viewholder.state.setText("OFF");
    }

    @Override
    public int getItemCount() {
        return allHueLamps.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView ID;
        RelativeLayout parentLayout;
        ImageView LampColorImage;
        TextView state;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.HueLamp_Lamp);
            parentLayout = itemView.findViewById(R.id.RecyclerView_MainScreen);
            LampColorImage = itemView.findViewById(R.id.ColorImage);
            state = itemView.findViewById(R.id.hueLamp_state);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(i);
                        }
                    }
                }
            });
        }
    }
}
