package kr.hkit.jimageshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sub02Activity extends AppCompatActivity {

    public static final int SELECT_IMAGE = 0;
    private float mAngle = 0.0F;
    private float mScaleX = 0.5F;
    private float mScaleY = 0.5F;
    private float mBrightScale = 0.1F;
    private SampleView sampleView;
    ColorMatrix cm = new ColorMatrix();
    LinearLayout imageLayout;
    private Bitmap mBitmap; // 전역변수로 뺐다 사진 upload를 위해

    boolean isSaturation = false, isRed = false; //이미지 색(gray, red) 반전
    boolean isReverse = false; // 영상 반전을 위한 변수
    boolean isHorizontalMatrix = false; // 좌우 반전을 위한 변수
    boolean isVerticalMatrix = false; // 상하 반전을 위한 변수

    int cNum = 1; // 색상 변수
    int bNum = 1; // 밝기 변수

    int hNum = 1; // 좌우반전 변수
    int vNum = 1; // 상하반전 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub02);
        imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        sampleView = new SampleView(this);
        imageLayout.addView(sampleView);
    }

    //이미지 버튼의 onClick속성으로 연결된 메소드
    public void onSub02ButtonClicked(View view) {
        switch (view.getId()) {

            //위쪽 이미지
            // plus button
            case R.id.btnPlus:
                mScaleX += 0.01f;
                mScaleY += 0.01f;
                sampleView.invalidate();
                return;
            // minus button
            case R.id.btnMinus:
                if (mScaleX < 0 && mScaleY < 0) {
                    return;
                } else {
                    mScaleX -= 0.01f;
                    mScaleY -= 0.01f;
                    sampleView.invalidate();
                }
                return;
            // rotateright button
            case R.id.btnRotateRight:
                mAngle += 20;
                sampleView.invalidate();
                return;
            // rotateleft button
            case R.id.btnRotateLeft:
                mAngle -= 20;
                sampleView.invalidate();
                return;
            //reserve
            case R.id.btnReverseHorizental:
                isHorizontalMatrix = true;
                hNum = -hNum;
                sampleView.invalidate();
                return;

            case R.id.btnReverseVertical:
                isVerticalMatrix = true;
                vNum = -vNum;
                sampleView.invalidate();
                return;

            case R.id.btnReset:
                mBrightScale = 1.0f;
                mAngle = 0.0f;
                mScaleX = 0.5f;
                mScaleY = 0.5f;
                isHorizontalMatrix = false;
                isVerticalMatrix = false;
                hNum = 1;
                vNum = 1;
                setColorScale(cm, mBrightScale);
                isRed = false;
                isReverse = false;
                sampleView.invalidate();
                return;

            //아래쪽 이미지
            case R.id.btnBrightUp:
                mBrightScale += 0.1f;
                setColorScale(cm, mBrightScale);
                sampleView.invalidate();
                break;

            case R.id.btnBrightDown:
                mBrightScale -= 0.1f;
                setColorScale(cm, mBrightScale);
                sampleView.invalidate();
                return;

            case R.id.btnGrayTone:
                isSaturation = !isSaturation;
                if (isSaturation)
                    cm.setSaturation(0); // 이런 메소드가 있기에 이렇게 사용
                else
                    cm.setSaturation(1);
                sampleView.invalidate();
                return;

            case R.id.btnRedTone:
                isRed = !isRed;
                sampleView.invalidate();
                return;

            case R.id.btnReverse:
                isReverse = !isReverse;
                if (isReverse) {
                    cNum = -1;
                    bNum = 255;
                } else {
                    cNum = 1;
                    bNum = 0;
                }
                setColorScale(cm, mBrightScale);
                sampleView.invalidate();
                return;

            case R.id.btnOpen:
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(intent, SELECT_IMAGE);
                return;

            case R.id.btnSave:
                savePhoto();
                return;
        }

    }

    //사진 save 해주는 메소드
    public void savePhoto() {
        imageLayout.setDrawingCacheEnabled(true);
        imageLayout.buildDrawingCache();

        Bitmap saveBitmap = imageLayout.getDrawingCache();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "MyPic" + date + "_change.png";

        try {
            FileOutputStream fos = new FileOutputStream(new File(path, photoFile));
            saveBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Toast.makeText(getApplicationContext(), "Image saved!!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Image save failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            imageLayout.setDrawingCacheEnabled(false);
        }
    }

    //사진 upload 해주는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK && requestCode == SELECT_IMAGE) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mBitmap = bitmap; // bitmap으로 받은 것을 전역변수로 지정한 mBitmap(smapleView가 붙어있는)에 붙이면 문제 해결
            mScaleX = 0.5f;
            mScaleY = 0.5f;
            sampleView.invalidate();
        }
    }

    // 색상을 정해주는 메소드
    public void setColorScale(ColorMatrix cm, float scale) {
        cm.set(new float[]{
                scale * cNum, 0, 0, 0, 0 + bNum,
                0, scale * cNum, 0, 0, 0 + bNum,
                0, 0, scale * cNum, 0, 0 + bNum,
                0, 0, 0, 1, 0});
    }

    //그림을 그려주는 커스텀 뷰
    public class SampleView extends View {
        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public SampleView(Context context) {
            super(context);
            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image01);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint = mPaint;

            //red 톤 설정
            if (isRed)
                paint.setColorFilter(new LightingColorFilter(0xff0080, 0x000050)); // 안드로이드에서 지원하는 메소드
            else
                paint.setColorFilter(new ColorMatrixColorFilter(cm));

            //전체지점에서 사진크기만큼 빼고 거기에 절반
            // canvas의 높이, 넓이     //사진의 크기
            float x = (this.getWidth() - mBitmap.getWidth()) / 2;
            float y = (this.getHeight() - mBitmap.getHeight()) / 2;

            // x, y 좌표를 구해서 중간에 위치 시킨다 중간 점을 찍는 것
            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;

            canvas.drawColor(Color.WHITE);
            canvas.scale(mScaleX, mScaleY, centerX, centerY);
            canvas.rotate(mAngle, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);

            //좌우 반전
            if (isHorizontalMatrix) {
                Matrix horizontalMatrix = new Matrix();
                horizontalMatrix.setScale(hNum, vNum); // 1이면 정상 - 이면 반전
                Bitmap horizontalBitmap = Bitmap.createBitmap(mBitmap, // 그 그림 크기 그대로 horizontalbitmap에 넣고
                        0, 0, mBitmap.getWidth(), mBitmap.getHeight(), horizontalMatrix, false);
                canvas.drawBitmap(horizontalBitmap, x, y, paint); // 그것을 붙인다.
                isVerticalMatrix = false;
            }

            //상하 반전
            if (isVerticalMatrix) {
                Matrix verticalMatrix = new Matrix();
                verticalMatrix.setScale(hNum, vNum);
                Bitmap verticalBitmap = Bitmap.createBitmap(mBitmap,
                        0, 0, mBitmap.getWidth(), mBitmap.getHeight(), verticalMatrix, false);
                canvas.drawBitmap(verticalBitmap, x, y, paint);
                isHorizontalMatrix = false;
            }

            //원래 대로
            if (isVerticalMatrix == false && isHorizontalMatrix == false) {
                canvas.drawBitmap(mBitmap, x, y, paint);
            }
        }
    }
}

