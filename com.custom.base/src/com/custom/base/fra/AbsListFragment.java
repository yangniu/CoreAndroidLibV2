package com.custom.base.fra;

import java.util.List;

import com.custom.base.adapter.AbsListAdapter;
import com.custom.view.widgets.OnHeaderRefreshListener;
import com.custom.view.widgets.OnScrollLoadMoreListener;
import com.custom.view.widgets.UpFreshListView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.custom.base.R;
@Deprecated
public abstract class AbsListFragment<T> extends AbsBaseFragment implements
		OnItemClickListener, OnHeaderRefreshListener, OnScrollLoadMoreListener {
	private UpFreshListView mlistview;
	private AbsListAdapter<?, ?> listItemAdapter;
	private View viewLayout = null;
	protected LinearLayout  emptyLayout;
	protected ImageView emptyImgView;
	protected TextView referView1;
	protected TextView referView2;
	protected Button emptyBtn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		viewLayout = inflater.inflate(R.layout.com_listview_layout, container,false);
		initView(viewLayout);
		return viewLayout;
	}

	protected void initView(View v) {

		mlistview = (UpFreshListView) v.findViewById(R.id.common_listview);
		View headerView=getListHeaderView();
		if(headerView!=null){
			mlistview.addHeaderView(headerView,null, false);
		}
		listItemAdapter=createAbsListAdapter();
		if(listItemAdapter!=null){
			mlistview.setAdapter(listItemAdapter);
			listItemAdapter.notifyDataSetChanged();
		}
		mlistview.setOnHeaderRefreshListener(this);
		mlistview.setOnScrollLoadMoreListener(this);
		mlistview.setOnItemClickListener(this);
		emptyLayout =(LinearLayout)v.findViewById(R.id.common_listview_empty_layout);
		View emptyView=LayoutInflater.from(getActivity()).inflate(R.layout.com_list_empty_view,emptyLayout, false);
		emptyImgView=(ImageView)emptyView.findViewById(R.id.list_empty_view_iv);
		referView1=(TextView)emptyView.findViewById(R.id.list_empty_view_tv_refer);
		referView2 =(TextView)emptyView.findViewById(R.id.list_empty_view_tv_refer2);
		emptyBtn=(Button)emptyView.findViewById(R.id.list_empty_view_btn);
		emptyLayout.addView(emptyView);
		emptyLayout.setVisibility(View.GONE);
	}
	
	public void setEmptyViewShow(boolean isShow){
		
		if(emptyLayout!=null){
			emptyLayout.setVisibility(isShow?View.VISIBLE:View.GONE);
		}
	}
	
	public void setEmptyViewText(int resId,String refer1,String refer2){
		emptyImgView.setVisibility(resId==0?View.GONE:View.VISIBLE);
		emptyImgView.setImageResource(resId);
		referView1.setVisibility(TextUtils.isEmpty(refer1)?View.GONE:View.VISIBLE);
		referView1.setText(refer1);
		referView2.setVisibility(TextUtils.isEmpty(refer2)?View.GONE:View.VISIBLE);
		referView2.setText(refer2);
	}
	protected abstract AbsListAdapter<?, ?> createAbsListAdapter();
	
	
	protected View getListHeaderView(){
		return null;
		
	}
	
	@Override  
    public void setUserVisibleHint(boolean isVisibleToUser) {  
       
       if (isVisibleToUser &&this.isResumed()){
           //相当于Fragment的onResume  
    	   onlazyLoad();
       } else {  
           //相当于Fragment的onPause  
       } 
       super.setUserVisibleHint(isVisibleToUser);
    } 
	
	public void onlazyLoad() {
		
	}
	public UpFreshListView getFreshListView(){
		
		return mlistview;
	}
	
	
	protected AbsListAdapter<?, ?> getCurrentListAdapter(){
		
		return listItemAdapter;
	}
	
	protected int getListItemCount(){
		
		return listItemAdapter!=null?listItemAdapter.getCount():0;
	}
	
	/***
	 * 
	 * @param absListview
	 * @param packagelist
	 * @param numRows
	 * @param niufei
	 * @return void
	 * @throws
	 */
	public void setFooterViewVisible(UpFreshListView absListview,List<?> list,int numRows){
		
		if(absListview==null){
			throw new RuntimeException("absListView is empty !!");
		}
		if (list.size() < numRows&&list.size()!=0) {
			absListview.addAutoFooterView();
		} else {
			absListview.removeAutoFooterView();
		}
		absListview.onRefreshComplete();
	}
	
	protected abstract String getPageName();
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	
		if(listItemAdapter!=null){
			listItemAdapter.clearDisplayedImages();
		}
		
	}
}
