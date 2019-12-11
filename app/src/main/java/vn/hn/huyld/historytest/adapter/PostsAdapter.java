package vn.hn.huyld.historytest.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import vn.hn.huyld.historytest.R;
import vn.hn.huyld.historytest.activity.QuestionActivity;
import vn.hn.huyld.historytest.model.DataList;

public class PostsAdapter extends ArrayAdapter<DataList> {

    public static final String EXTRA_POST = "extraPost";

    public PostsAdapter(@NonNull Context context, ArrayList<DataList> dataLists){
        super(context, 0, dataLists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_post, parent, false);
        }
        TextView tvPost = convertView.findViewById(R.id.tvItemPost);
        final TextView tvNumPost = convertView.findViewById(R.id.tvNumPost);
        final DataList dataList = getItem(position);

        tvNumPost.setText(String.format(Locale.getDefault(),"Bài %d", position + 1));
        assert dataList != null;
        tvPost.setText(dataList.mPosts);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), QuestionActivity.class);
                intent.putExtra(EXTRA_POST, dataList.mPosts);
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
