package com.example.chatproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {
    private ArrayList<S_item> s_list;
    Context context;
    ArrayList<S_item> items;
    int pos;

    public ScheduleAdapter(){
        s_list = new ArrayList();
    }
    public ScheduleAdapter(Context context,  ArrayList<S_item> items)
    {
        this.context = context;
        this.items = items;
    }
    public int getPos(){
        return pos;
    }
    public void setPos(int position){
        this.pos = position;
    }

    public void add(String s_time, String s_content)
    {
        s_list.add(new S_item(s_time, s_content));
        notifyDataSetChanged();
    }
    public void modify(S_item item)
    {
        items.set(getPos(),item);
        notifyDataSetChanged();
    }

    public void remove(int position){
        s_list.remove(position);

        notifyDataSetChanged();
    }
    public int getCount(){
        return s_list.size();
    }
    public Object getItem(int position){
        return s_list.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent ){
        final Context context = parent.getContext();

        TextView textLeft=null;
        TextView textRight=null;


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_item,parent,false);

        }

        S_item item = s_list.get(position);

        textLeft = (TextView) convertView.findViewById(R.id.time);
        textRight = (TextView) convertView.findViewById(R.id.contents);

        textLeft.setText(item.getDate());
        textRight.setText(item.getContent());

        return convertView;
    }
}
