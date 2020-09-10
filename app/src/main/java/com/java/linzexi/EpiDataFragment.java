package com.java.linzexi;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class EpiDataFragment extends Fragment {
    public XRecyclerView dataRecyclerView;
//    private EpiDataXRecyclerViewAdapter dataRecyclerAdapter;
    private EpiDataViewModel mViewModel;
//    private PopupWindow popupWindow = null;
//    TextView pop_text = null;

    public static EpiDataFragment newInstance() {
        return new EpiDataFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EpiDataViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.epi_data_fragment, container, false);

//        dataRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_epi_data);
//        dataRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        dataRecyclerView.addItemDecoration(new MyDecoration());
//        dataRecyclerAdapter = new EpiDataXRecyclerViewAdapter(getContext(), this);
//        dataRecyclerView.setAdapter(dataRecyclerAdapter);
//
//        dataRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                dataRecyclerView.refreshComplete();
//            }
//
//            @Override
//            public void onLoadMore() {
//                dataRecyclerView.loadMoreComplete();
//            }
//        });

//        final View popupWindow_view = getLayoutInflater().inflate(R.layout.pop_below, null, false);
//        pop_text = popupWindow_view.findViewById(R.id.pop_window);
//        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setAnimationStyle(R.style.AnimationFade);
//        popupWindow.setFocusable(true);
//        popupWindow.showAtLocation(view, 0,Gravity.CENTER,Gravity.CENTER);
//        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (popupWindow_view != null && popupWindow_view.isShown()) {
//
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                }
//                return false;
//            }
//        });
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
//        popupWindow.dismiss();

//        mViewModel.updateData(0, dataRecyclerAdapter);
//        dataRecyclerAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel

    }

//    public void popup(final EpiDataModel model){
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pop_text.setText(model.place + " confirmed:" + model.getLastData().confirmed);
//
//                popupWindow.showAsDropDown(dataRecyclerView);
//            }
//        },500);
//
//    }

//    class MyDecoration extends RecyclerView.ItemDecoration {
//        @Override
//        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.set(0,0,0, 15);
//        }
//    }

}