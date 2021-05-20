package com.example.swp_challenge.dataController;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swp_challenge.R;

import java.util.ArrayList;

public class AchivementsAdpater extends RecyclerView.Adapter<AchivementsAdpater.ItemViewHolder>{
    //
    // adapter에 들어갈 list 입니다.
    private ArrayList<recyclerAchivementsData> listData = new ArrayList<recyclerAchivementsData>();
    public interface OnItemClickListner{
        void onItemClick(View v, int pos);
    }

    private OnItemClickListner mListener = null;
    public void setOnItemClickListener(OnItemClickListner listener){
        this.mListener = listener;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_achivement, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }
    public void updateData(ArrayList<recyclerAchivementsData> data) {
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }
    public void addItem(recyclerAchivementsData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView3;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView3 = itemView.findViewById(R.id.textview_achivements);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int pos = getAdapterPosition() ;
                        if (pos != RecyclerView.NO_POSITION) {
                            if(mListener !=null){
                                mListener.onItemClick(v, pos);
                            }
                        }
                    }

            });
        }

        void onBind(recyclerAchivementsData data) {
            textView3.setText(data.getContent());
        }
    }

}