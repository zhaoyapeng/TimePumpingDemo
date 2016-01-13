package com.jusfoun.timepumpingdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Author  wangchenchen
 * CreateDate 2016/1/12.
 * Email wcc@jusfoun.com
 * Description
 */
public class CustomeScrollView extends View {
    private Context context;
    private float currentY=0,moveY;
    private float downY;
    private Paint paint;
    private int width;
    private int scale=1;
    private int y=0, screenHeight,screenWidth;
    private List<Rect> points;
    private int radius;
    private int clickCount=-1;
    public CustomeScrollView(Context context) {
        super(context);
        initView(context);
    }

    public CustomeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomeScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth=w;
        Log.e("size", "w==" + w + "\nh==" + h);
        y=h/2;
        screenHeight =h;
        refresh(18);

    }

    private void initView(Context context){
        this.context=context;
        width=Utils.dip2px(context,40);
        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        points=new ArrayList<>();

        radius=Utils.dip2px(context,20);


    }

    public void refresh(int count){
        int index=points.size();
        for (int i = index; i <index+count ; i++) {
            if (scale>=3)
                scale=1;
            Rect rect=new Rect();
            if (i==0)
                rect.set(screenWidth,0,screenWidth+width,Utils.dip2px(context,50));
            else
                rect.set(points.get(i-1).right+scale*width,0,points.get(i-1).right+(scale+1)*width,Utils.dip2px(context,50));
            points.add(rect);
            scale++;
        }
    }

    public int getClickCount(int x,int y){
        for (int i = 0; i <points.size() ; i++) {
            Rect rect=points.get(i);
            if (rect.left+currentY+moveY<=x
                    &&rect.right+currentY+moveY>=x
                    &&rect.top<=y
                    &&rect.bottom>=y){
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getPointerCount()>=2){
            return true;
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = event.getX();
                clickCount=getClickCount((int) (event.getX()), (int) event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                moveY =  event.getX()-downY;
                pointMove(moveY,true);
                break;
            case MotionEvent.ACTION_UP:
                if (clickCount!=-1) {
                    Toast.makeText(context, "第" + clickCount + "个点", Toast.LENGTH_SHORT).show();
                    //点击跳转
                    /*if (listener!=null){
                        listener.touchMove(moveY-500);
                        listener.touchUp(currentY,moveY-500);
                    }*/
                }
                pointUp(currentY,moveY,true);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawColor(Color.parseColor("#fff000"));
        for (int i = 0; i < points.size(); i++) {
            Rect rect=points.get(i);
            canvas.drawCircle(rect.left/2+rect.right/2+currentY+moveY,rect.top/2+rect.bottom/2,radius,paint);
            Log.e("move", (currentY+moveY) + "");
            if (i%6==0)
                canvas.drawLine(rect.left+currentY+moveY,0,rect.left+currentY+moveY, screenHeight,paint);
        }
        canvas.restore();
    }

    public void pointUp(float currentY,float moveY,boolean isHasListener){
            this.currentY = currentY + moveY;
            this.downY = 0;
            this.moveY = 0;
        postInvalidate();
            if (listener!=null&&isHasListener)
                listener.touchUp(currentY,moveY);
        }

    public void pointMove(float moveY,boolean isHasListener){
        if(Math.abs(moveY)>10) {
            this.moveY=moveY;
            postInvalidate();
            if (listener!=null&&isHasListener){
                listener.touchMove(moveY);
            }
        }
    }

    private OnScrollTouchListener listener;
    public void setListener(OnScrollTouchListener listener){
        this.listener=listener;
    }
    public interface OnScrollTouchListener{
        public void touchUp(float currentY,float moveY);
        public void touchMove(float moveY);
    }

}
