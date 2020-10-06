package com.zgzx.metaphysics.controller;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.zgzx.metaphysics.controller.core.RequestPresenter;
import com.zgzx.metaphysics.controller.views.core.IStatusView;
import com.zgzx.metaphysics.model.entity.MasterServiceTypeEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 编辑师傅 特长、擅长
 */
public interface EditMasterSpecialtyController {

    class Presenter extends RequestPresenter<View> {

        private List<MasterServiceTypeEntity> all, user;

        // 获取用户标签，所有标签
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void init() {
            all = new ArrayList<>(Arrays.asList(
                    new MasterServiceTypeEntity(1, "婚姻情感"),
                    new MasterServiceTypeEntity(2, "事业财运")
            ));

            user = new ArrayList<>(
                    Arrays.asList(
                            //new MasterServiceTypeEntity(1, "婚姻情感"),
                            new MasterServiceTypeEntity(2, "事业财运")
                    )
            );

            mView.renderAll(all);
            mView.renderUser(user);

            // 选中
            mView.renderSelected(1);
        }

        // 添加标签
        public void add(int position) {
            MasterServiceTypeEntity entity = all.get(position);

            if (!user.contains(entity)) { // 添加
                user.add(new MasterServiceTypeEntity(entity.getId(), entity.getName()));
                //mView.renderSelected(position);
                mView.renderUser(user);
            }

        }

        // 删除标签
        public void delete(MasterServiceTypeEntity entity) {
            if (user.contains(entity)) { // 添加
                user.remove(entity);
                mView.renderUser(user);
            }
        }

        // 更新标签
        public void update() {

        }

    }

    interface View extends IStatusView {

        // 用户标签
        void renderUser(List<MasterServiceTypeEntity> list);

        // 所有标签
        void renderAll(List<MasterServiceTypeEntity> list);

        // 选中的
        void renderSelected(int... position);
    }
}
