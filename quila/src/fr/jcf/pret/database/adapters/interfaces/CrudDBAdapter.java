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
package fr.jcf.pret.database.adapters.interfaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import fr.jcf.pret.database.adapters.PretEmpruntDBAdapter;
import fr.jcf.pret.database.helpers.DatabaseHelper;

public abstract class CrudDBAdapter<T> implements ICrudAdapter<T>{
	private static Context context;
	public static SQLiteDatabase bdd;
	public static DatabaseHelper databaseHelper;
	
	public CrudDBAdapter(Context context) {
		super();
		CrudDBAdapter.context = context;
	}
	
	/**
	 * Permet d'ouvrir une connexion à la base de données
	 * @return
	 */
	public static SQLiteDatabase open() {
		databaseHelper = DatabaseHelper.getInstance(context);
		bdd = databaseHelper.getWritableDatabase();
		return bdd;
	}

	/**
	 * Permet de fermer une connexion à la base de données
	 */
	public static void close() {
		if(bdd.isOpen()){
			bdd.close();
		}else{
			Log.d(PretEmpruntDBAdapter.class.getCanonicalName(),"La base de données est déjà fermée!");
		}
	}
}
