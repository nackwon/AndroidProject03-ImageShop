package kr.hkit.jimageshop;

import com.bumptech.glide.Glide;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sub05Activity extends AppCompatActivity {

    ListView list_photographer;

    Integer[] list_photographer_image={
            R.drawable.photo01, R.drawable.photo02, R.drawable.photo03,
            R.drawable.photo04, R.drawable.photo05, R.drawable.photo06,
            R.drawable.photo07, R.drawable.photo08, R.drawable.photo09,
            R.drawable.photo10
    };

    String[] list_photographer_title_string = {
            "김중만", "로버트 카파", "배병우", "빌 커닝햄", "성남훈",
            "앙리 카르티에 브레송", "얀 아르튀스 베르트랑", "유진 스미스", "제나 할러웨이", "헬무트 뉴튼"
    };

    Integer[] list_photohrapher_title = {
            R.string.string_photographer_title01,R.string.string_photographer_title02,R.string.string_photographer_title03,
            R.string.string_photographer_title04,R.string.string_photographer_title05,R.string.string_photographer_title06,
            R.string.string_photographer_title07,R.string.string_photographer_title08,R.string.string_photographer_title09,
            R.string.string_photographer_title10
    };

    Integer[] list_photographer_content = {
            R.string.string_photographer_content01, R.string.string_photographer_content02, R.string.string_photographer_content03,
            R.string.string_photographer_content04, R.string.string_photographer_content05, R.string.string_photographer_content06,
            R.string.string_photographer_content07, R.string.string_photographer_content08, R.string.string_photographer_content09,
            R.string.string_photographer_content10
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub05);

        list_photographer =(ListView) findViewById(R.id.list_photographer);
        list_photographer.setAdapter(new Sub05Activity.CustomList(this));
        list_photographer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Intent intent = new Intent(Sub05Activity.this, Sub05PhotographerActivity.class);
                //각 넘버에 맞게 그 값을 가지고 넘어간다
                intent.putExtra("PHOTOGRAPHER_NUMBER",postion);
                intent.putExtra("PHOTOGRAPHER_NAMES",list_photographer_title_string[postion]);
                startActivity(intent);
            }
        });
    }

    //리스트뷰를 붙이는 커스텀뷰 클래스
    public class CustomList extends ArrayAdapter<String> {
        private final Activity mContext;

        public CustomList(Activity context){
            super(context, R.layout.list_painter_content, list_photographer_title_string);
            this.mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_painter_content, null, true);

            ImageView imagePainter = (ImageView) rowView.findViewById(R.id.imagePainter);
            TextView textTitle = (TextView) rowView.findViewById(R.id.text_painter_title);
            TextView textContent = (TextView) rowView.findViewById(R.id.text_painter_content);

            Glide.with(mContext).load(list_photographer_image[position]).into(imagePainter);
            textTitle.setText(list_photohrapher_title[position]);
            textContent.setText(list_photographer_content[position]);

            return rowView;
        }
    }
}
