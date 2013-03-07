package fr.jcf.pret.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import fr.jcf.pret.R;
import fr.jcf.pret.adapters.ViewPagerAdapter;
import fr.jcf.pret.contrats.PretEmpruntContrat;
import fr.jcf.pret.fragments.PretsEmpruntsFragment;
import fr.jcf.pret.listeners.ActionBarCustomListener;
import fr.jcf.pret.listeners.OnPageChangeCustomListener;
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
 * Classe permettant de mettre en place un Pager Swipe Fragments Inspiré des tutoriels : - http://www.tutos-android.com/fragment-slider-page-lautre -
 * http://android-developers.blogspot.fr/2011/08/horizontal-view-swiping-with- viewpager.html
 * 
 * @author jfélicité
 * 
 */
public class ViewPagerSwipingActivity extends FragmentActivity {
	private ViewPagerAdapter mAdapter;
	private ViewPager mPager;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		construireBarAction();
	}

	/**
	 * Cette méthode construit la bar d'action pour l'activity principale
	 */
	private void construireBarAction() {
		// Création de la listes des fragments que l'on souhaite afficher dans les onglets
		List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(new PretsEmpruntsFragment(PretEmpruntContrat.PRET));
		fragments.add(new PretsEmpruntsFragment(PretEmpruntContrat.EMPRUNT));

		// Récupération de la barre d'action
		actionBar = getActionBar();

		// Instanciation de l'adapter du ViewPager
		mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);

		// On récupère le ViewPager
		mPager = (ViewPager) findViewById(R.id.pager);

		// On associe l'adapter au pager
		mPager.setAdapter(mAdapter);

		// Lorsqu'un élément du pager change des évènements sont levés
		mPager.setOnPageChangeListener(new OnPageChangeCustomListener(actionBar));

		// La bar de navigation est en mode Onglet ou Tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tabPrets = actionBar.newTab();
		ActionBarCustomListener tabListenerAlbum = new ActionBarCustomListener(this, 0, mPager);
		tabPrets.setText(R.string.prets_activity);
		tabPrets.setTabListener(tabListenerAlbum);
		actionBar.addTab(tabPrets);

		// On crée chaque Onglet du menu et on l'ajoute à la barre d'action
		Tab tabEmprunts = actionBar.newTab();
		tabEmprunts.setText(R.string.emprunts_activity);
		ActionBarCustomListener tabListenerArtists = new ActionBarCustomListener(this, 1, mPager);
		tabEmprunts.setTabListener(tabListenerArtists);
		actionBar.addTab(tabEmprunts);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Récupération de l'option sélectionnée
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent settingsEmpruntActivityIntent = new Intent(this, UserPreferencesActivity.class);
			startActivity(settingsEmpruntActivityIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
