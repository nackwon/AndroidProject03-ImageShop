package kr.hkit.jimageshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Sub06Activity extends AppCompatActivity {

    Spinner spinner;
    ImageView imageMuseum;
    TextView textMuseum;
    Activity mContext = this;
    int museum_Position;

    //박물관 이미지
    Integer[] imageMuseumIds = {
            R.drawable.museum01, R.drawable.museum02, R.drawable.museum03,
            R.drawable.museum04, R.drawable.museum05, R.drawable.museum06,
            R.drawable.museum07, R.drawable.museum08, R.drawable.museum09,
            R.drawable.museum10
    };

    //박물관 설명
    Integer[] textMuseumIds = {
            R.string.museum_content01, R.string.museum_content02, R.string.museum_content03,
            R.string.museum_content04, R.string.museum_content05, R.string.museum_content06,
            R.string.museum_content07, R.string.museum_content08, R.string.museum_content09,
            R.string.museum_content10
    };

    //박물관 웹 사이트
    String[] museumWebAddress = {
            "http://www.metmuseum.org/",
            "http://www.gardnermuseum.org/home",
            "http://www.tate.org.uk",
            "http://www.louvre.fr",
            "http://www.christusrex.org/www1/vaticano/0-Musei.html",
            "http://www.centrepompidou.fr",
            "http://www.rembrandthuis.nl",
            "http://www.vangoghmuseum.nl",
            "http://www.museumsnett.no/munchmuseet/",
            "http://www.nm.cz/"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub06);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("미술관");

        spinner = (Spinner) findViewById(R.id.spinner);
        imageMuseum = (ImageView) findViewById(R.id.imageMuseum);
        textMuseum = (TextView) findViewById(R.id.textMuseum);


        Glide.with(mContext).load(imageMuseumIds[0]).into(imageMuseum);

        spinner.setBackgroundColor(Color.GRAY);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.museum_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

                Glide.with(mContext).load(imageMuseumIds[position]).into(imageMuseum);
                textMuseum.setText(textMuseumIds[position]);
                museum_Position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Toast.makeText(getApplicationContext(),"아래의 미술관 아이콘을 클릭하시면\n웹페이지로 이동합니다.",
                Toast.LENGTH_SHORT).show();
    }// end onCreate()


    // 미술관 아이콘 클릭했을 때
    public void onMuseumWebClicked(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(museumWebAddress[museum_Position]));
        startActivity(intent);
    }
}
