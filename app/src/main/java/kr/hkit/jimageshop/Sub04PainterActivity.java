package kr.hkit.jimageshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static kr.hkit.jimageshop.ImageResources.imageIds01;
import static kr.hkit.jimageshop.ImageResources.imageNames01;
import static kr.hkit.jimageshop.R.id.imageNames;

public class Sub04PainterActivity extends AppCompatActivity {


    int painterNumber;
    Activity mContext = this;
    TextView imageNames1;
    ImageView imageView;
    String painterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub04_painter);
        //액션바 뒤로가기(매니패스트에 적용)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageNames1 = (TextView) findViewById(imageNames);

        //넘어온 인텐트를 받는 것
        Intent intent = getIntent();
        //넘어온 key값 가지고 오기
        painterNumber = intent.getExtras().getInt("PAINTER_NUMBER");
        painterName = intent.getStringExtra("PAINTER_NAME");

        setTitle("'"+painterName+"' 의 작품");

        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new imageAdapter(this));

        //갤러리에서 이미지를 클릭했을 때 이미지 뷰에 붙이는 것
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                imageView = (ImageView) findViewById(R.id.iamgeGellery);

                Glide.with(mContext).load(imageIds01[painterNumber][position]).into(imageView);
                imageNames1.setText("<< "+imageNames01[painterNumber][position]+" >>");
            }
        });

    }// end onCreate()

    //이미지를 붙일 어댑터
    public class imageAdapter extends BaseAdapter {

        private Context context;

        public imageAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return imageIds01[painterNumber].length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        //인텐트로 넘긴 것을 갤러리에 붙이는 것
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(context);
            Glide.with(context).load(imageIds01[painterNumber][position]).into(imageView);
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 300));
            imageView.setBackgroundColor(Color.WHITE);

            return imageView;
        }
    } // end imageAdapter()
}
