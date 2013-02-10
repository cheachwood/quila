package fr.jcf.pret.database.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.widget.EditText;
import fr.jcf.pret.modeles.PretEmprunt;
import fr.jcf.pret.utils.UtilPret;

public class ContactsDBAdapter {

	public static PretEmprunt fetchContact(Context context, int requestCode, int resultCode, Intent data, EditText editText, PretEmprunt pretEmprunt) {
		// Vérification de la requpete appelant
		if (requestCode == UtilPret.PICK_CONTACT_REQUEST) {
			// On vérifie si la requête a aboutie
			if (resultCode == Activity.RESULT_OK) {
				// Récupération de l'adresse URI du contact sélectionné
				Uri contactUri = data.getData();

				// On a besoin de colonne Nom et ID des contacts
				String[] projection = { Contacts.DISPLAY_NAME, Contacts._ID };

				// Exécution de la requête sur l'ID du contact
				Cursor cursor = context.getContentResolver().query(contactUri, projection, null, null, null);
				cursor.moveToFirst();

				// On récupère certaines info du Contact
				int columnContactName = cursor.getColumnIndex(Contacts.DISPLAY_NAME);
				int columnContactId = cursor.getColumnIndex(Contacts._ID);
				String nomContact = cursor.getString(columnContactName);
				String idContact = cursor.getString(columnContactId);

				// On affiche le nom du Contact
				CharSequence nomContactCharSequence = nomContact;
				editText.setText(nomContactCharSequence);

				// On affecte l'ID du Contact
				pretEmprunt.contactId = Integer.parseInt(idContact);
			}
		}
		return pretEmprunt;
	}

	public static String getTelephoneContact(Context context, int contactId) {
		List<String> telephones = new ArrayList<String>();
		String[] arguments = { String.valueOf(contactId) };
		Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, Contacts._ID + " = ?", arguments, null);
		
		while (cursor.moveToNext()) {
			String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

			if (Integer.parseInt(hasPhone) == 1) {
				// On sait qu'il existe un numéro de téléphone, on peut donc y accéder
				// en requêtant sur la table des Téléphones en passe l'ID du contact en arguments
				Cursor cursorPhones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arguments, null);

				// On navigue dans tous les numéros de téléphone et on récupère tous les téléphones mobile
				while (cursorPhones.moveToNext()) {
					int typePhone = cursorPhones.getInt(cursorPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
					if (typePhone == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
						telephones.add(cursorPhones.getString(cursorPhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					}
				}
				cursorPhones.close();
			}
		}

		if (telephones.size() > 0) {
			// On ne retourne que le premier téléphone mobile s'il y en a un
			return telephones.get(0);
		}
		return null;
	}

	/**
	 * Permet de récupérer la liste des emails de la personne
	 * @param context
	 * @param contactId
	 * @return
	 */
	public static List<String> getEmailContact(Context context, int contactId) {
		List<String> emails = new ArrayList<String>();
		String[] arguments = { String.valueOf(contactId) };
		Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, Contacts._ID + " = ?", arguments, null);
		
		while (cursor.moveToNext()) {

			Cursor cursorEmail = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", arguments, null);

			while (cursorEmail.moveToNext()) {
				emails.add(cursorEmail.getString(cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
			}
			cursorEmail.close();
		}

		if (emails.size() > 0) {
			// On ne retourne que le premier téléphone mobile s'il y en a un
			return emails;
		}
		return null;
	}
}
