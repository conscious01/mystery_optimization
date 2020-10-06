package com.zgzx.metaphysics.city_time_picker.xpopupext.adapter;



import com.zgzx.metaphysics.city_time_picker.wheel.WheelAdapter;

import java.util.List;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {
	

	// items
	private List<T> items;

	/**
	 * Constructor
	 * @param items the items
	 */
	public ArrayWheelAdapter(List<T> items) {
		this.items = items;
	}
	
	@Override
	public Object getItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.get(index);
		}

		return "";
	}

	@Override
	public int getItemsCount() {
		return items.size();
	}

	@Override
	public int indexOf(Object o){
		return items.indexOf(o);
	}

}
