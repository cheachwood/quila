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
