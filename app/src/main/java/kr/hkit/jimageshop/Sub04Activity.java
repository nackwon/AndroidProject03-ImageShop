package kr.hkit.jimageshop;

import com.bumptech.glide.Glide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sub04Activity extends AppCompatActivity {

    ListView list_painter;

    Integer[] list_paintre_image={
            R.drawable.painter_01_gogh, R.drawable.painter_02, R.drawable.painter_03,
            R.drawable.painter_04, R.drawable.painter_05, R.drawable.painter_06,
            R.drawable.painter_07, R.drawable.painter_08, R.drawable.painter_09,
            R.drawable.painter_10
    };

    String[] list_painter_title_string = {
            "빈센트 반 고흐", "구스타프 클림프", "램브란트", "에두아르 마네", "모네",
            "파블로 피카소", "카미유 피사로", "에드가 드가", "폴 고갱", "살바도르 달리"
    };

    Integer[] list_painter_title = {
            R.string.string_painter_title01,R.string.string_painter_title02,R.string.string_painter_title03,
            R.string.string_painter_title04,R.string.string_painter_title05,R.string.string_painter_title06,
            R.string.string_painter_title07,R.string.string_painter_title08,R.string.string_painter_title09,
            R.string.string_painter_title10
    };

    Integer[] list_painter_content = {
            R.string.string_painter_content01, R.string.string_painter_content02, R.string.string_painter_content03,
            R.string.string_painter_content04, R.string.string_painter_content05, R.string.string_painter_content06,
            R.string.string_painter_content07, R.string.string_painter_content08, R.string.string_painter_content09,
            R.string.string_painter_content10,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub04);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_painter =(ListView) findViewById(R.id.list_painter);
        list_painter.setAdapter(new CustomList(this));
        list_painter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                Intent intent = new Intent(Sub04Activity.this, Sub04PainterActivity.class);
                //각 넘버에 맞게 그 값을 가지고 넘어간다
                intent.putExtra("PAINTER_NUMBER",postion);
                intent.putExtra("PAINTER_NAME",list_painter_title_string[postion]);
                startActivity(intent);
            }
        });
    }

    //리스트뷰를 붙이는 커스텀뷰 클래스
    public class CustomList extends ArrayAdapter<String> {
        private final Activity mContext;

        public CustomList(Activity context){
            super(context, R.layout.list_painter_content, list_painter_title_string);
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

            //imagePainter.setImageResource(list_image[position]);
            Glide.with(mContext).load(list_paintre_image[position]).into(imagePainter);
            textTitle.setText(list_painter_title[position]);
            textContent.setText(list_painter_content[position]);

            return rowView;
        }
    }
}
