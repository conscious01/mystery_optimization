package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;


import com.lxj.xpopup.XPopup;

import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.DomesticJsonBean;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.ForeignJsonBean;
import com.zgzx.metaphysics.city_time_picker.xpopupext.listener.CityPickerListener;
import com.zgzx.metaphysics.city_time_picker.xpopupext.popup.CityPickerPopup;
import com.zgzx.metaphysics.model.DataRepository;

import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.network.rx.SchedulersTransformer;


import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


/**
 * 出生地点，中国、海外
 */
public class BirthPlaceDialog {

    public static void show(Context context, CityPickerListener listener){
        CityPickerPopup popup = new CityPickerPopup(context);
        popup.setCityPickerListener(listener);

        Disposable subscribe = DataRepository.getInstance()
                .areaChina()

                .flatMap((Function<BasicResponseEntity<List<DomesticJsonBean>>, ObservableSource<BasicResponseEntity<List<ForeignJsonBean>>>>) entity -> {
                    popup.setDomesticData(entity.getData());
                    return DataRepository.getInstance().areaOversea();
                })

                .compose(SchedulersTransformer.transformer())
                .subscribe(entity -> {
                    popup.setForeignData(entity.getData());

                    new XPopup.Builder(context)
                            .asCustom(popup)
                            .show();
                });
    }

}
