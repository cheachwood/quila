package fr.jcf.pret.contrats;

public class PretEmpruntContrat{
	public static final String TABLE_NOM="pret_emprunt";
	public static final String _ID="_id";
	public static final String CONTACT_ID="id_contact";
	public static final String CONTACT_NOM = "nom_contact";
	public static final String DATE="date";
	public static final String OBJET="objet";
	public static final String IMAGE_OBJET="image";
	public static final String ALARM="alarm"; // Le champ prend la valeur 0 = non  ou 1 = oui
	public static final String PRET_EMPRUNT="pret_ou_emprunt";	//Le champ aura comme valeur 0 = pret, 1 = emprunt

	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NOM + " ("
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ CONTACT_ID + " INTEGER, "
			+ CONTACT_NOM + " TEXT NOT NULL, "
			+ OBJET + " TEXT NOT NULL, "
			+ DATE + " DATE, "
			+ PRET_EMPRUNT + " TEXT NOT NULL, "
			+ IMAGE_OBJET + " TEXT, "
			+ ALARM + " INTEGER);";
	
	// A Compléter lors de changement de version 23
	public static final String TABLE_ALTER = "ALTER TABLE " + TABLE_NOM  + ";"; 
	
	// Suppression de la table
	public static final String TABLE_DROP = "DROP TABLE " + TABLE_NOM;
	
	 // Projection sur toutes les colonnes
	public static final String[] PROJECTION_ALL = new String[] { _ID, CONTACT_ID, CONTACT_NOM,
			DATE, OBJET, IMAGE_OBJET, PRET_EMPRUNT, ALARM };
	
	// Paramètre de tri
	public static final String ORDER_BY_ID = "ASC _id";
	public static final String ORDER_BY_CONTACT = "ASC id_contact";
	public static final String ORDER_BY_DATE = "ASC DATE";
	public static final String ORDER_BY_OBJET = "ASC objet";
	
	// Arguments pour définir si c'est un pret ou un emprunt 
	public static final int PRET = 0;
	public static final int EMPRUNT = 1;

	// Arguments pour définir si c'est une alarme est active ou non
	public static final int ALARM_ON = 1;
	public static final int ALARM_OFF = 0;

	
}
