package com.bashundhara.mushfequr.news_detector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Slide_Adapter extends PagerAdapter
{
    private static final String TAG=Slide_Adapter.class.getSimpleName();
    String[] embed_codes;
    private Context Current_Context;
    LayoutInflater layoutInflater;

    public Slide_Adapter(String[] embed_codes, Context current_Context) {
        this.embed_codes = embed_codes;
        Current_Context = current_Context;

    }

    @Override
    public int getCount() {
        if(embed_codes!=null)
            return embed_codes.length;
        else
            return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) Current_Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.youtube_slide_layout,container,false);
        WebView youtube_view = (WebView) view.findViewById(R.id.youtube_video);
        final ImageView left_scroll = (ImageView) view.findViewById(R.id.youtube_left_scroll);
        final ImageView right_scroll = (ImageView) view.findViewById(R.id.yourube_right_scroll);
        youtube_view.getSettings().setJavaScriptEnabled(true);
        youtube_view.getSettings().setPluginState(WebSettings.PluginState.ON);
        youtube_view.setWebChromeClient(new WebChromeClient());
        youtube_view.setWebViewClient(new WebViewClient());
        Log.d(TAG,"Playing Video");

        Log.d(TAG,embed_codes[position]);
        youtube_view.loadUrl("http://www.youtube.com/embed/" + embed_codes[position] + "?autoplay=1&vq=small");

        if(position==0)
        {
            left_scroll.setVisibility(View.INVISIBLE);
            right_scroll.setVisibility(View.VISIBLE);
        }
        else if(position==getCount()-1)
        {
            right_scroll.setVisibility(View.INVISIBLE);
            left_scroll.setVisibility(View.VISIBLE);
        }
        else
        {
           left_scroll.setVisibility(View.VISIBLE);
           right_scroll.setVisibility(View.VISIBLE);
        }

        youtube_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                left_scroll.setVisibility(View.INVISIBLE);
                right_scroll.setVisibility(View.INVISIBLE);
            }
        });

        container.addView(view);
        return view;
        //return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((RelativeLayout) object);
    }
}
