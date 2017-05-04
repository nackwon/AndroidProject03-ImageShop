package kr.hkit.jimageshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import static kr.hkit.jimageshop.ImageResources.imageIds02;

public class Sub05PhotographerActivity extends AppCompatActivity
            implements ViewSwitcher.ViewFactory{

    int photographerNumber;
    Activity mContext = this;
    TextView imageNames1;
    private ImageSwitcher mImageSwitcher;
    String photographerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub05_photographer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageNames1 = (TextView) findViewById(R.id.imageNames);

        //넘어온 인텐트를 받는 것
        Intent intent = getIntent();
        //넘어온 key값 가지고 오기
        photographerNumber = intent.getExtras().getInt("PHOTOGRAPHER_NUMBER");
        photographerName = intent.getStringExtra("PHOTOGRAPHER_NAMES");

        setTitle("'"+photographerName+"' 의 작품");

        Gallery gallery = (Gallery) findViewById(R.id.gallay);
        gallery.setAdapter(new Sub05PhotographerActivity.imageAdapter(this));



        //갤러리에서 이미지를 클릭했을 때 이미지 뷰에 붙이는 것
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Glide.with(mContext).load(imageIds02[photographerNumber][position]).
                        asBitmap().listener(new RequestListener<Integer, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, Integer model, Target<Bitmap> target,
                                                                            boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Integer model, Target<Bitmap> target,
                                                            boolean isFromMemoryCache, boolean isFirstResource) {
                        mImageSwitcher.setImageDrawable(new BitmapDrawable(getResources(), resource));
                        return true;
                    }
                }).into((ImageView)mImageSwitcher.getCurrentView());
            }
        });

        //이미지 스위쳐를 붙이고 애니매이션 효과를 주는 부분
        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(this);
        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        
        Glide.with(mContext).load(imageIds02[photographerNumber][0]).into((ImageView)mImageSwitcher.getCurrentView());
    }// end onCreate()


    //이미지 스위치에 이미지를 붙이는 메소드
    @Override
    public View makeView() {
        ImageView iv = new ImageView(this);
        iv.setBackgroundColor(0xFF000000);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT));
        return iv;
    }


    //이미지를 붙일 어댑터
    public class imageAdapter extends BaseAdapter {

        private Context context;
        private int itemBackground;

        public imageAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return imageIds02[photographerNumber].length;
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
            Glide.with(context).load(imageIds02[photographerNumber][position]).into(imageView);
            imageView.setLayoutParams(new Gallery.LayoutParams(300, 300));
            imageView.setBackgroundColor(Color.WHITE);

            return imageView;
        }
    } // end imageAdapter()

}
