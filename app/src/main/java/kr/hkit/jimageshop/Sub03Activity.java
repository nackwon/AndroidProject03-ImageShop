package kr.hkit.jimageshop;

import com.bumptech.glide.Glide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

import static kr.hkit.jimageshop.Sub02Activity.SELECT_IMAGE;

public class Sub03Activity extends AppCompatActivity {

    public static final int CAMERA_CAPTURE = 1; // 사진을 인텐트를 보낼 때 request 상수
    ImageView camvasImageView;
    Activity mContext = this;
    Uri uri = null;

    boolean isOpenOrCamera = false; //글라이드 이미즈를 불러오거나 사진을 찍었는지 확인하는 변수

    int roundValue = 5; // edge circle effect value
    int blurValue = 1; // blur effect value
    float brightnessValue = 0.0f; // bright effect value
    float swirlValue1 = 0.0f; // swirl effect scale
    float swirlValue2 = 0.0f; // swirl effect
    int kuwaharaValue = 0; // 수체화
    float vignetteValeu1 = 0.0f; // vignette effect
    float vignetteValue2 = 0.0f;
    float contrastValue = 0.0f; // contrast effect
    int pixelValue = 0; // pixel effect value

    //글라이드 효과 선택 이미지에서 사용할 이미지의 아이디
    private Integer[] imageIds={
            R.id.imageView01,R.id.imageView02,R.id.imageView03,R.id.imageView04,
            R.id.imageView05,R.id.imageView06,R.id.imageView07,R.id.imageView08,
            R.id.imageView09,R.id.imageView10,R.id.imageView11,R.id.imageView12,
            R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16,
            R.id.imageView17,R.id.imageView18,R.id.imageView19,R.id.imageView20,
            R.id.imageView21,R.id.imageView22
    };

    // 글라이드 효과 선택 이미지에서 사용할 이미지의 위치
    private Integer[] imageDrables={
            R.drawable.glide_01,R.drawable.glide_02,R.drawable.glide_03,R.drawable.glide_04,
            R.drawable.glide_05,R.drawable.glide_06,R.drawable.glide_07,R.drawable.glide_08,
            R.drawable.glide_09,R.drawable.glide_10,R.drawable.glide_11,R.drawable.glide_12,
            R.drawable.glide_13,R.drawable.glide_14,R.drawable.glide_15,R.drawable.glide_16,
            R.drawable.glide_17,R.drawable.glide_18,R.drawable.glide_19,R.drawable.glide_20,
            R.drawable.glide_21,R.drawable.glide_22,
    };


