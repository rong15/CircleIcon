package com.het.circleiconlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class CircleIcon extends AppCompatImageView {

    private ImageLoader imageLoader;
    private Context mContext;
    private Paint mPaint;

    private boolean mLoaded;
    private String mImageUri = "";
    private int mCircleColor;
    private int mCircleWidth;
    public CircleIcon(Context context) {
        this(context,null);
    }

    public CircleIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.zrCircleView);
        if (ta != null){
            mCircleColor = ta.getColor(R.styleable.zrCircleView_circle_color,0xffffffff);
            mCircleWidth = ta.getInteger(R.styleable.zrCircleView_circle_width,6);

            ta.recycle();
        }

        mContext = context;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (mLoaded){
            mPaint = new Paint();
            mPaint.setColor(mCircleColor);
            mPaint.setStrokeWidth(mCircleWidth);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);

            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-mCircleWidth/2,mPaint);
        }else{
            init();
        }



    }
    public void setmImageUri(String uri){
        mImageUri = uri;

    }
    private void init(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .memoryCacheExtraOptions(480, 800)
                // 缓存在内存的图片的宽和高度
                // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//你可以通过自己的内存缓存实现
                .memoryCacheSize(3 * 1024 * 1024)// 缓存到内存的最大数据
                .memoryCacheSizePercentage(13)
                .diskCacheSize(50 * 1024 * 1024)// //缓存到文件的最大数据
                .diskCacheFileCount(100)// 文件数量
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .imageDownloader(new BaseImageDownloader(mContext)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()// Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 初始化
        if (mImageUri == null){
            Toast.makeText(mContext,"图片路径为空",Toast.LENGTH_SHORT).show();
            return;
        }


        imageLoader.displayImage(mImageUri, this ,MyDisplayImageOptions());
        imageLoader.setDefaultLoadingListener(new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                Log.i("mylog","onLoadingFailed");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                Log.i("mylog","onLoadingComplete");
                mLoaded = true;
                invalidate();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }

    public  DisplayImageOptions MyDisplayImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .displayer(new RoundedBitmapDisplayer(360))//是否设置为圆角，弧度为多少
//      .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成

        return options;
    }
}
