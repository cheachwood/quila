/**
 * Copyright 2013 Clint Cheachwood
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fr.jcf.pret.listeners;

import android.app.Activity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import fr.jcf.pret.R;
import fr.jcf.pret.database.adapters.PretEmpruntDBAdapter;
import fr.jcf.pret.modeles.PretEmprunt;

public class PretEmpruntEnregOnClickListener implements OnMenuItemClickListener {
	private int pretOuEmprunt;
	private View view;
	private PretEmprunt data;

	public PretEmpruntEnregOnClickListener(final int pretOuEmprunt, View view) {
		super();
		this.pretOuEmprunt = pretOuEmprunt;
		this.view = view;
	}

	public PretEmpruntEnregOnClickListener(PretEmprunt pretEmprunt, View currentFocus) {
		this.data = pretEmprunt;
		this.view = currentFocus;
	}

	/**
	 * Permet de créer un objet métier
	 * 
	 * @param view
	 * @return
	 */
	private PretEmprunt creerObjetMetier(View view, PretEmprunt pretEmprunt) {
		View parent = (View) view.getRootView();
		EditText nomEditText = null;
		EditText dateEditText = null;
		EditText objetEditText = null;
		ImageView imageView = null;

		nomEditText = (EditText) parent.findViewById(R.id.pret_contact_EditText);
		dateEditText = (EditText) parent.findViewById(R.id.pret_date_EditText);
		objetEditText = (EditText) parent.findViewById(R.id.pret_objet_EditText);
		imageView = (ImageView) parent.findViewById(R.id.pret_image);

		if (null == pretEmprunt) {
			pretEmprunt = new PretEmprunt();
			pretEmprunt.pretOuEmprunt = this.pretOuEmprunt;
		}
		if (null != data) {
			pretEmprunt.contactId = data.contactId;
		}
		pretEmprunt.contactNom = nomEditText.getText().toString();
		pretEmprunt.date = dateEditText.getText().toString();
		pretEmprunt.objet = objetEditText.getText().toString();
		pretEmprunt.image = String.valueOf(imageView.getTag());
		return pretEmprunt;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		PretEmprunt pret = null;
		PretEmpruntDBAdapter empruntDBAdapter = new PretEmpruntDBAdapter(view.getContext());
		PretEmpruntDBAdapter.open();

		// On enregistre l'objet créé
		if (null == data) {
			pret = creerObjetMetier(view, null);
			empruntDBAdapter.insert(pret);
		} else {
			pret = creerObjetMetier(view, data);
			empruntDBAdapter.update(pret);
		}

		PretEmpruntDBAdapter.close();
		// Ferme la fenêtre en cours
		Activity activity = (Activity) view.getContext();
		activity.finish();
		return false;
	}

}
