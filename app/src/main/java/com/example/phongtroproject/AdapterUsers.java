package com.example.phongtroproject;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {

    Context context;
    List<User> userList;

    public AdapterUsers(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }


    @androidx.annotation.NonNull
    @Override
    public MyHolder onCreateViewHolder( @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder( @androidx.annotation.NonNull MyHolder myHolder, int i) {
//        String userImage = userList.get(i).getIm
        String userName = userList.get(i).getName();
        String userMail = userList.get(i).getEmail();
        String userPhone = userList.get(i).getPhone_number();

        myHolder.mNameTv.setText(userName);
        myHolder.mEmailTv.setText(userMail);
        myHolder.mphonoeTv.setText(userPhone);
//        try {
//            Picasso
//        }

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+userMail, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNameTv, mEmailTv, mphonoeTv;

        public MyHolder( @androidx.annotation.NonNull View itemView) {
            super(itemView);

            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);
            mphonoeTv = itemView.findViewById(R.id.phoneTv);


        }
    }
}
