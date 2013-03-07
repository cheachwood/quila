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

import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/**
 * Permet de définir les évènements lorsqu'un élément de la barre d'action change
 * @author jfélicité
 *
 */
public class ActionBarCustomListener implements android.app.ActionBar.TabListener {
	private final int mTag;
	private ViewPager mPager;

	/**
	 * Constructeur utilisé à chaque création de Tab
	 * 
	 * @param activity   L'Activity hote utilisée pour instancier un fragment
	 * @param tag        Identifiant tag du fragment
	 */
	public ActionBarCustomListener(Activity activity, int tag, ViewPager mPager) {
		this.mTag = tag;
		this.mPager = mPager;
	}

	/**
	 * Méthode utilisée lorsqu'un Tab est sélectionné un fragment est alors affiché
	 */
	public void onTabSelected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		mPager.setCurrentItem(mTag);
	}

	/**
	 * Méthode utilisée lorsqu'un Tab est déselectionné Le fragment n'est
	 * plus affiché
	 */
	public void onTabUnselected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		// On ne fait rien	
	}

	public void onTabReselected(Tab tab,
			FragmentTransaction fragmentTransaction) {
		// Un utilisateur a déjà sélectionné le tab en cours. Actuellement
		// il ne se passe rien.
	}

}
