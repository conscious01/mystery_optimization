package com.zgzx.metaphysics.city_time_picker.xpopupext.view;

import android.view.View;


import com.zgzx.metaphysics.city_time_picker.R;
import com.zgzx.metaphysics.city_time_picker.wheel.OnItemSelectedListener;
import com.zgzx.metaphysics.city_time_picker.wheel.WheelView;
import com.zgzx.metaphysics.city_time_picker.xpopupext.adapter.ArrayWheelAdapter;
import com.zgzx.metaphysics.city_time_picker.xpopupext.listener.OnOptionsSelectListener;

import java.util.List;

public class WheelOptions<T> {

    private View view;
    private WheelView wv_option1;
    private WheelView wv_option2;
    private WheelView wv_option3;

    private List<T> mOptions1Items;
    private List<List<T>> mOptions2Items;
    private List<List<List<T>>> mOptions3Items;

    private boolean linkage = true; // 默认联动
    private boolean isRestoreItem;  // 切换时，还原第一项
    private OnItemSelectedListener wheelListener_option1;
    private OnItemSelectedListener wheelListener_option2;

    private OnOptionsSelectListener optionsSelectChangeListener;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelOptions(View view, boolean isRestoreItem) {
        super();
        this.isRestoreItem = isRestoreItem;
        this.view = view;
        wv_option1 = (WheelView) view.findViewById(R.id.options1);// 初始化时显示的数据
        wv_option2 = (WheelView) view.findViewById(R.id.options2);
        wv_option3 = (WheelView) view.findViewById(R.id.options3);
    }


