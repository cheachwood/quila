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
package fr.jcf.pret.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.jcf.pret.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * @author jfélicité
 * 
 */
public class DatePickerFragment extends DialogFragment implements
		OnDateSetListener {
	private EditText dateEditText;

	public DatePickerFragment(EditText dateEditText) {
		super();
		this.dateEditText = dateEditText;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// On affiche la date courante pour initialiser la date
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// On instancie un nouveau composant DatePicherDialog pour affichage
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	/**
	 * Cette méthode est appelée une fois que la date est sélectionnée
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Récupération de la date
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);

		// Formatage de la date
		//SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.format_date));
		
		// Affiche de la date dans l'activité appelante
		dateEditText.setText(sdf.format(cal.getTime()));
	}

}
