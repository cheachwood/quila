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
package fr.jcf.pret.database.helpers;

import fr.jcf.pret.contrats.PretEmpruntContrat;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Cette classe permet d'initialiser la base de données de l'application
 * @author jfélicité
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String NOM_BDD = "pret.db";
	private static final int VERSION_BDD = 24; 
	public static DatabaseHelper instance;
	
	/**
	 * Une seule instance de DatabaseHelper est nécessaire pour l'ensemble de l'application
	 * @param context
	 * @param version
	 * @return
	 */
	public static DatabaseHelper getInstance(Context context){
		if(null == instance){
			instance= new DatabaseHelper(context.getApplicationContext(), null, VERSION_BDD);
		}
		return instance;
	}
	
	private DatabaseHelper(Context context, CursorFactory factory, int version) {
		super(context, NOM_BDD, factory, version);
	}

	/**
	 * Méthode qui est lancée à l'initisalitation de la base pour créer les tables
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(PretEmpruntContrat.TABLE_CREATE);
	}

	/**
	 * Méthode qui est lancée lorsqu'une mise à jour de la base de données est nécessaire
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(PretEmpruntContrat.TABLE_DROP);
		onCreate(db);
//		if (newVersion > oldVersion) {
//			db.execSQL(PretEmpruntContrat.TABLE_ALTER);
//		}
			//		} else {
//			//db.execSQL(PretEmpruntContracts.TABLE_DROP);
//		}
	}

}
