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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import fr.jcf.pret.R;
import fr.jcf.pret.activities.PretEmpruntActivity;
import fr.jcf.pret.alarms.NotificationAlarm;
import fr.jcf.pret.contrats.PretEmpruntContrat;
import fr.jcf.pret.database.adapters.PretEmpruntDBAdapter;
import fr.jcf.pret.modeles.PretEmprunt;

public final class PretEmpruntMultiChoiceModeListener implements MultiChoiceModeListener {
	private Context context;
	private ListView listView;
	private List<PretEmprunt> pretEmpruntsSelect;
	private List<PretEmprunt> pretEmprunts;
	private int position;

	public PretEmpruntMultiChoiceModeListener() {
		super();
		pretEmpruntsSelect = new ArrayList<PretEmprunt>();
	}

	public PretEmpruntMultiChoiceModeListener(Context applicationContext, ListView listView, List<PretEmprunt> pretEmprunts) {
		this();
		this.context = applicationContext;
		this.listView = listView;
		this.pretEmprunts = pretEmprunts;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// Inflate the menu for the CAB
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.contextuel_menu, menu);
		return true;
	}

	/**
	 * Méthode levée lorsqu'un bouton de la barre d'action
	 */
	@Override
	public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
		PretEmpruntDBAdapter empruntDBAdapter = new PretEmpruntDBAdapter(this.context);
		// Respond to clicks on the actions in the CAB
		switch (item.getItemId()) {
		case R.id.menu_edit:
			PretEmprunt pretEmpruntSelect = pretEmpruntsSelect.get(0);
			Intent pretEmpruntActivityIntent = new Intent(this.context, PretEmpruntActivity.class);
			pretEmpruntActivityIntent.putExtra("pretEmprunt", pretEmpruntSelect.pretOuEmprunt);
			pretEmpruntActivityIntent.putExtra("data", pretEmpruntSelect);

			pretEmpruntActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.context.getApplicationContext().startActivity(pretEmpruntActivityIntent);
			return true;
		case R.id.menu_delete:
			long nombreLignesSupprimer = 0;
			
			PretEmpruntDBAdapter.open();

			// On parcours tous les éléments sélectionnés pour suppression
			for (PretEmprunt pretEmprunt : pretEmpruntsSelect) {
				nombreLignesSupprimer = empruntDBAdapter.delete(pretEmprunt.id);
				// On supprime également de la liste qui est mappée sur le listview
				pretEmprunts.remove(pretEmprunt);
			}
			PretEmpruntDBAdapter.close();

			if (nombreLignesSupprimer > 0) {
				Toast.makeText(this.context, pretEmpruntsSelect.size() + " élément(s) supprimé(s)", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this.context, "Aucun élément supprimé", Toast.LENGTH_LONG).show();
			}

			// On vide la liste des éléments sélectionnés
			pretEmpruntsSelect.clear();
			actionMode.finish();
			return true;
		case R.id.menu_alarm:
			PretEmprunt pretEmpruntSelectAlarm = pretEmpruntsSelect.get(0);
			pretEmpruntSelectAlarm.alarm = PretEmpruntContrat.ALARM_ON;
			
			PretEmpruntDBAdapter.open();
			empruntDBAdapter.update(pretEmpruntSelectAlarm);
			PretEmpruntDBAdapter.close();
			
			// Création de l'alarme qui doit lever la notification
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			
			// On passe l'élément qui doit être affichée dans la notification
			Intent intent = new Intent(context, NotificationAlarm.class);
			intent.putExtra("pretEmprunt", pretEmpruntSelectAlarm);
			intent.setData( Uri.parse("alarm://" + pretEmpruntSelectAlarm.toString()));
			intent.setAction(pretEmpruntSelectAlarm.toString());
			
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
			SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.format_date));
			Date date = null;
			try {
				date = (Date) dateFormat.parse(pretEmpruntSelectAlarm.date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// On indique quand l'alarme doit se déclencher
			alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime() - 216000000, pendingIntent);
			
			if(pretEmprunts.contains(pretEmpruntSelectAlarm)){
				pretEmprunts.remove(pretEmpruntSelectAlarm);
				pretEmprunts.add(pretEmpruntSelectAlarm);
				
				View v = listView.getChildAt(position);
				ImageView alarmButton = (ImageView) v.findViewById(R.id.image_alarm);
				alarmButton.setImageResource(R.drawable.ic_alarms);
				listView.refreshDrawableState();
			}
			
			actionMode.finish();
			return true;
		case R.id.menu_delete_alarm:
			PretEmprunt pretEmpruntDeleteAlarm = pretEmpruntsSelect.get(0);
			pretEmpruntDeleteAlarm.alarm = PretEmpruntContrat.ALARM_OFF;
			
			PretEmpruntDBAdapter.open();
			empruntDBAdapter.update(pretEmpruntDeleteAlarm);
			PretEmpruntDBAdapter.close();
			
			// Création de l'alarme qui doit lever la notification
			AlarmManager alarmManagerDelete = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			
			// On passe l'élément qui doit être affichée dans la notification
			Intent intentDelete = new Intent(context, NotificationAlarm.class);
			intentDelete.putExtra("pretEmprunt", pretEmpruntDeleteAlarm);
			intentDelete.setData( Uri.parse("alarm://" + pretEmpruntDeleteAlarm.toString()));
			intentDelete.setAction(pretEmpruntDeleteAlarm.toString());
			
			PendingIntent pendingIntentDelete = PendingIntent.getBroadcast(context, 0, intentDelete, PendingIntent.FLAG_ONE_SHOT);

			// On désactive l'alarme sélectionnée
			alarmManagerDelete.cancel(pendingIntentDelete);
			
			if(pretEmprunts.contains(pretEmpruntDeleteAlarm)){
				pretEmprunts.remove(pretEmpruntDeleteAlarm);
				pretEmprunts.add(pretEmpruntDeleteAlarm);
				
				View v = listView.getChildAt(position);
				ImageView alarmButton = (ImageView) v.findViewById(R.id.image_alarm);
				alarmButton.setImageResource(android.R.color.transparent);
				listView.refreshDrawableState();
			}
			
			actionMode.finish();
			return true;
		default:
			return false;
		}
	}

	/**
	 * Méthode levée lorsque un élément est sélectionné dans le listview
	 */
	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
		this.position = position;

		// On alimente la liste des éléments sélectionnés pour traitement
		PretEmprunt pretEmprunt = (PretEmprunt) listView.getItemAtPosition(position);
		pretEmpruntsSelect.add(pretEmprunt);

		// On affiche dans l'entête de l'application le nombre d'éléments sélectionnés
		int itemCount = listView.getCheckedItemCount();
		if (itemCount == 1) {
			mode.setSubtitle("1 sélection");
			// Affiche le bouton d'édition menu_popup_edit du menu contextuel
			mode.getMenu().findItem(R.id.menu_edit).setVisible(true);
			
			// Afiche les boutons d'alarme en fonction du context
			if(pretEmprunt.alarm == PretEmpruntContrat.ALARM_ON){
				mode.getMenu().findItem(R.id.menu_alarm).setVisible(false);	
				mode.getMenu().findItem(R.id.menu_delete_alarm).setVisible(true);	
			}else{
				mode.getMenu().findItem(R.id.menu_alarm).setVisible(true);	
				mode.getMenu().findItem(R.id.menu_delete_alarm).setVisible(false);
			}
		}

		if (itemCount > 1) {
			mode.setSubtitle(itemCount + " sélections");

			// Masque le bouton d'édition menu_popup_edit du menu contextuel
			mode.getMenu().findItem(R.id.menu_edit).setVisible(false);
			mode.getMenu().findItem(R.id.menu_alarm).setVisible(false);
			mode.getMenu().findItem(R.id.menu_delete_alarm).setVisible(true);	
		}
	}
}
