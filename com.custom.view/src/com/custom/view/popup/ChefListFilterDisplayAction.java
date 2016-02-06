package com.custom.view.popup;

import java.util.ArrayList;
import java.util.List;

import com.custom.view.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChefListFilterDisplayAction extends PopupWindows{

	public enum FilterListMode{
		
		singleMode,doubleMode;
	}
	private View anchorView;
	ListView groupListView,childListView;
	List<Entry> groupList=new ArrayList<Entry>();

	List<Entry> childList=new ArrayList<Entry>();
	
	FilterAdapter groupFilterAdapter;
	FilterChildAdapter childFilterAdapter;
	AfterOnListItemClickCallBack mcallback;
	/**
	 * @param context
	 */
	public ChefListFilterDisplayAction(Context context,FilterListMode filterListMode,AfterOnListItemClickCallBack callBack) {
		
		super(context);
		this.mcallback=callBack;
		
		switch (filterListMode) {
		case singleMode:
			childListView.setVisibility(View.GONE);
			break;
		case doubleMode:
			childFilterAdapter=new FilterChildAdapter(mContext, childList);
			childListView.setVisibility(View.INVISIBLE);
			childListView.setAdapter(childFilterAdapter);
			childFilterAdapter.notifyDataSetChanged();
			childListView.setOnItemClickListener(childItemClickListener);
			break;
		}
		groupList=new ArrayList<Entry>();
		groupFilterAdapter=new FilterAdapter(mContext, groupList,true);
		groupListView.setAdapter(groupFilterAdapter);
		groupFilterAdapter.notifyDataSetChanged();
		groupListView.setOnItemClickListener(groupItemClickListener);
	}
	@Override
	protected View getRootViewLayout() {
		
		View layoutView=LayoutInflater.from(mContext).inflate(R.layout.view_chef_list_filter_layout,null);
		groupListView=	(ListView)layoutView.findViewById(R.id.filter_layout_listview1);
		childListView=(ListView)layoutView.findViewById(R.id.filter_layout_listview2);
		return layoutView;
	}
	public void setData(List<Entry> filterItemlist){
		if(filterItemlist!=null){
			groupList.addAll(filterItemlist);
			groupFilterAdapter.notifyDataSetChanged();
			if(groupList.size()>0){
				if(mcallback!=null){
					mcallback.afterOnItemClick(anchorView,groupList.get(0));
				}
			}
		}
	}
	
	
	public Entry getFilterItemByTitle(String title){
		Entry Entry=	new Entry();
		Entry.setTitle(title);
		if(groupList!=null&&groupList.contains(Entry)){
			
			return groupList.get(groupList.indexOf(Entry));
		}
		if(childList!=null&&childList.contains(Entry)){
			
			return childList.get(childList.indexOf(Entry));
		}
		return null;
		
	}
	OnItemClickListener groupItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			groupFilterAdapter.setSelectedPosition(arg2);
			groupFilterAdapter.notifyDataSetChanged();
			Entry Entry=(Entry)arg0.getItemAtPosition(arg2);
			
			if(Entry.getChildList()!=null&&!Entry.getChildList().isEmpty()){
				childListView.setVisibility(View.VISIBLE);
				childList.clear();
				childList.addAll(Entry.getChildList());
				childFilterAdapter.setSelectedPosition(-1);
				childFilterAdapter.notifyDataSetChanged();
			}else{
				if(childList!=null&&childFilterAdapter!=null){
					childList.clear();
					childFilterAdapter.setSelectedPosition(-1);
					childFilterAdapter.notifyDataSetChanged();
				}
				dismiss();
				if(mcallback!=null){
					mcallback.afterOnItemClick(anchorView,Entry);
				}
			}
		}
		
	};
	OnItemClickListener childItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Entry Entry=(Entry)arg0.getItemAtPosition(arg2);
			childFilterAdapter.setSelectedPosition(arg2);
			childFilterAdapter.notifyDataSetChanged();
			dismiss();
			if(mcallback!=null){
				mcallback.afterOnItemClick(anchorView,Entry);
			}
		}
		
	};
	@Override
	protected void setWindowLayoutParam() {
		
		mWindow.setWidth(WindowManager.LayoutParams.FILL_PARENT);
		mWindow.setHeight(dip2px(mContext,200));
//		mWindow.setHeight(WindowManager.LayoutParams.FILL_PARENT);
		
	}
	 private int dip2px(Context context, float pxValue) {  
	        final float scale = context.getResources().getDisplayMetrics().density;  
	        return (int) (pxValue * scale + 0.5f);  
	    }  
	/* (non-Javadoc)
	 * @see com.Apricotforest.ActionBar.PopupWindows#show(android.view.View)
	 */
	public void show(View anchor) {
		
		super.show(anchor);
		this.anchorView=anchor;
	}
	
	public interface AfterOnListItemClickCallBack{
		
		public void afterOnItemClick(View anchorView,Entry filterItem);
	}
	
	public class FilterAdapter extends BaseAdapter{

		
		private Context mcontext;
		private List<Entry> mList;
		private LayoutInflater mInflater;
		private int selectedPosition=-1;
		private boolean isItemShowLogo;
		private class FilterHolder{
			
			TextView titleTextView;
		}
		/**
		 * 
		 */
		public FilterAdapter(Context context,List<Entry> list) {
			
			this(context, list,false);
		}
		
		public FilterAdapter(Context context,
				List<Entry> list,boolean isItemShowLogo) {
			this.mcontext=context;
			this.mList=list;
			this.isItemShowLogo=isItemShowLogo;
		    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			
			return mList.size();
		}
		
		public int getSelectedPosition() {
			return selectedPosition;
		}
		public void setSelectedPosition(int selectedPosition) {
			this.selectedPosition = selectedPosition;
		}
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			
			FilterHolder viewHolder;
			if(convertView!=null){
				viewHolder = (FilterHolder) convertView.getTag();
	        	 }else{
	        		convertView= mInflater.inflate(R.layout.view_chef_list_filter_item, arg2, false);
	    			viewHolder=new FilterHolder();
		    		viewHolder.titleTextView = (TextView)convertView.findViewById(R.id.filter_item_txt);
		    		convertView.setTag(viewHolder);
	        	 }
			if(getSelectedPosition()==arg0){
				convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
			}else{
				convertView.setBackgroundResource(R.drawable.view_filter_item_group_bg);
			}
			Entry Entry=mList.get(arg0);
			viewHolder.titleTextView.setText(Entry.getTitle());
			return convertView;
		}
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			
			return mList.get(position);
		}
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			
			return mList.get(position).getId();
		}
		
	}
	
	public class FilterChildAdapter extends BaseAdapter{

		
		private Context mcontext;
		private List<Entry> mList;
		private LayoutInflater mInflater;
		private int selectedPosition=-1;
		private boolean isItemShowLogo;
		private class MagzineFilterHolder{
			
			TextView titleTextView;
		}
		/**
		 * 
		 */
		public FilterChildAdapter(Context context,List<Entry> list) {
			
			this(context, list,false);
		}
		
		public FilterChildAdapter(Context context,
				List<Entry> list,boolean isItemShowLogo) {
			this.mcontext=context;
			this.mList=list;
			this.isItemShowLogo=isItemShowLogo;
		    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
		}
		@Override
		public int getCount() {
			
			return mList.size();
		}
		
		public int getSelectedPosition() {
			return selectedPosition;
		}
		public void setSelectedPosition(int selectedPosition) {
			this.selectedPosition = selectedPosition;
		}
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			
			MagzineFilterHolder viewHolder;
			if(convertView!=null){
				viewHolder = (MagzineFilterHolder) convertView.getTag();
	        	 }else{
	        		convertView= mInflater.inflate(R.layout.view_chef_list_filter_item, arg2, false);
	    			viewHolder=new MagzineFilterHolder();
		    		viewHolder.titleTextView = (TextView)convertView.findViewById(R.id.filter_item_txt);
		    		convertView.setTag(viewHolder);
	        	 }
			Entry Entry=mList.get(arg0);
			viewHolder.titleTextView.setText(Entry.getTitle());
			return convertView;
		}
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position) {
			
			return mList.get(position);
		}
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			
			return mList.get(position).getId();
		}
		
	}
	
	
	public static class Entry {
		
		int id;
		String title;
		Object object;
		List<Entry> childList=new ArrayList<ChefListFilterDisplayAction.Entry>();
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public List<Entry> getChildList() {
			return childList;
		}
		public void setChildList(List<Entry> childList) {
			this.childList = childList;
		}
		public Object getObject() {
			return object;
		}
		public void setObject(Object object) {
			this.object = object;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((title == null) ? 0 : title.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			return true;
		}
		
	}
}
