package com.custom.base.fra;

import com.custom.base.analysis.MobStatisticUtils;
import com.custom.view.dialog.ProgressDialog;

import android.support.v4.app.Fragment;

public class AbsBaseFragment extends Fragment {

	private ProgressDialog progressDialog = null;

	protected void showProgressBar() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(getActivity());
		}
		if (!progressDialog.isShowing() && !getActivity().isFinishing())
			progressDialog.show();
	}

	protected void dismissProgressBar() {

		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	

	@Override
	public void onResume() {
		super.onResume();
		MobStatisticUtils.onFragResume(this); 
		
	}

	@Override
	public void onPause() {
		super.onPause();
		MobStatisticUtils.onFragResume(this); 
	}
}
