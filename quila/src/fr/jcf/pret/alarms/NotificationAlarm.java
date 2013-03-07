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
package fr.jcf.pret.alarms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import fr.jcf.pret.R;
import fr.jcf.pret.activities.PopupActivity;
import fr.jcf.pret.modeles.PretEmprunt;

/**
 * Permet de créer une notification à l'heure voulu
 * @author jfélicité
 *
 */
public class NotificationAlarm extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String ns = Context.NOTIFICATION_SERVICE;
		String titre = "";
		CharSequence message = "";
		long when = System.currentTimeMillis(); //Permet de voir quand la notificaiton a été levée
		
		PretEmprunt pretEmpruntSelectAlarm = (PretEmprunt) intent.getExtras().get("pretEmprunt");
		
		// Création du message
		if (pretEmpruntSelectAlarm.pretOuEmprunt == 0) {
			titre = context.getString(R.string.notification_titre_pret);
			message =context.getString(R.string.notification_message_pret, pretEmpruntSelectAlarm.objet, pretEmpruntSelectAlarm.contactNom, pretEmpruntSelectAlarm.date );
		} else {
			titre = context.getString(R.string.notification_titre_emprunt);
			message = context.getString(R.string.notification_message_emprunt, pretEmpruntSelectAlarm.objet, pretEmpruntSelectAlarm.objet, pretEmpruntSelectAlarm.date);
		}
		
		// Définition d'un ID de notification
		int NOTIFICATION_ID = pretEmpruntSelectAlarm.id;
		
		// Gestion d'une notification
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
		
		
		// On passe les informations qui doivent être envoyées à la notification
		// Cela permet également de lancer une Activity lorsqu'un utilisateur clique sur la notification
		Intent notificationIntent = new Intent(Intent.ACTION_MAIN);
		notificationIntent.setClass(context, PopupActivity.class);
		notificationIntent.putExtra("pretEmprunt", pretEmpruntSelectAlarm.pretOuEmprunt);
		notificationIntent.putExtra("data", pretEmpruntSelectAlarm);

		// Intent qui peut être lancé par une autre application que celle en cours
		// Cela permet de bénéficier des droits de l'application alors le context n'est pas le même
		PendingIntent contentIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Création de la notification
		Notification notification = new Notification.Builder(context)
				.setContentTitle(titre)
				.setContentText(message)
				.setSmallIcon(R.drawable.ic_launcher)
				.setWhen(when)
				.setContentIntent(contentIntent)
				.getNotification();

		mNotificationManager.notify(NOTIFICATION_ID, notification);
	}
	
	

}
