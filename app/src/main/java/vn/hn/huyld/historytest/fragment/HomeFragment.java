package vn.hn.huyld.historytest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.hn.huyld.historytest.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cardWorld = view.findViewById(R.id.cardWorld);
        cardWorld.setOnClickListener(this);

        CardView cardVN = view.findViewById(R.id.cardVN);
        cardVN.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (getFragmentManager() != null){
            switch (v.getId()){
                case R.id.cardWorld:
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorldFragment()).commit();
                    break;
                case R.id.cardVN:
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new VNFragment()).commit();
                    break;
            }
        }
    }
}
