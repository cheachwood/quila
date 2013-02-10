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
