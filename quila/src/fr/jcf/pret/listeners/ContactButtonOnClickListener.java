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
