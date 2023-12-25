package com.example.musicplayer;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class fragment1 extends Fragment {
    private View view;
    //创建歌曲的String数组和歌手图片的int数组
    public String[] name={"圣诞星——周杰伦","稻香——周杰伦","告白气球——周杰伦","兰亭序——周杰伦","蒲公英的约定——周杰伦","青花瓷——周杰伦","晴天——周杰伦"};
    public static int[] icons={R.drawable.music0,R.drawable.music1,R.drawable.music2,R.drawable.music3,R.drawable.music4,R.drawable.music5,R.drawable.music6};
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view=inflater.inflate(R.layout.list_music,null);

        ListView listView=view.findViewById(R.id.lv);

        MyBaseAdapter adapter=new MyBaseAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(fragment1.this.getContext(), MusicPlayerActivity.class);

                intent.putExtra("name",name[position]);//将歌曲名和歌曲的下标存入Intent对象
                intent.putExtra("position",String.valueOf(position));

                startActivity(intent);//开始跳转
            }
        });
        return view;
    }

    class MyBaseAdapter extends BaseAdapter{
        @Override
        public int getCount(){return  name.length;}
        @Override
        public Object getItem(int i){return name[i];}
        @Override
        public long getItemId(int i){return i;}

        @Override
        public View getView(int i ,View convertView, ViewGroup parent) {

            View view=View.inflate(fragment1.this.getContext(),R.layout.item,null);
            TextView textView=view.findViewById(R.id.item_name);
            ImageView imageView=view.findViewById(R.id.iv);
            //设置控件显示的内容，就是获取的歌曲名和歌手图片
            textView.setText(name[i]);
            imageView.setImageResource(icons[i]);
            return view;
        }
    }

}

