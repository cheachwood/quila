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
package fr.jcf.pret.utils;

import java.io.FileNotFoundException;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Classe utilitaires pour le projet TabProject
 * 
 * @author jfélicité
 * 
 */
public class UtilPret {
	public final static String LOG_SOURCE = "fr.jcf.pret";
	public static final int PICK_CONTACT_REQUEST = 1;
//	public static final int PRET = 0;
//	public static final int EMPRUNT = 1;
	public static final int PICK_DATE_REQUEST = 2;
	public static final int PICK_IMAGE_REQUEST = 3;
	public static final int PICK_CAMERA_REQUEST = 4;

	
	/**
	 * Permet de charge un frament à partir d'un layout passé en paramètre
	 * 
	 * @param inflater
	 * @param container
	 * @param layout
	 *            Le layout correspondant au fragment à charger
	 * @return Une vue
	 */
	public static View chargerLayout(LayoutInflater inflater,
			ViewGroup container, int layout) {
		if (container == null) {
			return null;
		}

		LinearLayout linearLayout = (LinearLayout) inflater.inflate(layout,
				container, false);

		return linearLayout;
	}

	/**
	 * Renvoie une image taillée à la bonne dimension
	 * @param selectedImage
	 * @param contentResolver
	 * @param imageView
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap decodeUri(Uri selectedImage, 
			ContentResolver contentResolver, ImageView imageView)
			throws FileNotFoundException {
		
		int originalImageId = Integer.parseInt(selectedImage.toString()
				.substring(selectedImage.toString().lastIndexOf("/") + 1,
						selectedImage.toString().length()));
		
		return MediaStore.Images.Thumbnails.getThumbnail(
				contentResolver, originalImageId,
				MediaStore.Images.Thumbnails.MINI_KIND, null);
	}
	
	
}
