package com.example.quizodyssey.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizodyssey.Activities.MainActivity;
import com.example.quizodyssey.R;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder>{
    Context context;
    String[] data;

    public OptionAdapter(Context context, String[] data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionViewHolder(LayoutInflater.from(context).inflate(R.layout.options, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        holder.button.setText(Html.fromHtml(data[position], Html.FROM_HTML_MODE_LEGACY));
        holder.button.setChecked(MainActivity.selected ==position);
        if (MainActivity.selected!=position){
            holder.layout.setBackgroundResource(R.drawable.option_not_selected);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout.setBackgroundResource(R.drawable.option_selcted);
                MainActivity.selected = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public String selectedText(){
        return data[MainActivity.selected];
    }
    public class OptionViewHolder extends RecyclerView.ViewHolder{
        RadioButton button;
        LinearLayout layout;
        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.button);
            layout=itemView.findViewById(R.id.layout);
        }
    }
}
