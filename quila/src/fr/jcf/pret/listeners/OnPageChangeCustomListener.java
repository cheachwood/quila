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

import android.app.ActionBar;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * Permet de gérer les évènements lorsque le ViewPager change de position
 * @author jfélicité
 *
 */
public class OnPageChangeCustomListener implements OnPageChangeListener {
	private ActionBar actionBar;	
	
	public OnPageChangeCustomListener() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnPageChangeCustomListener(ActionBar actionBar) {
		super();
		this.actionBar = actionBar;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// On ne fait rien pour l'instant
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// On ne fait rien pour l'instant
	}

	/**
	 * Lorsque la position du pager change, un nouvel onglet est affiché
	 */
	@Override
	public void onPageSelected(int position) {
		this.actionBar.getTabAt(position).select();
	}

}
