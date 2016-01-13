package com.jusfoun.timepumpingdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyapeng
 * @version create time:16/1/5上午10:52
 * @Email zyp@jusfoun.com
 * @Description ${TODO}
 */
public class TimePumpingView extends RelativeLayout {
    private int trCount = 0;

    private Context mContext;
    private int screenWidth = 0;//屏幕宽度
    private int screenHeight = 0;//屏幕高度


    private int imgWidth = 200;
    private int imgHeight = 200;
    private int index=0;


    private List<HashMap<String,Object>> firstList;
    private List<HashMap<String,Object>> secondeList;
    private List<HashMap<String,Object>> thirdList;

    int initY = 0;
//    private TimePumpingEntity timePumpingEntity1;
//    private TimePumpingEntity timePumpingEntity2;
//    private TimePumpingEntity timePumpingEntity3;

    public TimePumpingView(Context context) {
        super(context);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public TimePumpingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    public TimePumpingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
        initViews();
        initActions();
    }

    private void initData() {

        screenWidth = Utils.getDisplayWidth(mContext);
        screenHeight = Utils.getDisplayHeight(mContext);
        imgWidth = Utils.dip2px(mContext, 10);
        imgHeight = Utils.dip2px(mContext, 10);

        initY = Utils.dip2px(mContext, 30) - imgHeight;

        firstList = new ArrayList<>();
        secondeList = new ArrayList<>();
        thirdList = new ArrayList<>();


    }

    private void initViews() {
        setBackgroundResource(R.mipmap.back);
    }

    private void initActions() {
//        invalidate();
        addImageView();
    }


    public void addImageView() {

        currentY = Utils.dip2px(mContext, 30) - imgHeight;
//        imageView1 = new ImageView(getContext());
//        RelativeLayout.LayoutParams params = new LayoutParams(imgWidth, imgHeight);
//        imageView1.setLayoutParams(params);
//        imageView1.setImageResource(R.mipmap.ic_launcher);
//        addView(imageView1);
//        imageView1.setX(screenWidth / 2 - imgWidth / 2);
//        imageView1.setY(Utils.dip2px(mContext, 30) - imgHeight);
//
//        imageView1.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "aaa", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RelativeLayout.LayoutParams params2 = new LayoutParams(imgWidth, imgHeight);
//        imageView2 = new ImageView(getContext());
//        imageView2.setLayoutParams(params2);
//        imageView2.setImageResource(R.mipmap.ic_launcher);
//        addView(imageView2);
//        imageView2.setX(screenWidth / 2 - imgWidth / 2);
//        imageView2.setY(Utils.dip2px(mContext, 30) - imgHeight);

//        RelativeLayout.LayoutParams params3 = new LayoutParams(imgWidth, imgHeight);
//        imageView3 = new ImageView(getContext());
//        imageView3.setLayoutParams(params3);
//        imageView3.setImageResource(R.mipmap.ic_launcher);
//        addView(imageView3);
//        imageView3.setX(screenWidth / 2 - imgWidth / 2);
//        imageView3.setY(Utils.dip2px(mContext, 30) - imgHeight);
//        timePumpingEntity1 = new TimePumpingEntity(mContext, this);
//        timePumpingEntity2 = new TimePumpingEntity(mContext, this);
//        timePumpingEntity3 = new TimePumpingEntity(mContext, this);
        addData();
    }

    float downY = 0;
    float moveY;
    private float downX =0;
    private float moveX=0;
    private float currentY = 0;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("tag", "onTouchEvent1");
        if(event.getPointerCount()>=2){
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
                Log.e("tag", "callca");
                break;
            case MotionEvent.ACTION_DOWN:
                Log.e("tag", "onTouchEvent2");
                downY = event.getY();
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("tag", "onTouchEvent3");
                moveY = -downY + event.getY();
                moveX = moveX-event.getX();
                pointMove(moveY,true);
                break;
            case MotionEvent.ACTION_UP: {
                pointUp(currentY,moveY,true);
                break;

            }
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
    }

    public interface OnTouchChangeListener{
        public void touchUp(float currentY,float moveY);
        public void touchMove(float moveY);
        public void addData(int count);
    }

    private OnTouchChangeListener listener;
    public void  setListener(OnTouchChangeListener listener){
        this.listener=listener;
    }

    public void pointUp(float currentY,float moveY,boolean isHasListener){
        if(Math.abs(moveY)>10) {
            for (int i = 0; i < firstList.size(); i++) {
                HashMap<String,Object> map=firstList.get(i);
                ((TimePumpingEntity)map.get("value")).setCurrentY(moveY);
            }
//
//                    for (int i = 0; i < secondeList.size(); i++) {
//                        HashMap<String,Object> map=secondeList.get(i);
//                        ((TimePumpingEntity)map.get("value")).setCurrentY(moveY);
//                    }
//
//                    for (int i = 0; i < thirdList.size(); i++) {
//                        HashMap<String,Object> map=thirdList.get(i);
//                        ((TimePumpingEntity)map.get("value")).setCurrentY(moveY);
//                    }
            this.currentY = currentY + moveY;
            this.downY = 0;
            this.moveY = 0;
            if (listener!=null&&isHasListener)
                listener.touchUp(currentY,moveY);
        }
    }

