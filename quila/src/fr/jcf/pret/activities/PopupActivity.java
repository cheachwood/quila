package fr.jcf.pret.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import fr.jcf.pret.R;
import fr.jcf.pret.database.adapters.ContactsDBAdapter;
import fr.jcf.pret.modeles.PretEmprunt;
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
 
/**
 * Fenêtre qui s'affiche lorsqu'une notification est sélectionnée par l'utilisateur On donne la possibilité d'envoyer un SMS ou un Email
 * 
 * @author jfélicité
 * 
 */
public class PopupActivity extends Activity {
	private TextView nomContactTextView;
	private TextView objetContactTextView;
	private TextView dateContactTextView;
	private PretEmprunt pretEmprunt;

	public PopupActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PopupActivity(PretEmprunt pretEmprunt) {
		super();
		this.pretEmprunt = pretEmprunt;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Récupération de la donnée portée par la notification
		// C'est un objet du type PretEmprunt
		Bundle bundle = getIntent().getExtras();
		this.pretEmprunt = (PretEmprunt) bundle.get("data");
		setContentView(R.layout.popup);

		chargerFenetre();
	}

	/**
	 * Permet de charger le contenu dans la fenêtre
	 */
	protected void chargerFenetre() {

		String messageDate = null;
		ImageButton imageButtonSMS = (ImageButton) findViewById(R.id.popup_envoi_sms);
		ImageButton imageButtonEmail = (ImageButton) findViewById(R.id.popup_envoi_email);

		if (pretEmprunt.pretOuEmprunt == 0) {
			this.setTitle("Prets");
			messageDate = getString(R.string.popup_label_date_pret);
			imageButtonSMS.setVisibility(ImageView.VISIBLE);
			imageButtonEmail.setVisibility(ImageView.VISIBLE);
		} else {
			this.setTitle("Emprunts");
			messageDate = getString(R.string.popup_label_date_emprunt);
			imageButtonSMS.setVisibility(ImageView.INVISIBLE);
			imageButtonEmail.setVisibility(ImageView.INVISIBLE);
		}

		nomContactTextView = (TextView) findViewById(R.id.nom);
		nomContactTextView.setText(getString(R.string.popup_label_contact) + pretEmprunt.contactNom);
		objetContactTextView = (TextView) findViewById(R.id.objet);
		objetContactTextView.setText(getString(R.string.popup_label_objet) + pretEmprunt.objet);

		dateContactTextView = (TextView) findViewById(R.id.date);
		dateContactTextView.setText(messageDate + pretEmprunt.date);

		ImageView image = (ImageView) findViewById(R.id.image);
		if (null != pretEmprunt.image && !pretEmprunt.image.equals("") && !pretEmprunt.image.equals("null")) {
			image.setImageURI(Uri.parse(pretEmprunt.image));
		} else {
			image.setImageResource(R.drawable.ic_launcher);
		}
	}

	/**
	 * Méthode évènementielle d'envoi de SMS
	 * 
	 * @param v
	 */
	public void envoyerSMS(View v) {
		SmsManager smsManager = SmsManager.getDefault();
		String telephone = ContactsDBAdapter.getTelephoneContact(getApplicationContext(), pretEmprunt.contactId);

		// S'il existe un téléphone mobile alors on envoi un SMS
		if (null != telephone) {
			List<String> messages = creationMessage();

			smsManager.sendMultipartTextMessage(telephone, null, (ArrayList<String>) messages, null, null);
			Toast.makeText(getApplicationContext(), "Envoie d'un SMS", Toast.LENGTH_LONG).show();
		} else {
			// Sinon on indique à l'utilisateur que le SMS n'a pas été envoyé faute de téléphone non renseigné
			Toast.makeText(getApplicationContext(), R.string.popup_telephone_absent, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * @return
	 */
	protected List<String> creationMessage() {
		List<String> messages = new ArrayList<String>();
		messages.add("Bonjour ");
		messages.add(pretEmprunt.contactNom);
		messages.add("\n");
		messages.add("n'oublie pas de me rendre l'objet suivant que tu m'as emprunté : ");
		messages.add(pretEmprunt.objet);
		messages.add("\n");
		messages.add("Avant le ");
		messages.add(pretEmprunt.date);
		messages.add("\n");
		messages.add("Merci et à bientôt!");
		return messages;
	}

	/**
	 * Méthode évènementielle d'envoi d'Email
	 * 
	 * @param v
	 */
	public void envoyerEmail(View v) {
		String message = "";
		for (String mess : creationMessage()) {
			message += mess;
		}
		
		List<String> emails = ContactsDBAdapter.getEmailContact(getApplicationContext(), pretEmprunt.contactId);
		String recipients = "";
		for (String email : emails) {
			recipients += ";" + email;
		}
		
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + recipients + "?subject="
				+ Uri.encode("Emprunt") + "&body="
				+ Uri.encode(message)));
		startActivity(Intent.createChooser(intent, "Choisir un outil de Mail"));
	}
}
