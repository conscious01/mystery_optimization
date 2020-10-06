package com.zgzx.metaphysics.widgets;
/**
 * Created by mani on 03/09/17.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.TestEntity;
import com.zgzx.metaphysics.ui.activities.PreviewActivity;
import com.zgzx.metaphysics.ui.activities.TestActivity;
import com.zgzx.metaphysics.utils.DateUtils;
import com.zgzx.metaphysics.utils.ViewUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<TestEntity> list;
    private OnItemClickListener onItemClickListener;
    private Handler handler = new Handler();

    //  private List<Long> mTimeDownBeanList;
    public RecyclerViewAdapter(Context context,
                               OnItemClickListener onItemClickListener) {
        this.context = context;
        list = new ArrayList<>();
//    mTimeDownBeanList = new ArrayList<>();
    }


    public void setData(List<TestEntity> data) {

        if (data != null && data.size() > 0) {
            list.addAll(data);
            startTime();
        }
    }

    private void startTime() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < list.size(); i++) {
                            long useTime = list.get(i).getCountDowntime();

                            if (useTime > 0) {
                                useTime=useTime-1;
                                list.get(i).setCountDowntime(useTime);
                                RecyclerViewAdapter.this.notifyItemChanged(i);
                            }

                        }

                    }
                });


            }
        }, 0, 1000);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView contentTv;
        private TextView timeTv;

        private ViewHolder(View itemView) {
            super(itemView);
            init(itemView);
//      itemView.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//          if (itemClickListener!=null){
//            itemClickListener.onItemClick(v,getLayoutPosition());
//          }
//        }
//      });
        }

        private void init(View itemView) {
            contentTv = (TextView) itemView.findViewById(R.id.tv_title);
            timeTv = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recycle_item_message, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            TestEntity timeDownBean = list.get(position);
            viewHolder.contentTv.setText(timeDownBean.getTitle());
            try {
                setTime(viewHolder, position);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    private void setTime(ViewHolder holder, int position) throws ParseException {
        TestEntity timeDownBean = list.get(position);
        long lastTime = timeDownBean.getCountDowntime();

        System.out.println("settime--> " + lastTime);
        if (lastTime > -1) {
            holder.timeTv.setVisibility(View.VISIBLE);
            holder.timeTv.setText(DateUtils.secToTime((int) lastTime));

        } else {
//            holder.timeTv.setVisibility(View.GONE);
            holder.timeTv.setText("done");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();//list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
