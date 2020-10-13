package com.zgzx.metaphysics.model.event;

import com.zgzx.metaphysics.model.entity.AddfortuneDataEntity;

public class OnGetFourtuneEvent {
    AddfortuneDataEntity addfortuneDataEntity;

    public OnGetFourtuneEvent(AddfortuneDataEntity addfortuneDataEntity) {
        this.addfortuneDataEntity = addfortuneDataEntity;
    }

    public AddfortuneDataEntity getAddfortuneDataEntity() {
        return addfortuneDataEntity;
    }

    public void setAddfortuneDataEntity(AddfortuneDataEntity addfortuneDataEntity) {
        this.addfortuneDataEntity = addfortuneDataEntity;
    }
}
