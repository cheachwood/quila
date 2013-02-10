package fr.jcf.pret.fragments;

import fr.jcf.pret.R;
import fr.jcf.pret.utils.UtilPret;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RappelsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return UtilPret.chargerLayout(inflater, container,
				R.layout.rappels_fragment);

	}

}
