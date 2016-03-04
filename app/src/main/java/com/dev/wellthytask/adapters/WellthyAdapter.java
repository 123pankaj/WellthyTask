package com.dev.wellthytask.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.dev.wellthytask.R;
import com.dev.wellthytask.constants.AppConstants;
import com.dev.wellthytask.models.DataModel;

import java.util.ArrayList;


/**
 * Created by dev on 4/3/16.
 */
public class WellthyAdapter extends RecyclerView.Adapter<WellthyAdapter.ViewHolder> {
    Context ctx;
    AQuery aq;
    ArrayList<DataModel> data;

    public WellthyAdapter(Context context, ArrayList<DataModel> list) {
        this.ctx = context;
        this.data = list;
        aq = new AQuery(ctx);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public WellthyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_inflator, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        DataModel model = data.get(position);
        viewHolder.word.setText(model.getWord());
        viewHolder.meaning.setText(model.getMeaning());
        aq.id(viewHolder.image).progress(viewHolder.progress).image(AppConstants.IMAGE_URL + model.getImage_id() + AppConstants.IMAGE_EXTENTION);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView word, meaning;
        ProgressBar progress;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            image = (ImageView) itemLayoutView.findViewById(R.id.image);
            word = (TextView) itemLayoutView.findViewById(R.id.word);
            meaning = (TextView) itemLayoutView.findViewById(R.id.meaning);
            progress = (ProgressBar) itemView.findViewById(R.id.progress);
        }
    }

    @Override
    public int getItemCount() {
        return  data.size();
    }
}
