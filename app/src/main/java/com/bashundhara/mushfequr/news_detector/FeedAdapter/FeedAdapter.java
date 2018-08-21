package com.bashundhara.mushfequr.news_detector.FeedAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bashundhara.mushfequr.news_detector.Interface.ItemClickListner;
import com.bashundhara.mushfequr.news_detector.Model.RSSObject;
import com.bashundhara.mushfequr.news_detector.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
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
        holder.TextTitle.setText((CharSequence) rssObject.getItems().get(position).getTitle());
        holder.TextPubdate.setText(rssObject.getItems().get(position).getPubDate());
        holder.TextContent.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void OnCLick(View view, int position, boolean isLongClick) {
                if(!isLongClick)
                {
                    Intent browser_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    mContext.startActivity(browser_intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(rssObject!=null) {
            return rssObject.getItems().size();
        }
        else
            return 0;

    }
}

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
{

    public TextView TextTitle,TextPubdate,TextContent;
    private ItemClickListner itemClickListner;

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public FeedViewHolder(View itemView) {

        super(itemView);
        TextTitle =(TextView) itemView.findViewById(R.id.Texttitle);
        TextPubdate=(TextView) itemView.findViewById(R.id.PD);
        TextContent=(TextView) itemView.findViewById(R.id.Content);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.OnCLick(view,getAdapterPosition(),false);

    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListner.OnCLick(view,getAdapterPosition(),true);
        return true;
    }
}
