package kr.hkit.jimageshop;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int DIALOG_YES_NO = 1;

    String[] potion_menu_url = {
           "https://github.com/bumptech/glide",
            "http://www.nationalgeographic.com/",
            "http://www.pulitzer.org/",
            "http://royalwine.net/museum/museum.htm"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    //옵션 메뉴
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.glide_site:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(potion_menu_url[0]));
                startActivity(intent);
                return true;
            case R.id.nationalGeographic_site:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(potion_menu_url[1]));
                startActivity(intent);
                return true;
            case R.id.pulitzerPrizes_site:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(potion_menu_url[2]));
                startActivity(intent);
                return true;
            case R.id.worldMuseum_site:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(potion_menu_url[3]));
                startActivity(intent);
                return true;
            case R.id.infomation:
                intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // back키를 눌렀을 때 대화 상자 호출 메소드
    @Override
    public void onBackPressed() {
        showDialog(DIALOG_YES_NO);
    }

    //  종료 대화 상자
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_YES_NO:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("종료 확인 대화 상자")
                        .setMessage("애플리케이션을 종료하시겠습니까?")
                        .setIcon(R.mipmap.ic_dialog)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                return alertDialog;
        }
        return null;
    }

    // MainActivity of Button onClick 속성으로 연결된 메소드
    public void onMainButtonClicked(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.btnCanvas:
                intent = new Intent(MainActivity.this, Sub01Activity.class);
                break;
            case R.id.btnConvert:
                intent = new Intent(MainActivity.this, Sub02Activity.class);
                break;
            case R.id.btnConvertGlide:
                intent = new Intent(MainActivity.this, Sub03Activity.class);
                break;
            case R.id.btnMasterPiece:
                intent = new Intent(MainActivity.this, Sub04Activity.class);
                break;
            case R.id.btnPicture:
                intent = new Intent(MainActivity.this, Sub05Activity.class);
                break;
            case R.id.btnMuseum:
                intent = new Intent(MainActivity.this, Sub06Activity.class);
                break;
        }
        if(intent != null){
            startActivity(intent);
        }
    }
}
