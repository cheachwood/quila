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

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.jcf.pret.R;
import fr.jcf.pret.contrats.PretEmpruntContrat;
import fr.jcf.pret.modeles.PretEmprunt;

public class PretEmpruntInfoAdapter extends BaseAdapter {

	// private objects
	public List<PretEmprunt> listPretEmpruntInfo;
	private LayoutInflater mInflater;
	private Context context;

	/*
	 * constructor
	 */
	public PretEmpruntInfoAdapter(Context context, List<PretEmprunt> list) {
		this.listPretEmpruntInfo = list;
		this.context = context;
		// create layout inflater
		this.mInflater = LayoutInflater.from(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return listPretEmpruntInfo.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return listPretEmpruntInfo.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// get view reference
		View view = convertView;
		// if null
		if (view == null) {
			// inflate new layout
			view = mInflater.inflate(R.layout.list_prets_emprunts, null);
			// create a holder
			ViewHolder holder = new ViewHolder();
			// find controls
			holder.txtNomContact = (TextView) view.findViewById(R.id.txtNomContact);
			holder.txtDate = (TextView) view.findViewById(R.id.txtDate);
			holder.txtObjet = (TextView) view.findViewById(R.id.txtObjet);
			holder.imageView = (ImageView) view.findViewById(R.id.image_list);
			holder.alarm = (ImageView) view.findViewById(R.id.image_alarm);
			// set data structure to view
			view.setTag(holder);
		}

		// get selected user info
		PretEmprunt pretEmprunt = listPretEmpruntInfo.get(position);
		// if not null
		if (pretEmprunt != null) {
			// query data structure
			ViewHolder holder = (ViewHolder) view.getTag();
			// set data to display
			holder.txtNomContact.setText(pretEmprunt.contactNom);
			holder.txtDate.setText(pretEmprunt.date);
			holder.txtObjet.setText(pretEmprunt.objet);
			
			if(pretEmprunt.alarm == PretEmpruntContrat.ALARM_ON){
				holder.alarm.setImageResource(R.drawable.ic_alarms);
			}else{
				holder.alarm = new ImageView(context);
			}
			
			Bitmap bm = null;
			if (null != pretEmprunt.image) {
				if (pretEmprunt.image.lastIndexOf("/") >= 1) {
					// On récupère l'identifiant de l'image
					int originalImageId = Integer.parseInt(pretEmprunt.image.substring(pretEmprunt.image.lastIndexOf("/") + 1,
							pretEmprunt.image.length()));
					try {
						// On récupère le thumbnail de l'image à partir de l'identifiant
						bm = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), originalImageId,
								MediaStore.Images.Thumbnails.MICRO_KIND, null);

						holder.imageView.setImageBitmap(bm);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else {
					holder.imageView.setImageResource(R.raw.web_hi_res_512);
				}
			}
		}
		return view;
	}

	/*
	 * @class ViewHolder to hold data structure on view with user info
	 */
	static class ViewHolder {
		private TextView txtNomContact;
		private TextView txtDate;
		private TextView txtObjet;
		private ImageView imageView;
		private ImageView alarm;
	}

}
