package com.project.recipesearch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class CostumAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    ArrayList<String> event;
    ArrayList<String> link;
    ArrayList<String> ing;
    ArrayList<String> thum;
    Context context;

    public CostumAdapter(Context incomeFragment, ArrayList<String> event,ArrayList<String> link,ArrayList<String> ing,ArrayList<String> thum) {
        context = incomeFragment;
        this.event = event;
        this.link=link;
        this.ing=ing;
        this.thum=thum;
        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return event.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.incomeviewlist, null);
        Holder holder = new Holder();
        holder.tDec = (TextView) convertView.findViewById(R.id.txtLstDec);
        holder.ting=convertView.findViewById(R.id.txting);
        holder.tlink=convertView.findViewById(R.id.txtlink);
        holder.ttu=convertView.findViewById(R.id.txtthu);
        holder.tDec.setText(event.get(position));
        holder.ttu.setText(thum.get(position));
        holder.tlink.setText(link.get(position));
        holder.ting.setText(ing.get(position));

        holder.tDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc=new Intent(context,DetailsActivity.class);
                abc.putExtra("Title",holder.tDec.getText());
                abc.putExtra("href",holder.tlink.getText());
                abc.putExtra("ingredients",holder.ting.getText());
                abc.putExtra("thumbnail",holder.ttu.getText());
                abc.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(abc);
                Toast.makeText(context,holder.tDec.getText(),Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    class Holder {
        TextView tlink, ting,ttu, tDec;

    }
}
