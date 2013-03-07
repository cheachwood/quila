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
package fr.jcf.pret.database.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import fr.jcf.pret.contrats.PretEmpruntContrat;
import fr.jcf.pret.database.adapters.interfaces.CrudDBAdapter;
import fr.jcf.pret.modeles.PretEmprunt;

/**
 * Adapter Database pour la table pret_emprunt
 * 
 * @author jfélicité
 * 
 */
public class PretEmpruntDBAdapter extends CrudDBAdapter<PretEmprunt> {
	private int pretOuEmprunt = 2;

	public int getPretOuEmprunt() {
		return pretOuEmprunt;
	}

	public void setPretOuEmprunt(int pretOuEmprunt) {
		this.pretOuEmprunt = pretOuEmprunt;
	}

	public PretEmpruntDBAdapter(Context context) {
		super(context);
	}

	/**
	 * Permet d'insérer une donnée dans la base de données
	 */
	@Override
	public long insert(PretEmprunt domaineObject) {
		ContentValues values = creerContentValue(domaineObject);
		long i = bdd.insert(PretEmpruntContrat.TABLE_NOM, null, values);
		return i;
	}

	/**
	 * Permet de mettre à jour une donnée dans la base de données
	 */
	@Override
	public long update(PretEmprunt domaineObject) {
		ContentValues values = creerContentValue(domaineObject);
		String[] params = { String.valueOf(domaineObject.id) };
		long i = bdd.update(PretEmpruntContrat.TABLE_NOM, values, PretEmpruntContrat._ID + " = ?", params);
		return i;
	}

	/**
	 * Permet de supprimer une donnée dans la base de données
	 */
	@Override
	public long delete(int identifiant) {
		String[] params = { String.valueOf(identifiant) };
		return bdd.delete(PretEmpruntContrat.TABLE_NOM, PretEmpruntContrat._ID + " = ?", params);
	}

	/**
	 * Retourne tous les objets PretEmprunts en fonction du flag pret_emprunt Si pret_emprunt = 0 ==> renvoie tous les prets Si pret_emprunt = 1 ==>
	 * renvoie tous les emprunts
	 */
	@Override
	public List<PretEmprunt> fetchAll() {
		if (pretOuEmprunt == 2) {
			Log.d(PretEmpruntDBAdapter.class.getCanonicalName(), "Attention il faut définir si c'est un pret setPretOuEmprunt(0) "
					+ "ou un emprunt setPretOuEmprunt(1) sur une instance de  PretEmpruntDBAdapter");
			return null;
		}

		List<PretEmprunt> emprunts = null;
		Cursor cursor = null;
		String[] params = { String.valueOf(pretOuEmprunt) };

		// On récupère tous les enregistrements de la table PretEmprunt avec
		// tous les champs, triée par Date
		cursor = bdd.query(PretEmpruntContrat.TABLE_NOM, PretEmpruntContrat.PROJECTION_ALL, PretEmpruntContrat.PRET_EMPRUNT + "=?", params, null,
				null, null);

		if (cursor != null) {
			emprunts = new ArrayList<PretEmprunt>();

			// On se positionne sur le premier enregistrement
			cursor.moveToFirst();

			// On vérifie s'il existe un enregistrement suivant
			while (!cursor.isAfterLast()) {

				// On crée un objet PretEmprunt
				PretEmprunt pretEmprunt = creerPretEmprunt(cursor);
				emprunts.add(pretEmprunt);

				// On passe à l'enregistrement suivant
				cursor.moveToNext();
			}

			// On ferme le curseur si il n'a pas été préalablement fermé
			if (!cursor.isClosed()) {
				cursor.close();
			}
		}
		return emprunts;
	}

	/**
	 * Retourne uniquement l'objet PretEmprunt dont l'index est spécifié
	 */
	@Override
	public PretEmprunt fetchById(String index) {
		Cursor cursor = null;
		String[] params = { String.valueOf(PretEmpruntContrat._ID) };
		PretEmprunt pretEmprunt = null;

		// On récupére tous les enregistrements de la table PretEmprunt
		cursor = bdd.query(PretEmpruntContrat.TABLE_NOM, PretEmpruntContrat.PROJECTION_ALL, PretEmpruntContrat._ID + "=?", params, null, null, null);

		if (cursor != null) {
			// On se positionne sur le premier enregistrement
			cursor.moveToFirst();

			// On crée un objet PretEmprunt
			pretEmprunt = creerPretEmprunt(cursor);

			// On ferme le curseur si il n'a pas été préalablement fermé
			if (!cursor.isClosed()) {
				cursor.close();
			}
		}
		return pretEmprunt;
	}

	/**
	 * Permet de crée un objet ContentValues
	 * 
	 * @param domaineObject
	 * @return
	 */
	private ContentValues creerContentValue(PretEmprunt domaineObject) {
		ContentValues values = new ContentValues();
		values.put(PretEmpruntContrat.CONTACT_ID, domaineObject.contactId);
		values.put(PretEmpruntContrat.CONTACT_NOM, domaineObject.contactNom);
		values.put(PretEmpruntContrat.DATE, domaineObject.date);
		values.put(PretEmpruntContrat.IMAGE_OBJET, domaineObject.image);
		values.put(PretEmpruntContrat.OBJET, domaineObject.objet);
		values.put(PretEmpruntContrat.PRET_EMPRUNT, domaineObject.pretOuEmprunt);
		values.put(PretEmpruntContrat.ALARM, domaineObject.alarm);
		return values;
	}

	/**
	 * Création d'un objet métier PretEmprunt
	 * 
	 * @param cursor
	 * @return
	 */
	private PretEmprunt creerPretEmprunt(Cursor cursor) {
		PretEmprunt pretEmprunt = new PretEmprunt();

		pretEmprunt.id = cursor.getInt(cursor.getColumnIndex(PretEmpruntContrat._ID));

		if (null != cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.CONTACT_ID))) {
			pretEmprunt.contactId = cursor.getInt(cursor.getColumnIndex(PretEmpruntContrat.CONTACT_ID));
		}
		if (null != cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.CONTACT_NOM))) {
			pretEmprunt.contactNom = cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.CONTACT_NOM));
		}
		if (null != cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.DATE))) {
			pretEmprunt.date = cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.DATE));
		}
		if (null != cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.IMAGE_OBJET))) {
			pretEmprunt.image = cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.IMAGE_OBJET));
		}
		if (null != cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.OBJET))) {
			pretEmprunt.objet = cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.OBJET));
		}
		if (null != cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.PRET_EMPRUNT))) {
			pretEmprunt.pretOuEmprunt = Integer.parseInt(cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.PRET_EMPRUNT)));
		}
		if (null != cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.ALARM))) {
			pretEmprunt.alarm = Integer.parseInt(cursor.getString(cursor.getColumnIndex(PretEmpruntContrat.ALARM)));
		}
		return pretEmprunt;
	}

}
