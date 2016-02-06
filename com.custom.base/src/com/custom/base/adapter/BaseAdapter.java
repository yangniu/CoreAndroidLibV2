package com.custom.base.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<T> mDataList = new ArrayList<T>();

	public BaseAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 添加单项
	 * 
	 * @param object
	 */
	public void add(T object) {
		if (null != object) {
			mDataList.ensureCapacity(getCount() + 1);
			mDataList.add(object);
		}
	}

	/**
	 * 添加单项
	 * 
	 * @param index
	 * @param object
	 */
	public void add(int index, T object) {
		if (null != object) {
			mDataList.ensureCapacity(getCount() + 1);
			mDataList.add(index, object);
		}
	}

	/**
	 * 添加子集
	 * 
	 * @param objects
	 */
	public void add(List<T> objects) {
		if (null != objects && objects.size() > 0) {
			mDataList.ensureCapacity(getCount() + objects.size());
			mDataList.addAll(objects);
		}
	}

	/**
	 * 添加子集
	 * 
	 * @param index
	 * @param objects
	 */
	public void add(int index, List<T> objects) {
		if (null != objects && objects.size() > 0) {
			mDataList.ensureCapacity(getCount() + objects.size());
			mDataList.addAll(index, objects);
		}
	}

	/**
	 * 删除单项
	 * 
	 * @param object
	 */
	public void remove(T object) {
		if (null != object) {
			mDataList.remove(object);
			mDataList.ensureCapacity(getCount());
		}
	}

	/**
	 * 删除单项
	 * 
	 * @param position
	 */
	public void remove(int position) {
		if (position >= 0 && position < getCount()) {
			mDataList.remove(position);
			mDataList.ensureCapacity(getCount());
		}
	}

	/**
	 * 删除子集
	 * 
	 * @param objects
	 */
	public void remove(List<T> objects) {
		if (null != objects && objects.size() > 0) {
			mDataList.removeAll(objects);
			mDataList.ensureCapacity(getCount());
		}
	}

	/**
	 * 清空
	 */
	public void clear() {
		mDataList.clear();
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public T getItem(int position) {
		return (position >= 0 && position < getCount()) ? mDataList.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getConvertView(position, convertView, mLayoutInflater, parent);
	}

	/**
	 * 替代getView方法，用于创建子布局
	 * 
	 * @说明 实现该方法，不需重写getview方法
	 * @param position
	 * @param convertView
	 * @param layoutInflater
	 * @param parent
	 * @return
	 */
	public abstract View getConvertView(int position, View convertView, LayoutInflater layoutInflater,
			ViewGroup parent);
}
