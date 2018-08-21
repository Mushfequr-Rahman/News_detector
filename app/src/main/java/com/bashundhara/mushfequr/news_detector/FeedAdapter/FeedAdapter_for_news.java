package com.bashundhara.mushfequr.news_detector.FeedAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bashundhara.mushfequr.news_detector.Interface.ItemClickListner;
import com.bashundhara.mushfequr.news_detector.Model.WebSite;
import com.bashundhara.mushfequr.news_detector.R;

public class FeedAdapter_for_news extends RecyclerView.Adapter<FeedViewHolder>  {

    private WebSite website_Object;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter_for_news(WebSite rssObject, Context mContext) {
        this.website_Object = rssObject;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView= inflater.inflate(R.layout.row,parent,false);


        return new FeedViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.TextTitle.setText((CharSequence) website_Object.getArticles().get(position).getTitle());
        holder.TextPubdate.setText(String.format(website_Object.getArticles().get(position).getSource().getName()+" Published On "+website_Object.getArticles().get(position).getPublishedAt()));
        holder.TextContent.setText(website_Object.getArticles().get(position).getDescription());


        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void OnCLick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    Intent browser_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website_Object.getArticles().get(position).getUrl()));
                    mContext.startActivity(browser_intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(website_Object!=null) {
            return website_Object.getArticles().size();
        }
        else
            return 0;

    }
}



