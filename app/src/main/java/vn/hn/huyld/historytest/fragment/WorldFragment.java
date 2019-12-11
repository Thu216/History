package vn.hn.huyld.historytest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

import vn.hn.huyld.historytest.R;
import vn.hn.huyld.historytest.activity.QuestionActivity;
import vn.hn.huyld.historytest.adapter.PostsAdapter;
import vn.hn.huyld.historytest.control.JSHelper;
import vn.hn.huyld.historytest.model.DataList;

public class WorldFragment extends Fragment {

    GridView gvPost;
    ArrayList<DataList> dataList = new ArrayList<>();
    PostsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gvPost = getActivity().findViewById(R.id.gridViewPost);

        JSHelper jsHelper = new JSHelper(getActivity());
        try {
            dataList = jsHelper.getData("TG");

        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter = new PostsAdapter(getActivity(), dataList);
        gvPost.setAdapter(adapter);
    }
}
