package com.jusfoun.timepumpingdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author zhaoyapeng
 * @version create time:16/1/7上午11:17
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class TimePumpingEntity {

    public static final int TYPE_LINE=0;
    public static final int TYPE_IMAGE=1;
    private int imgWidth = 0;
    private int imgHeight = 0;
//    private ImageView imageView = null;
    private int initX, initY;
    private Context mContext;
    private int offsetHeight = 0;
    private int screenWidth = 0;//屏幕宽度
    private int screenHeight = 0;//屏幕高度
    private RelativeLayout layout;

    private float currentY = 0;
    private float currentX = 0;
    private int width,height;

    private int lineWidth =0,lineHight;

    private int type;

    private String year;

    private int saveX, saveY, saveWidth, saveHeight;

    private float ratio;

    public TimePumpingEntity(Context mContext, RelativeLayout layout) {
        this.mContext = mContext;
//        imgWidth = Utils.dip2px(mContext, 10);
//        imgHeight = Utils.dip2px(mContext, 10);
        offsetHeight = Utils.dip2px(mContext, 30);
        Bitmap bitmap= BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.img_time1);
        Bitmap bitmap1=BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.legal);
//        ratio=(bitmap1.getHeight()+bitmap.getHeight()/2)/(float)(bitmap.getHeight()+bitmap1.getHeight());
        ratio=0.7f;
        width=bitmap.getWidth();
        height=bitmap.getHeight();
        imgWidth = width/10;
        imgHeight =height/10;
        initX = Utils.getDisplayWidth(mContext) / 2 - imgWidth / 2;
        initY = Utils.dip2px(mContext, 30) - imgHeight;
        screenWidth = Utils.getDisplayWidth(mContext);
        screenHeight = Utils.getDisplayHeight(mContext);
        saveWidth = imgWidth;
        saveHeight = imgHeight;
        lineWidth =screenWidth/15;
        lineHight=Utils.dip2px(mContext,5);
        this.layout = layout;

    }


    public void createImg(float x, float y) {
//        imageView = new ImageView(mContext);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(saveWidth, saveHeight);
//        imageView.setLayoutParams(params);
//        imageView.setImageResource(R.mipmap.img_time1);
//        imageView.setAlpha(0f);
//        imageView.setX(x);
//        imageView.setY(y);
    }

    private View imageView;
    private TextView txt1,txt2;
    public void createView(int type) {
        switch (type){
            case TYPE_LINE:
                imageView = LayoutInflater.from(mContext).inflate(R.layout.time_line_layout, null);
                txt1= (TextView) imageView.findViewById(R.id.txt1);
                txt2= (TextView) imageView.findViewById(R.id.txt2);
                txt1.setText(year);
                txt2.setText(year);
                break;
            case TYPE_IMAGE:
                imageView = LayoutInflater.from(mContext).inflate(R.layout.time_item_layout, null);
                break;
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(saveWidth, saveHeight);
        imageView.setLayoutParams(params);
        imageView.setAlpha(0f);
    }

    public void setImageViewParams(float moveY, int angle) {

        if ((currentY + moveY - (Utils.dip2px(mContext, 30) - imgHeight)) > 0 && (currentY + moveY - (Utils.dip2px(mContext, 30) - imgHeight)) < screenHeight) {
            if (imageView == null) {
//                createImg(currentX, currentY + moveY);
                createView(type);
                layout.addView(imageView);
            }

            switch (type){
                case TYPE_LINE:
                    changeLine(moveY);
                    break;
                case TYPE_IMAGE:
                    changeImage(moveY,angle);
                    break;
            }

        } else {
            Log.e("tag", "销毁中");
            if (imageView != null) {
//                currentY = currentY + moveY;
                layout.removeView(imageView);
                imageView = null;
            }
        }
    }

    private void changeLine(float moveY){

        float scale = 0;
        scale = (currentY + moveY) / (screenHeight / 2.5f);
//            float y=(currentY+moveY)*scale;
        currentX = screenWidth / 2;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        imageView.setX((float) (currentX - (currentY + moveY) * Math.tan(Math.PI * (60 / 180.0f))));
        imageView.setY(currentY + moveY - params.height / 2);
        float size=  ((currentY+moveY)/screenHeight)*25;
        txt1.setTextSize(size);
        txt2.setTextSize(size);
//            if(currentY + moveY<screenHeight/4) {
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = (int) (screenWidth-imageView.getX()*2);
        Log.e("change","width=="+params.width+"\ncurrentX=="+imageView.getX()+"\nendX=="+(params.width+imageView.getX()));

//            }
        imageView.requestLayout();
        if (currentY + moveY < screenHeight / 4) {
            Log.e("tag", "currentY + moveY=" + (currentY + moveY));
            float alpha = (currentY + moveY) / (screenHeight / 2.5f);
            imageView.setAlpha(alpha);
        } else if (currentY + moveY < screenHeight / 3) {
            imageView.setAlpha(1f);
        } else {
//                float alpha = 1.0f - (screenHeight - imageView.getY()) / Utils.dip2px(mContext, 200);
            float alpha = 1.0f - (currentY + moveY - screenHeight / 3) / (screenHeight / 3);
            Log.e("tag", "alpha=" + alpha);

            imageView.setAlpha(alpha);
        }
//            else {
//                imageView.setAlpha(1.0f);
//            }
    }
    private void changeImage(float moveY,float angle){
//        float scale = 0;
//        scale = (currentY + moveY) / (screenHeight / 2.5f);
//            float y=(currentY+moveY)*scale;
        if (moveY+currentY-offsetHeight<=0)
            return;
        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        Log.e("image", "currentY=="+imageView.getY()+"\ncurrentX=="+imageView.getX());


        Log.e("tag", "执行到这里了currentX=" + currentX + " currentY=" + (currentY + moveY) + " imageView.w=" + imageView.getWidth() + " imageView.h=" + imageView.getHeight());
        Log.e("tag", "setImageViewParams=" + (screenWidth / 2 - (float) ((currentY + moveY - offsetHeight + imageView.getHeight()) * Math.tan(Math.PI * (angle / 180.0f))) - imageView.getWidth() / 2));

//            if(currentY + moveY<screenHeight/4) {
        saveWidth = (int) (imgHeight * (1 + (imageView.getY() - offsetHeight + imgWidth) / Utils.dip2px(mContext, 20)));
        saveHeight = (int) (imgHeight * (1 + (imageView.getY() - offsetHeight + imgHeight) / Utils.dip2px(mContext, 10)));
        params.height = (int) (imgHeight * (1 + (moveY+currentY - offsetHeight) / Utils.dip2px(mContext, 10)));
        params.width = (int) (imgWidth * (1 + (moveY+currentY  - offsetHeight ) / Utils.dip2px(mContext, 20)));

//            }
        Log.e("tag", "params.height===" + (imgHeight * (1 + (imageView.getY() + imgHeight) / Utils.dip2px(mContext, 20))));

        imageView.requestLayout();
        float y=(currentY+moveY)-params.height*ratio;

        currentX = screenWidth / 2 - (float) ((currentY+moveY - offsetHeight ) * Math.tan(Math.PI * (angle / 180.0f))) - params.width / 2;
        Log.e("xy", "image"+(params.height*ratio));

        imageView.setX(currentX);
        imageView.setY(y);

        Log.e("tag", "imageView.getY()1=" + imageView.getY());
        Log.e("tag", "imageView.getY()2=" + (screenHeight - imageView.getY()));
        Log.e("tag", "imageView.getY()3=" + Utils.dip2px(mContext, 200));
        Log.e("tag", "alpha1=" + (1.0f - (screenHeight - imageView.getY()) / imageView.getHeight()));
        if (currentY + moveY < screenHeight / 3) {
            Log.e("tag", "currentY + moveY=" + (currentY + moveY));
            float alpha = (currentY + moveY) / (screenHeight / 2.5f);
            imageView.setAlpha(alpha);
        } else if (currentY + moveY > screenHeight / 3&&currentY+moveY<screenHeight*4/5) {
            imageView.setAlpha(1f);
        } else {
//                float alpha = 1.0f - (screenHeight - imageView.getY()) / Utils.dip2px(mContext, 200);
            float alpha = 1.0f - (currentY + moveY - screenHeight*4 / 5) / (screenHeight / 5);
            Log.e("tag", "alpha=" + alpha);

            imageView.setAlpha(alpha);
        }
//            else {
//                imageView.setAlpha(1.0f);
//            }
    }


    public void setType(int type) {
        this.type = type;
    }

    public void init(int y) {
        currentY = y;
    }

    public void setCurrentY(float moveY) {
        this.currentY = currentY + moveY;
    }

    public float getX(){
        return imageView==null?0:imageView.getX();
    }

    public float getY(){
        return imageView==null?0:imageView.getY();
    }

    public void setYear(String year) {
        this.year = year;
    }

}
