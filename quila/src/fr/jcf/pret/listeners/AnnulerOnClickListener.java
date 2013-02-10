package fr.jcf.pret.listeners;

import android.app.Activity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;

public class AnnulerOnClickListener implements OnMenuItemClickListener {
	private View view;
	
	public AnnulerOnClickListener(View currentFocus) {
		this.view = currentFocus;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Activity activity = (Activity) view.getContext();
		activity.finish();
		return false;
	}

}
