package com.example.forkifydemoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context context;
    private ArrayList<ExampleItem> exampleItems;

    public ExampleAdapter(Context context,ArrayList<ExampleItem> exampleItems){
        Log.d("shubham",exampleItems.toString());
        this.exampleItems=exampleItems;
        this.context=context;

    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewId;
        public TextView textViewEmail;

        public ImageButton editImage,deleteImage;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewEmail = itemView.findViewById(R.id.text_view_mail_id);
            textViewId = itemView.findViewById(R.id.text_view_id);
            editImage = itemView.findViewById(R.id.editImage);
            deleteImage = itemView.findViewById(R.id.deleteImage);
        }
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.example_item,parent,false);
        return new ExampleViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = exampleItems.get(position);

        String imageUrl =currentItem.getImageUrl();
        String name =currentItem.getName();
        String mail =currentItem.getMail();
        final int id = currentItem.getId();

        holder.textViewName.setText("Name - "+name);
        holder.textViewEmail.setText("Mail ID - "+mail);
        holder.textViewId.setText("Id - "+id);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.imageView);

        holder.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editUserDetail(currentItem);
            }
        });

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserDetail(id);
            }
        });

    }

    private void editUserDetail(ExampleItem currentItem) {

        LayoutInflater factory = LayoutInflater.from(context);
        final View deleteDialogView = factory.inflate(R.layout.edit_dialog, null);
        final AlertDialog editDialog = new AlertDialog.Builder(context).create();
        editDialog.setView(deleteDialogView);
        TextView id= deleteDialogView.findViewById(R.id.text_view_id);
        EditText Name= deleteDialogView.findViewById(R.id.editTextName);
        EditText Mail= deleteDialogView.findViewById(R.id.editTextMail);
        id.setText(String.valueOf(currentItem.getId()));
        Name.setText(currentItem.getName());
        Mail.setText(currentItem.getMail());

        deleteDialogView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

        deleteDialogView.findViewById(R.id.updateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"we are working ",Toast.LENGTH_LONG).show();
            }
        });

        editDialog.show();

    }

    private void deleteUserDetail(int id) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure, You wanted to delete");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                RequestQueue requstQueue = Volley.newRequestQueue(context);

                                String url = "https://reqres.in/api/users/"+String.valueOf(id);
                                StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                                        new Response.Listener<String>()
                                        {
                                            @Override
                                            public void onResponse(String response) {
                                                // response
                                                Toast.makeText(context, "Successfully delete", Toast.LENGTH_LONG).show();
                                            }
                                        },
                                        new Response.ErrorListener()
                                        {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // error.
                                                Log.d("shuubham","delete error");

                                            }
                                        }
                                );
                                requstQueue.add(dr);

                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return exampleItems.size();
    }

}
