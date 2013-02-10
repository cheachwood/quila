package fr.jcf.pret.listeners;

import fr.jcf.pret.utils.UtilPret;
import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.View.OnClickListener;

public class ContactButtonOnClickListener implements OnClickListener {
	@Override
	public void onClick(View v) {
		// On affiche l'activity qui permet de créer un contact
		// lorsque l'option "créer un contact" est sélectionnée
		Intent intent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
		Activity activity =  (Activity) v.getContext();
		activity.startActivityForResult(intent, UtilPret.PICK_CONTACT_REQUEST);
	}

}
