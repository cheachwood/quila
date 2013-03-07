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
package fr.jcf.pret.cameravideo.helpers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.jcf.pret.utils.UtilPret;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

/**
 * Classe qui déclare les opérations possibles sur une image ou une vidéo
 * 
 * @author jfélicité
 * 
 */
public class CameraVideoHelper {
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	/**
	 * Permet de créer une URI pour un fichier image ou vidéo
	 * 
	 * @param type
	 * @return
	 */
	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * Permet de sauvegarde une image ou une vidéo dans le système de fichiers
	 * 
	 * @param type
	 * @return
	 */
	private static File getOutputMediaFile(int type) {
		// TODO vérifier que la SDCard est montée
		// en utilisant Environment.getExternalStorageState() avant de faire ce qui suit.

		// On récupère le répertoire dans lequel on souhaite enregistrer les images
		// On utilise Environment.getExternalStoragePublicDirectory lorsque l'on souhaite
		// partager les images avec d'autres applications et donc les faire persister
		// lorsque l'application est désinstallée
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PreterQuoiAQui");

		// On crée un répertoire de stockage s'il n'existe pas
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(UtilPret.LOG_SOURCE + ".cameravideo.helpers.CameraVideoHelper", "Erreur de création d'un répertoire");
				return null;
			}
		}

		// On crée un fichier image
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
			try {
				Log.d(UtilPret.LOG_SOURCE, mediaFile.getCanonicalPath().toString());
			} catch (IOException e) {
				Log.d(UtilPret.LOG_SOURCE + ".cameravideo.helpers.CameraVideoHelper", "Erreur de création d'un répertoire");
				Log.d(UtilPret.LOG_SOURCE + ".cameravideo.helpers.CameraVideoHelper", e.getMessage());
			}
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}
}
