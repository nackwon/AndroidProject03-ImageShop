package kr.hkit.jimageshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sub01Activity extends AppCompatActivity {


    private  SingleTouchView drawView;
    private ImageButton currPaint;
    LinearLayout paintLayout;
    boolean isEraser = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub01);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawView = (SingleTouchView) findViewById(R.id.drawing);
        paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
    }

    //method connected Paint, Brush, eraser, save button of onClick attribute
    public void onSub01ButtonClicked(View view){
        switch (view.getId()){
            case R.id.btnNew:

                break;
            case R.id.btnBrush:
                PopupMenu popupMenu = new PopupMenu(this, view);
                popupMenu.getMenuInflater().inflate(R.menu.brush_popup, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        drawView.setStrokeWidth(Float.parseFloat(item.getTitle().toString()));
                        return true;
                    }
                });
                popupMenu.show();
                break;
            case R.id.btnEraser:
                drawView.setColor("#FFFFFFFF");
                drawView.setStrokeWidth(Float.parseFloat("50f"));
                break;
            case R.id.btnSave:
                savePhoto();
                break;
        }
    }

    // savePhoto method
    public void savePhoto(){
        drawView.setDrawingCacheEnabled(true); // cache 열고
        drawView.buildDrawingCache();

        Bitmap saveBitmap = drawView.getDrawingCache();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "MyPic"+ date + "_note.png";

        try{
            FileOutputStream fos = new FileOutputStream(new File(path, photoFile));
            saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(getApplicationContext(),"Save Image!!!",Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"failed save!!",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            drawView.setDrawingCacheEnabled(false);
        }

    }

    //method connected color button of onClick attribute
    public void onColorClicked(View view){
        if(view != currPaint){
            String color = view.getTag().toString();
            drawView.setColor(color);
            currPaint = (ImageButton) view;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
