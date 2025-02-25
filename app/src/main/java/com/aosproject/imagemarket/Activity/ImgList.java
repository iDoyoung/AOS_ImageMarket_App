package com.aosproject.imagemarket.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aosproject.imagemarket.Adapter.ImgListAdapter;
import com.aosproject.imagemarket.Bean.ImgListBean;
import com.aosproject.imagemarket.NetworkTask.NetworkTaskImgList;
import com.aosproject.imagemarket.R;

import java.util.ArrayList;

import static com.aosproject.imagemarket.Util.ShareVar.loginEmail;
import static com.aosproject.imagemarket.Util.ShareVar.macIP;

public class ImgList extends Activity {

    String urlAddr = null;
    ArrayList<ImgListBean> imglist;
    ImgListAdapter adapter;

    ListView profile_lv_imglist_list;
    ImageView profile_iv_imglist_back;
    LinearLayout profile_layout_imglist_noitem, profile_layout_imglist_yesitem, profile_layout_imglist_additem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_list);

        profile_iv_imglist_back = findViewById(R.id.profile_iv_imglist_back);
        profile_lv_imglist_list = findViewById(R.id.profile_lv_imglist_list);
        profile_layout_imglist_noitem = findViewById(R.id.profile_layout_imglist_noitem);
        profile_layout_imglist_yesitem = findViewById(R.id.profile_layout_imglist_yesitem);
        profile_layout_imglist_additem = findViewById(R.id.profile_layout_imglist_additem);

        urlAddr = macIP + "jsp/profile_imglist.jsp?loginEmail=" + loginEmail;

        profile_iv_imglist_back.setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        connectGetData();
    }

    private void connectGetData() {
        try {

            NetworkTaskImgList networkTask = new NetworkTaskImgList(ImgList.this, urlAddr);
            Object obj = networkTask.execute().get();
            imglist = (ArrayList<ImgListBean>) obj;

            if(imglist.size() == 0) {
                profile_layout_imglist_noitem.setVisibility(View.VISIBLE);
                profile_layout_imglist_yesitem.setVisibility(View.INVISIBLE);
                profile_layout_imglist_additem.setOnClickListener(onClickListener);
            }else {
                profile_layout_imglist_noitem.setVisibility(View.INVISIBLE);
                profile_layout_imglist_yesitem.setVisibility(View.VISIBLE);

                adapter = new ImgListAdapter(ImgList.this, R.layout.imglist_innerlist, imglist);
                profile_lv_imglist_list.setAdapter(adapter);
                profile_lv_imglist_list.setOnItemClickListener(onItemClickListener);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profile_iv_imglist_back:
                    Intent intent = new Intent(ImgList.this, MainActivity.class);
                    intent.putExtra("imgList", 4);
                    startActivity(intent);
                    break;
                case R.id.profile_layout_imglist_additem:
                    intent = new Intent(ImgList.this, ImageAddImageActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 혜지언니 판매 상품 상세 페이지
            Log.v("Chk", "ImgList_onItemClickListener start");
            Intent intent = new Intent(ImgList.this, ImageEditDeleteActivity.class);
            intent.putExtra("code", imglist.get(position).getImageCode());
            startActivity(intent);
            Log.v("Chk", "ImgList_onItemClickListener end");
        }
    };
}