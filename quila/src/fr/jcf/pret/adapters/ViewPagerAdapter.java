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
 package fr.jcf.pret.adapters;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Permet l'affichage du fragments sélectionné par un ViewPager
 * @author jfélicité
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
	// Liste des fragments à afficher
	private List<Fragment> fragments;
	
	/**
	 * On récupére la liste des fragments via le constructeur
	 * @param fm
	 * @param fragments : la liste des fragments
	 */
	public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}

	/**
	 * Permet de récupérer le fragments à afficher en fonction de la position
	 * du ViewPager
	 */
	@Override
	public Fragment getItem(int position) {
		return  fragments.get(position);
	}

}
