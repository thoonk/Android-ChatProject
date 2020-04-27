package com.example.chatproject;

import android.content.Context;
import android.media.Image;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<CustomAdapter.ListContents> m_List = new ArrayList();

    public class ListContents{
        String userName;
        String userId;
        String msg;
        int type;
        String time;
        ListContents(String _userName, String _userId, String _msg,int _type,String _time)
        {
            this.userName = _userName;
            this.userId = _userId;
            this.msg = _msg;
            this.type = _type;
            this.time = _time;
        }
    }

    public CustomAdapter() {
        m_List = new ArrayList();
    }
    public void add(String _userName,String _userId ,String _msg,int _type,String _time) {
        m_List.add(new ListContents(_userName, _userId, _msg,_type,_time));
    }

    public void remove(int _position) {
        m_List.remove(_position);
    }
    public int getCount() {
        return m_List.size();
    }
    public Object getItem(int position) {
        return m_List.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView text = null;
        CustomHolder holder = null;
        LinearLayout layout = null;
        View viewRight = null;
        View viewLeft = null;
        TextView stime = null;
        TextView gtime = null;
        ImageView limage = null;
        ImageView rimage = null;
        TextView sName = null;
        TextView gName = null;

        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_chatitem, parent, false);
            layout = (LinearLayout) convertView.findViewById(R.id.layout);
            text = (TextView) convertView.findViewById(R.id.text);
            viewRight = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft = (View) convertView.findViewById(R.id.imageViewleft);
            stime = (TextView)convertView.findViewById(R.id.sendTime);
            gtime = (TextView)convertView.findViewById(R.id.getTime);
            limage = (ImageView)convertView.findViewById(R.id.leftImage);
            rimage = (ImageView)convertView.findViewById(R.id.rightImage);
            sName = (TextView)convertView.findViewById(R.id.sName);
            gName = (TextView)convertView.findViewById(R.id.gName);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_TextView = text;
            holder.layout = layout;
            holder.viewRight = viewRight;
            holder.viewLeft = viewLeft;
            holder.stime = stime;
            holder.gtime = gtime;
            holder.limage = limage;
            holder.rimage = rimage;
            holder.sName = sName;
            holder.gName = gName;
            convertView.setTag(holder);
        }
        else {
            holder = (CustomHolder) convertView.getTag();
            text = holder.m_TextView;
            layout = holder.layout;
            viewRight = holder.viewRight;
            viewLeft = holder.viewLeft;
            stime = holder.stime;
            gtime = holder.gtime;
            limage = holder.limage;
            rimage = holder.rimage;
            sName = holder.sName;
            gName = holder.gName;
        }

        // Text 등록
        text.setText(m_List.get(position).msg);
        stime.setText(m_List.get(position).time);
        gtime.setText(m_List.get(position).time);
        sName.setText(m_List.get(position).userId);
        gName.setText(m_List.get(position).userId);

         if( m_List.get(position).type == 0 ) {
            text.setBackgroundResource(R.drawable.inbox2);
            layout.setGravity(Gravity.LEFT);
            viewRight.setVisibility(View.GONE);
            viewLeft.setVisibility(View.GONE);
            stime.setVisibility(View.GONE);
            gtime.setVisibility(View.VISIBLE);
            limage.setVisibility(View.VISIBLE);
            rimage.setVisibility(View.GONE);
            sName.setVisibility(View.VISIBLE);
            gName.setVisibility(View.GONE);
       }else if(m_List.get(position).type == 1) {
             text.setBackgroundResource(R.drawable.outbox2);
             layout.setGravity(Gravity.RIGHT);
             viewRight.setVisibility(View.GONE);
             viewLeft.setVisibility(View.GONE);
             stime.setVisibility(View.VISIBLE);
             gtime.setVisibility(View.GONE);
             limage.setVisibility(View.GONE);
             rimage.setVisibility(View.VISIBLE);
             sName.setVisibility(View.GONE);
             gName.setVisibility(View.VISIBLE);
         }
        /*}else if(m_List.get(position).type == 2){
            text.setBackgroundResource(R.drawable.datebg);
            layout.setGravity(Gravity.CENTER);
            viewRight.setVisibility(View.VISIBLE);
            viewLeft.setVisibility(View.VISIBLE);
            stime.setVisibility(View.VISIBLE);
            gtime.setVisibility(View.VISIBLE);
            limage.setVisibility(View.VISIBLE);
            rimage.setVisibility(View.VISIBLE);
            sName.setVisibility(View.VISIBLE);
            gName.setVisibility(View.VISIBLE);
        }*/


        // 리스트 아이템을 터치 했을 때 이벤트 발생
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(context, "리스트 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
            }
        });

        // 리스트 아이템을 길게 터치 했을때 이벤트 발생
        convertView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                // 터치 시 해당 아이템 이름 출력
                Toast.makeText(context, "리스트 롱 클릭 : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return convertView;
    }

    private class CustomHolder {
        TextView m_TextView;
        LinearLayout layout;
        View viewRight;
        View viewLeft;
        TextView stime;
        TextView gtime;
        ImageView limage;
        ImageView rimage;
        TextView gName;
        TextView sName;
    }
}