    public void setPicker(List<T> options1Items, List<List<T>> options2Items, List<List<List<T>>> options3Items) {
        this.mOptions1Items = options1Items;
        this.mOptions2Items = options2Items;
        this.mOptions3Items = options3Items;

        // 选项1
        wv_option1.setAdapter(new ArrayWheelAdapter(mOptions1Items));// 设置显示数据
        wv_option1.setCurrentItem(0);// 初始化时显示的数据

        // 选项2
        if (mOptions2Items != null) {
            wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(0)));// 设置显示数据
        }
        wv_option2.setCurrentItem(wv_option2.getCurrentItem());// 初始化时显示的数据

        // 选项3
        if (mOptions3Items != null) {
            wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(0).get(0)));// 设置显示数据
        }

        wv_option3.setCurrentItem(wv_option3.getCurrentItem());
        wv_option1.setIsOptions(true);
        wv_option2.setIsOptions(true);
        wv_option3.setIsOptions(true);

        if (this.mOptions2Items == null) {
            wv_option2.setVisibility(View.GONE);
        } else {
            wv_option2.setVisibility(View.VISIBLE);
        }

        if (this.mOptions3Items == null) {
            wv_option3.setVisibility(View.GONE);
        } else {
            wv_option3.setVisibility(View.VISIBLE);
        }

        // 联动监听器
        wheelListener_option1 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int opt2Select = 0;
                if (mOptions2Items == null) {//只有1级联动数据
                    if (optionsSelectChangeListener != null) {
                        optionsSelectChangeListener.onOptionsSelect(wv_option1.getCurrentItem(), 0, 0);
                    }

                } else {
                    if (!isRestoreItem) {
                        opt2Select = wv_option2.getCurrentItem();//上一个opt2的选中位置
                        //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                        opt2Select = opt2Select >= mOptions2Items.get(index).size() - 1 ? mOptions2Items.get(index).size() - 1 : opt2Select;
                    }

                    wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(index)));
                    wv_option2.setCurrentItem(opt2Select);

                    if (mOptions3Items != null) {
                        wheelListener_option2.onItemSelected(opt2Select);
                    } else {//只有2级联动数据，滑动第1项回调
                        if (optionsSelectChangeListener != null) {
                            optionsSelectChangeListener.onOptionsSelect(index, opt2Select, 0);
                        }
                    }

                }
            }
        };

        wheelListener_option2 = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                if (mOptions3Items != null) {
                    int opt1Select = wv_option1.getCurrentItem();
                    opt1Select = opt1Select >= mOptions3Items.size() - 1 ? mOptions3Items.size() - 1 : opt1Select;
                    index = index >= mOptions2Items.get(opt1Select).size() - 1 ? mOptions2Items.get(opt1Select).size() - 1 : index;
                    int opt3 = 0;

                    if (!isRestoreItem) {
                        // wv_option3.getCurrentItem() 上一个opt3的选中位置
                        //新opt3的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                        opt3 = wv_option3.getCurrentItem() >= mOptions3Items.get(opt1Select).get(index).size() - 1 ?
                                mOptions3Items.get(opt1Select).get(index).size() - 1 : wv_option3.getCurrentItem();
                    }

                    wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(wv_option1.getCurrentItem()).get(index)));
                    wv_option3.setCurrentItem(opt3);

                    //3级联动数据实时回调
                    if (optionsSelectChangeListener != null) {
                        optionsSelectChangeListener.onOptionsSelect(wv_option1.getCurrentItem(), index, opt3);
                    }
                } else {//只有2级联动数据，滑动第2项回调
                    if (optionsSelectChangeListener != null) {
                        optionsSelectChangeListener.onOptionsSelect(wv_option1.getCurrentItem(), index, 0);
                    }
                }
            }
        };

        // 添加联动监听
        if (options1Items != null && linkage) {
            wv_option1.setOnItemSelectedListener(wheelListener_option1);
        }

        if (options2Items != null && linkage) {
            wv_option2.setOnItemSelectedListener(wheelListener_option2);
        }

        if (options3Items != null && linkage && optionsSelectChangeListener != null) {
            wv_option3.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    optionsSelectChangeListener.onOptionsSelect(wv_option1.getCurrentItem(), wv_option2.getCurrentItem(), index);
                }
            });
        }
    }


    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
        wv_option3.setCyclic(cyclic);
    }


    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2。
     * 在快速滑动未停止时，点击确定按钮，会进行判断，如果匹配数据越界，则设为0，防止index出错导致崩溃。
     *
     * @return 索引数组
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wv_option1.getCurrentItem();

        if (mOptions2Items != null && mOptions2Items.size() > 0) { // 非空判断
            currentItems[1] = wv_option2.getCurrentItem() > (mOptions2Items.get(currentItems[0]).size() - 1) ? 0 : wv_option2.getCurrentItem();
        } else {
            currentItems[1] = wv_option2.getCurrentItem();
        }

        if (mOptions3Items != null && mOptions3Items.size() > 0) { // 非空判断
            currentItems[2] = wv_option3.getCurrentItem() > (mOptions3Items.get(currentItems[0]).get(currentItems[1]).size() - 1) ? 0 : wv_option3.getCurrentItem();
        } else {
            currentItems[2] = wv_option3.getCurrentItem();
        }

        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (linkage) {
            itemSelected(option1, option2, option3);
        } else {
            wv_option1.setCurrentItem(option1);
            wv_option2.setCurrentItem(option2);
            wv_option3.setCurrentItem(option3);
        }
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {
        if (mOptions1Items != null) {
            wv_option1.setCurrentItem(opt1Select);
        }

        if (mOptions2Items != null) {
            wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(opt1Select)));
            wv_option2.setCurrentItem(opt2Select);
        }

        if (mOptions3Items != null) {
            wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(opt1Select).get(opt2Select)));
            wv_option3.setCurrentItem(opt3Select);
        }
    }


    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        wv_option1.setDividerType(dividerType);
        wv_option2.setDividerType(dividerType);
        wv_option3.setDividerType(dividerType);
    }


    /**
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */
    public void isCenterLabel(boolean isCenterLabel) {
        wv_option1.isCenterLabel(isCenterLabel);
        wv_option2.isCenterLabel(isCenterLabel);
        wv_option3.isCenterLabel(isCenterLabel);
    }

    public void setOptionsSelectChangeListener(OnOptionsSelectListener listener) {
        this.optionsSelectChangeListener = listener;
    }


    /**
     * 设置最大可见数目
     *
     * @param itemsVisible 建议设置为 3 ~ 9之间。
     */
    public void setItemsVisible(int itemsVisible) {
        wv_option1.setItemsVisibleCount(itemsVisible);
        wv_option2.setItemsVisibleCount(itemsVisible);
        wv_option3.setItemsVisibleCount(itemsVisible);
    }

    public void setAlphaGradient(boolean isAlphaGradient) {
        wv_option1.setAlphaGradient(isAlphaGradient);
        wv_option2.setAlphaGradient(isAlphaGradient);
        wv_option3.setAlphaGradient(isAlphaGradient);
    }
}