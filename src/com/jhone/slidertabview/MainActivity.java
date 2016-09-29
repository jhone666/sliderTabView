package com.jhone.slidertabview;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.jhone.slidertabview.SliderView.SliderOnItemClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		
		//1.找到控件
		final SliderView sliderView = (SliderView)findViewById(R.id.sliderView);

		//2.设置titles
		final ArrayList<String> titles = new ArrayList<String>();
		titles.add("专家");
		titles.add("相关研报");
		titles.add("资讯");
		sliderView.setTitles(titles);

		//3.设置点击事件
		sliderView.setOnItemClick(new SliderOnItemClick() {

			@Override
			public void onItemClick(String title) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, title, 0).show();
			}

		});

//		final Handler handler = new Handler();
//		handler.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				//可用这个方法配合viewPage或者直接响应点击事件达到切换fragment页面效果
//				sliderView.setCurrentItem(new Random().nextInt(titles.size()));
//				handler.postDelayed(this, 2000);
//			}
//		}, 3000);

    }
}
