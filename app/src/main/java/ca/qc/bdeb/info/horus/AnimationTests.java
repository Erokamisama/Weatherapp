package ca.qc.bdeb.info.horus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import ca.qc.bdeb.info.horus.helper.Typefaces;

public class AnimationTests extends Activity implements ViewSwitcher.ViewFactory {

    private final int[] images = {R.drawable.art_clear, R.drawable.art_light_clouds,
            R.drawable.art_clouds, R.drawable.art_fog, R.drawable.art_light_rain,
            R.drawable.art_rain, R.drawable.art_storm, R.drawable.art_snow,
            R.mipmap.ic_launcher};
    private final int interval = 750;
    private int index = 0;
    private boolean isRunning = true;

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.fadeout);
        final Animation anim_in = AnimationUtils.loadAnimation(c, R.anim.fadein);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_tests);

        Animation aniIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation aniOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.animationTests_isWeatherLogo);
        final TextView tv_appname = (TextView) findViewById(R.id.animationTests_tvAppName);
        tv_appname.setVisibility(View.INVISIBLE);
        tv_appname.setTypeface(Typefaces.get(this, getString(R.string.font_pacifico)));
        imageSwitcher.setInAnimation(aniIn);
        imageSwitcher.setOutAnimation(aniOut);
        imageSwitcher.setFactory(this);
        imageSwitcher.setImageResource(images[index]);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                if (isRunning) {
                    if (index < images.length - 1) {
                        index++;
                        Log.d("Intro Screen", "Change Image " + index);
                        imageSwitcher.setImageResource(images[index]);
                        handler.postDelayed(this, interval);
                        if (index == images.length - 1) {
                            isRunning = false;
                            tv_appname.setVisibility(View.VISIBLE);
                            Animation tv_animation = AnimationUtils.loadAnimation(AnimationTests.this, R.anim.fadein);
                            tv_appname.startAnimation(tv_animation);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(AnimationTests.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        if (index == images.length - 1) {
                            isRunning = false;
                            tv_appname.setVisibility(View.VISIBLE);
                            Animation tv_animation = AnimationUtils.loadAnimation(AnimationTests.this, R.anim.fadein);
                            tv_appname.startAnimation(tv_animation);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(AnimationTests.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                }
            }
        };
        handler.postDelayed(runnable, interval);
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public void finish() {
        isRunning = false;
        super.finish();
    }
}
