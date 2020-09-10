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

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.tabs.TabLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

public class EpiDataFragment extends Fragment {
    public XRecyclerView dataRecyclerView;
    //    private EpiDataXRecyclerViewAdapter dataRecyclerAdapter;
    private EpiDataViewModel mViewModel;

    private BarChart confirmedBarChart;
    //private BarChart suspectedBatChart;
    private BarChart curedBarChart;
    private BarChart deadBarChart;
    private BarCharts barCharts;
//    private PopupWindow popupWindow = null;
//    TextView pop_text = null;

    public static EpiDataFragment newInstance() {
        return new EpiDataFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EpiDataViewModel.class);
        barCharts = new BarCharts();
        System.out.println("CREATEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.epi_data_fragment, container, false);

        final TabLayout tabLayout = view.findViewById(R.id.tabLayout_epi_types);
        tabLayout.addTab(tabLayout.newTab().setText("全球疫情"));
        tabLayout.addTab(tabLayout.newTab().setText("国内疫情"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    barCharts.showBarChart(confirmedBarChart, mViewModel.findData(0, 0, confirmedBarChart), true);
                    //barCharts.showBarChart(suspectedBatChart, mViewModel.findData(0, 1, suspectedBatChart), true);
                    barCharts.showBarChart(curedBarChart, mViewModel.findData(0, 2, curedBarChart), true);
                    barCharts.showBarChart(deadBarChart, mViewModel.findData(0, 3, deadBarChart), true);
                }
                else{
                    barCharts.showBarChart(confirmedBarChart, mViewModel.findData(1, 0, confirmedBarChart), true);
                    //barCharts.showBarChart(suspectedBatChart, mViewModel.findData(1, 1, suspectedBatChart), true);
                    barCharts.showBarChart(curedBarChart, mViewModel.findData(1, 2, curedBarChart), true);
                    barCharts.showBarChart(deadBarChart, mViewModel.findData(1, 3, deadBarChart), true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });
        confirmedBarChart = (BarChart) view.findViewById(R.id.confirmed_chart);
        //suspectedBatChart = (BarChart) view.findViewById(R.id.suspected_chart);
        curedBarChart = (BarChart) view.findViewById(R.id.cured_chart);
        deadBarChart = (BarChart) view.findViewById(R.id.dead_chart);
        barCharts.showBarChart(confirmedBarChart, mViewModel.findData(0, 0, confirmedBarChart), true);
        //barCharts.showBarChart(suspectedBatChart, mViewModel.findData(0, 1, suspectedBatChart), true);
        barCharts.showBarChart(curedBarChart, mViewModel.findData(0, 2, curedBarChart), true);
        barCharts.showBarChart(deadBarChart, mViewModel.findData(0, 3, deadBarChart), true);



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