    private  int count=0;
    public void pointMove(float moveY,boolean isHasListener){
        if(Math.abs(moveY)>10) {
            for (int i = firstList.size()-1; i >= 0; i--) {
                HashMap<String,Object> map=firstList.get(i);
                if ("1".equals(map.get("type"))){
                    ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, 50);
                }else if ("2".equals(map.get("type"))){
                    ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, 0);
                }else if ("3".equals(map.get("type"))){
                    ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, -50);
                }else {
                    ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, -1);
                }
            }
            Log.e("1234", (currentY + moveY) + "\nindex123"+(Utils.dip2px(mContext, 60) * index));
            if (4000*(count+1)<Math.abs(currentY+moveY)) {
                addData();
                count++;
                if (listener!=null)
                    listener.addData(18);
            }

//                    for (int i = 0; i < secondeList.size(); i++) {
//                        HashMap<String,Object> map=secondeList.get(i);
//                        if ("1".equals(map.get("type"))){
//                            ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, 40);
//                        }if ("2".equals(map.get("type"))){
//                            ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, 0);
//                        }else if ("3".equals(map.get("type"))){
//                            ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, -40);
//                        }
//                    }
//
//                    for (int i = 0; i < thirdList.size(); i++) {
//                        HashMap<String,Object> map=thirdList.get(i);
//                        if ("1".equals(map.get("type"))){
//                            ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, 40);
//                        }if ("2".equals(map.get("type"))){
//                            ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, 0);
//                        }else if ("3".equals(map.get("type"))){
//                            ((TimePumpingEntity)map.get("value")).setImageViewParams(moveY, -40);
//                        }
//                    }

            if (listener!=null&&isHasListener){
                listener.touchMove(moveY);
            }

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void addData() {

        index=0;
        String string=Utils.getString(getResources().openRawResource(R.raw.temp));
        TimeModel model=new Gson().fromJson(string, TimeModel.class);
        List<TimeItemModel> datas=model.getData();
        for (TimeItemModel data:datas){
            List<TimeItemValueModel> values=data.getValue();
            HashMap<String,Object> map1=new HashMap<>();
            TimePumpingEntity timePumpingEntity1 = new TimePumpingEntity(mContext, this);
            timePumpingEntity1.init(initY - (int) (Utils.dip2px(mContext, 60) * index));
            timePumpingEntity1.setType(TimePumpingEntity.TYPE_LINE);
            timePumpingEntity1.setYear(data.getYear());
            map1.put("type","-1");
        /*        if (value.getType().equals("1")){
                    map.put("type", "1");
                }else if (value.getType().equals("2")){
                    map.put("type", "2");
                }else if (value.getType().equals("3")){
                    map.put("type", "2");
                 }*/
            map1.put("value", timePumpingEntity1);
            firstList.add(map1);
            index++;
            for (TimeItemValueModel value:values){
                HashMap<String,Object> map=new HashMap<>();
                TimePumpingEntity timePumpingEntity = new TimePumpingEntity(mContext, this);
                timePumpingEntity.init(initY - (int)(Utils.dip2px(mContext, 60) * index));
                timePumpingEntity.setType(TimePumpingEntity.TYPE_IMAGE);
                map.put("type",value.getType());
        /*        if (value.getType().equals("1")){
                    map.put("type", "1");
                }else if (value.getType().equals("2")){
                    map.put("type", "2");
                }else if (value.getType().equals("3")){
                    map.put("type", "2");
                }*/
                map.put("value",timePumpingEntity);
                firstList.add(map);
                index++;
            }
        }
//        for (int i = 10; i >0; i--) {
//            HashMap<String,Object> map=new HashMap<>();
//            TimePumpingEntity timePumpingEntity = new TimePumpingEntity(mContext, this);
//            timePumpingEntity.init(initY - (int)(Utils.dip2px(mContext, 100) * i));
////            if(i==0){
////                timePumpingEntity.createImg(initX,initY);
////            }
//            map.put("type","1");
//            map.put("value",timePumpingEntity);
//            firstList.add(map);
//        }

//        for (int i = 10; i>0 ; i--) {
//            HashMap<String,Object> map=new HashMap<>();
//            TimePumpingEntity timePumpingEntity = new TimePumpingEntity(mContext, this);
//            timePumpingEntity.init(initY - (int)(Utils.dip2px(mContext, 80) * i));
////            if(i==0){
////                timePumpingEntity.createImg(initX,initY);
////            }
//            map.put("type","2");
//            map.put("value",timePumpingEntity);
//            secondeList.add(map);
//        }

//        for (int i = 10; i > 0; i--) {
//            HashMap<String,Object> map=new HashMap<>();
//            TimePumpingEntity timePumpingEntity = new TimePumpingEntity(mContext, this);
//            timePumpingEntity.init(initY - (int)(Utils.dip2px(mContext, 90) * i));
////            if(i==0){
////                timePumpingEntity.createImg(initX,initY);
////            }
//            map.put("type","3");
//            map.put("value",timePumpingEntity);
//            thirdList.add(map);
//        }

    }


}
