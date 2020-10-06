package com.zgzx.metaphysics.city_time_picker.xpopupext.popup;

import android.content.Context;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.lxj.xpopup.core.BottomPopupView;
import com.zgzx.metaphysics.city_time_picker.R;
import com.zgzx.metaphysics.city_time_picker.wheel.WheelView;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.DomesticJsonBean;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.ForeignJsonBean;
import com.zgzx.metaphysics.city_time_picker.xpopupext.listener.CityPickerListener;
import com.zgzx.metaphysics.city_time_picker.xpopupext.view.WheelOptions;

import java.util.ArrayList;
import java.util.List;

public class CityPickerPopup extends BottomPopupView {

    private List<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private CityPickerListener cityPickerListener;

    private WheelOptions wheelOptions;

    public CityPickerPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout._xpopup_ext_city_picker;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView btnConfirm = findViewById(R.id.btnConfirm);
        RadioGroup cityType = findViewById(R.id._xpopup_city_type);

        cityType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id._xpopup_domestic) { // 国内
                setDomestic(true);

            } else if (checkedId == R.id._xpopup_foreign) { // 国外
                setDomestic(false);
            }
        });

        // 确定
        btnConfirm.setOnClickListener(v -> {
            if (cityPickerListener != null) {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                int options1 = optionsCurrentItems[0];
                int options2 = optionsCurrentItems[1];
                int options3 = optionsCurrentItems[2];

                if (isDomestic) {
                    // 国内
                    cityPickerListener.onCityConfirm(
                            getContext().getResources().getString(R.string._xpopup_ext_domestic),
                            options1Items.get(options1),
                            options2Items.get(options1).get(options2),
                            options3Items.get(options1).get(options2).get(options3),
                            v
                    );

                } else {
                    // 国外
                    cityPickerListener.onCityConfirm(
                            "",
                            options1Items.get(options1),
                            options2Items.get(options1).get(options2),
                            "",
                            v
                    );
                }

            }

            dismiss();
        });

        wheelOptions = new WheelOptions(findViewById(R.id.citypicker), false);
        if (cityPickerListener != null) {
//            wheelOptions.setOptionsSelectChangeListener(new OnOptionsSelectListener() {
//                @Override
//                public void onOptionsSelect(int options1, int options2, int options3) {
//                    if (isDomestic) {
//                        cityPickerListener.onCityChange(
//                                options1Items.get(options1),
//                                options2Items.get(options1).get(options2),
//                                options3Items.get(options1).get(options2).get(options3)
//                        );
//                    } else {
//                        cityPickerListener.onCityChange(
//                                options1Items.get(options1),
//                                options2Items.get(options1).get(options2),
//                                null
//                        );
//                    }
//                }
//            });
        }

        wheelOptions.setAlphaGradient(true);
        wheelOptions.setCyclic(false);
        wheelOptions.setDividerType(WheelView.DividerType.FILL);
        wheelOptions.isCenterLabel(false);

        if (!options1Items.isEmpty() && !options2Items.isEmpty() && !options3Items.isEmpty()) {
            // 有数据直接显示
            if (wheelOptions != null) {
                wheelOptions.setPicker(options1Items, options2Items, options3Items);
                wheelOptions.setCurrentItems(0, 0, 0);
            }
        }

        // 选择国内
        cityType.check(R.id._xpopup_domestic);
    }

    public CityPickerPopup setCityPickerListener(CityPickerListener listener) {
        this.cityPickerListener = listener;
        return this;
    }

    private boolean isDomestic;

    public void setDomestic(boolean isDomestic) {
        this.isDomestic = isDomestic;
        initView();
    }


    // 国内数据
    private List<DomesticJsonBean> mDomesticList;

    public void setDomesticData(List<DomesticJsonBean> list) {
        mDomesticList = list;
    }

    // 国外数据
    private List<ForeignJsonBean> mForeignList;

    public void setForeignData(List<ForeignJsonBean> list) {
        mForeignList = list;
    }

    private void initView() {
        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        if (isDomestic) {

            // 国内数据
            for (DomesticJsonBean bean : mDomesticList) {
                options1Items.add(bean.getName());
                ArrayList<String> cityList = new ArrayList<>(); // 该省的城市列表（第二级）
                ArrayList<ArrayList<String>> areaList = new ArrayList<>(); // 该省的所有地区列表（第三极）

                List<DomesticJsonBean.CityBean> cityBeanList = bean.getCity();

                if (cityBeanList != null){
                    for (DomesticJsonBean.CityBean cityBean : cityBeanList) {
                        cityList.add(cityBean.getName());
                        areaList.add(new ArrayList<>(cityBean.getArea()));
                    }
                }

                options2Items.add(cityList);
                options3Items.add(areaList);
            }

            wheelOptions.setPicker(options1Items, options2Items, options3Items);
            wheelOptions.setCurrentItems(0, 0, 0);
        } else {

            // 海外数据
            for (ForeignJsonBean bean : mForeignList) {
                options1Items.add(bean.getName());
                options2Items.add(new ArrayList<>(bean.getCity()));
                options3Items.add(new ArrayList<>());
            }

            wheelOptions.setPicker(options1Items, options2Items, null);
            wheelOptions.setCurrentItems(0, 0, 0);
        }


    }


}