    //이미지를 담을 배열
    ImageView[] imageViews = new ImageView[22];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub03);

        camvasImageView = (ImageView) findViewById(R.id.camvasImageView);

        for(int i=0; i<imageViews.length;i++){
            imageViews[i] = (ImageView) findViewById(imageIds[i]);
            Glide.with(mContext).load(imageDrables[i]).into(imageViews[i]);
        }
    }

    // glide liberary 효과를 주는 버튼 onclick 속성(upper button)
    public void onGlideImageViewClicked(View view) {
        if (isOpenOrCamera == false) {
            Toast.makeText(getApplicationContext(), "먼저 이미지를 불러오거나\n사진을 찍어 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()) {
            case R.id.imageView01: // circle effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "정원 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView02: // quadrangle effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new CropSquareTransformation(mContext))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "정사각형 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView03: // edge circle effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new RoundedCornersTransformation(mContext, roundValue += 2, 20))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(),
                        "모서리 라운드 값이" + roundValue + "가(이) 되었습니다.\n" +
                                "모서리 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView04: //blur up effect
                blurValue += 1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new BlurTransformation(mContext, blurValue))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "블러 값이" + blurValue + "가(이) 되었습니다.\n" +
                        "블러 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView05: //blur down effect
                blurValue -= 1;
                if (blurValue <= 0)
                    blurValue = 1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new BlurTransformation(mContext, blurValue))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "블러 값이" + blurValue + "가(이) 되었습니다.\n" +
                        "블러 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView06: // bright up effect
                brightnessValue += 0.1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new BrightnessFilterTransformation(mContext, brightnessValue))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "밝기 값이" + brightnessValue + "가(이) 되었습니다.\n" +
                        "밝기 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView07: // bright down effect
                brightnessValue -= 0.1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new BrightnessFilterTransformation(mContext, brightnessValue))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "밝기 값이" + brightnessValue + "가(이) 되었습니다.\n" +
                        "밝기 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView08: // gray tone effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new GrayscaleTransformation(mContext))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "그레이톤 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView09: //sephia tone effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new SepiaFilterTransformation(mContext))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "세피아톤 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView10: //invert effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new InvertFilterTransformation(mContext))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "반전 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView11: // sketch effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new SketchFilterTransformation(mContext))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "스케치 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView12: // Toon filter effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new ToonFilterTransformation(mContext))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "만화 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView13: // swirl effect(scale)
                swirlValue1 += 0.1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new SwirlFilterTransformation(mContext, swirlValue1, 1.0f, new PointF(0.5f, 0.5f)))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "소용돌이 값이" + swirlValue1 + "가(이) 되었습니다.\n" +
                        "소용돌이 효과(넓이)가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView14: // swirl effect(angle)
                swirlValue2 += 0.1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new SwirlFilterTransformation(mContext, 0.5f, swirlValue2, new PointF(0.5f, 0.5f)))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "소용돌이 값이" + swirlValue2 + "가(이) 되었습니다.\n" +
                        "소용돌이 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView15: // kuwahara effect
                kuwaharaValue += 1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new KuwaharaFilterTransformation(mContext, kuwaharaValue))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "수체화 값이" + kuwaharaValue + "가(이) 되었습니다.\n" +
                        "수체화 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView16: // vignette effect
                vignetteValeu1 += 0.1;
                if (vignetteValeu1 >= 1.0)
                    vignetteValeu1 = 1.0f;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new VignetteFilterTransformation(mContext,
                                new PointF(0.5f, 0.5f), //밝기 좌표(0.5가 중심값)
                                new float[]{vignetteValeu1, vignetteValue2, vignetteValeu1}, 0.0f, 0.75f)) //RGB 값
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "비그넷 값이" + vignetteValeu1 + "가(이) 되었습니다.\n" +
                        "비그넷 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView17: // vignette effect range
                vignetteValue2 -= 0.1;
                if (vignetteValue2 <= -1.0)
                    vignetteValue2 = -1.0f;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new VignetteFilterTransformation(mContext,
                                new PointF(0.5f, 0.5f),//밝기 좌표(0.5가 중심값)
                                new float[]{0.0f, 0.0f, 0.0f}, vignetteValue2, 0.75f))// vin-> 안쪽 범위 / 0.75->밖 범위
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "비그넷 값이" + vignetteValue2 + "가(이) 되었습니다.\n" +
                        "비그넷 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView18: // red tone effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new ColorFilterTransformation(mContext, Color.argb(100, 255, 0, 0)))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "레드톤 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView19: // green tone effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new ColorFilterTransformation(mContext, Color.argb(100, 0, 255, 0)))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "그린톤 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView20: // blue tone effect
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new ColorFilterTransformation(mContext, Color.argb(100, 0, 0, 255)))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "블루톤 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView21: // contrast effect
                contrastValue += 0.1f;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new ContrastFilterTransformation(mContext, contrastValue))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "콘트라스트 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageView22: // pixel effect
                pixelValue += 1;
                Glide.with(mContext).load(uri)
                        .bitmapTransform(new PixelationFilterTransformation(mContext, pixelValue))
                        .into(camvasImageView);
                Toast.makeText(getApplicationContext(), "픽셀 효과가 적용되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    // image download, camera, information, reset button / onclick 속성(bottom button)
    public void onImageViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageOpen:
                uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                Intent intent = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(intent, SELECT_IMAGE);
                isOpenOrCamera = true;
                break;
            case R.id.imageCamera:
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, uri);
                startActivityForResult(i,CAMERA_CAPTURE);
                isOpenOrCamera = true;
                break;
            case R.id.imageSave:
                //savePhoto();
                saveImage();
                break;
            case R.id.imageReset:
                blurValue = 0;
                brightnessValue = 0.0f;
                swirlValue1 = 0.0f;
                swirlValue2 = 0.0f;
                kuwaharaValue = 0;
                vignetteValeu1 = 0.0f;
                vignetteValue2 = 0.0f;
                contrastValue = 0.0f;
                pixelValue = 0;
                roundValue = 0;
                Glide.with(mContext).load(uri).into(camvasImageView);
                Toast.makeText(getApplicationContext(), "리셋되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    } // end camvas ImageView


    //2. save Photo (teacher)
    public void saveImage(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String saveTime = dateFormat.format(System.currentTimeMillis());

        camvasImageView.setDrawingCacheEnabled(true); //캐시
        Bitmap mBitmap = camvasImageView.getDrawingCache();

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File filePath = new File(path, "MyImageShop");

        //MyImageSHop 파일이 존재하는지 안하는지 검사
        if(!filePath.exists()){
            filePath.mkdir();
            Toast.makeText(getApplicationContext(),"MyImageShop 디렉토리가 생성되었습니다.",Toast.LENGTH_SHORT).show();
        }

        String savePath = path+"/MyImageShop"+saveTime +"_glide_change.png";

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(savePath);
            mBitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(),"이미지가 저장되었습니다.",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"이미지가 저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
        } finally {
            camvasImageView.setDrawingCacheEnabled(false); // 그전에 문제를 이것으로 해결
        }
    }

    //1. save Photo
  /*  public void savePhoto() {
        camvasImageView.setDrawingCacheEnabled(true);
        camvasImageView.buildDrawingCache();

        Bitmap saveBitmap = camvasImageView.getDrawingCache();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String photoFile = "MyPic" + date + ".png";

        try {
            FileOutputStream fos = new FileOutputStream(new File(path, photoFile));
            saveBitmap.compress(Bitmap.CompressFormat.PNG, 70, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getApplicationContext(), "Image saved!!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Image save failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/ // end savePhoto


    //사진 upload 해주는 메소드 / 카메라로 사진을 찍어 결과를 돌려받는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 갤러리로부터 사진을 선택하고 결과를 받는 부분
        if (resultCode == RESULT_OK && requestCode == SELECT_IMAGE) {
            uri = data.getData();
            Glide.with(mContext).load(uri).into(camvasImageView);
            Toast.makeText(getApplicationContext(), "이미지를 불러왔습니다.", Toast.LENGTH_SHORT).show();
        }

        //카메라로 사진을 찍어 사진을 돌려 받는 부분
        if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE){
            uri = data.getData();
            Glide.with(mContext).load(uri).into(camvasImageView);

        }
    } // end onActivityResult

}

