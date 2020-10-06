package com.zgzx.metaphysics.model.entity;

import com.zgzx.metaphysics.ui.fragments.FindFragment;

import java.util.List;
import java.util.Objects;

/**
 * 师傅服务类型
 *
 * @see FindFragment#result(List)
 */
public class MasterServiceTypeEntity {

    private int id;
    private String name;

    public MasterServiceTypeEntity() {
    }

    public MasterServiceTypeEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterServiceTypeEntity that = (MasterServiceTypeEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
