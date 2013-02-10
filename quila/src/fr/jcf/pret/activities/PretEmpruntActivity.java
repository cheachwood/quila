package fr.jcf.pret.activities;

import java.io.FileNotFoundException;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import fr.jcf.pret.R;
import fr.jcf.pret.cameravideo.helpers.CameraVideoHelper;
import fr.jcf.pret.contrats.PretEmpruntContrat;
import fr.jcf.pret.database.adapters.ContactsDBAdapter;
import fr.jcf.pret.fragments.DatePickerFragment;
import fr.jcf.pret.listeners.AnnulerOnClickListener;
import fr.jcf.pret.listeners.ContactButtonOnClickListener;
import fr.jcf.pret.listeners.PretEmpruntEnregOnClickListener;
import fr.jcf.pret.modeles.PretEmprunt;
import fr.jcf.pret.utils.UtilPret;

/**
 * Activity principale
 * 
 * @author jfélicité
 * 
 */
public class PretEmpruntActivity extends FragmentActivity {

	private EditText contactNameEditText;
	private PretEmprunt pretEmprunt;
	private MenuItem enregButton;
	private ImageButton contactButton;
	private MenuItem annulerButton;
	private EditText dateEditText;
	private EditText objetEditText;
	private Bitmap bitmap;
	private ImageView imageView;
	private Uri fileUri;

	public PretEmpruntActivity() {
		super();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pret_emprunt_form);

		// Récupération des composants du layout
		contactButton = (ImageButton) findViewById(R.id.choix_contact_Button);
		dateEditText = (EditText) findViewById(R.id.pret_date_EditText);
		contactNameEditText = (EditText) findViewById(R.id.pret_contact_EditText);
		imageView = (ImageView) findViewById(R.id.pret_image);
		objetEditText = (EditText) findViewById(R.id.pret_objet_EditText);

		// Gestion des listeners sur les différents boutons
		contactButton.setOnClickListener(new ContactButtonOnClickListener());
		extractionData();
	}

	/**
	 * Lorsque l'Activity récupère le focus, elle récupère la donnée renvoyé par l'Intent créer dans l'Activity qui renvoie le résultat. Dans ce cas
	 * on interroge la table contact et on aliment le champ Nom du contact
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case UtilPret.PICK_CONTACT_REQUEST:
			pretEmprunt = ContactsDBAdapter.fetchContact(getApplicationContext(), requestCode, resultCode, data, contactNameEditText, pretEmprunt);
			data.putExtra("data", pretEmprunt);
			enregButton.setEnabled(true);
			break;
		case UtilPret.PICK_IMAGE_REQUEST:
			if (resultCode == RESULT_OK) {
				recuperationImage(data);
			}
			break;
		case UtilPret.PICK_CAMERA_REQUEST:
			if (resultCode == RESULT_OK) {
				sauvegardeImage(data, getApplicationContext(), fileUri, getContentResolver(), imageView);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Permet de récupérer l'image sélectionnée par l'utilisateur et de l'afficher à l'écran
	 * 
	 * @param data
	 */
	protected void recuperationImage(Intent data) {
		Uri selectedImage = null;
		// On récupère l'image sélectionnée pour l'associer au composant
		// ImageView
		if (null != data && null != data.getData()) {
			selectedImage = data.getData();
			try {
				bitmap = UtilPret.decodeUri(selectedImage, getContentResolver(), imageView);
				imageView.setTag(selectedImage.toString());
			} catch (FileNotFoundException e) {
				Log.d(UtilPret.LOG_SOURCE, "Le fichier sélectionné n'a pas été trouvé.");
				Log.d(UtilPret.LOG_SOURCE, e.getMessage());
			}
			imageView.setImageBitmap(bitmap);
		} else {
			// Si pas d'image sélectionnée on affiche l'image par défaut
			imageView.setImageResource(R.raw.web_hi_res_512);
		}
	}

	/**
	 * Méthode qui permet d'afficher la boite dialogue de date
	 * 
	 * @param v
	 */
	public void showDatePickerDialog(View v) {
		// Instanciation de la boite de dialogue à laquelle on passe le champ à alimenter
		DialogFragment newFragment = new DatePickerFragment(dateEditText);

		// Affichage de la boite de dialogue
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	/**
	 * Permet d'afficher la galerie pour choisir une image
	 * 
	 * @param view
	 */
	public void showImageDialog(View view) {
		// Création d'un intent d'affichage de la galerie
		Intent intentImage = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intentImage, UtilPret.PICK_IMAGE_REQUEST);
	}

	/**
	 * Permet de lancer l'appareil photo
	 * 
	 * @param view
	 */
	public void showCamera(View view) {
		Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// On crée un objet File et on l'enregistre dans le système de fichiers
		fileUri = CameraVideoHelper.getOutputMediaFileUri(CameraVideoHelper.MEDIA_TYPE_IMAGE);

		// On ajoute le fichier dans les données à transmettre dans l'Intent
		intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intentCamera, UtilPret.PICK_CAMERA_REQUEST);
	}

	/**
	 * Permet de sauvegarder des photos prises avec l'appareil photo
	 * 
	 * @param data
	 * @param context
	 * @param fileUri
	 * @param contentResolver
	 * @param imageView
	 */
	private void sauvegardeImage(Intent data, Context context, Uri fileUri, ContentResolver contentResolver, ImageView imageView) {
		Bitmap bitmap = null;
		if (data != null) {
			// L'image a été retournée (ne fonctionne pas sur certains périphériques!!!)
			Bundle extras = data.getExtras();
			bitmap = (Bitmap) extras.get("data");
			imageView.setImageBitmap(bitmap);
		} else {
			// On prépare l'image pour enregistrer son nom et son chemin en base
			Uri imageSelection = null;

			// On récupère le nom du fichier
			String nomImage = fileUri.toString().substring(fileUri.toString().lastIndexOf("/") + 1, fileUri.toString().length());

			// Ici on enlève la partie "file://" afin de ne récupérer que le chemin du répertoire
			String chemin = fileUri.toString().substring(7, fileUri.toString().length());
			try {
				// On enregistre l'image en base pour la récupérer plus tard
				imageSelection = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, chemin, nomImage, ""));
				bitmap = UtilPret.decodeUri(imageSelection, contentResolver, imageView);

				// On associe l'image pour affichage
				imageView.setTag(imageSelection);
				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.d(UtilPret.LOG_SOURCE, "Le fichier sélectionné n'a pas été trouvé.");
				Log.d(UtilPret.LOG_SOURCE, e.getMessage());
			}

		}
	}

	/**
	 * Permet de gérer les options (affichage, association d'évènements, ...)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_principal, menu);

		// Par défaut le bouton Enregistrer est désactivé, et on l'associe à un évènement d'enregistrement
		enregButton = menu.findItem(R.id.menu_enregistrer);
		if (this.pretEmprunt.id <= 0) {
			if (this.pretEmprunt.contactNom == null) {
				enregButton.setEnabled(false);
			}
			enregButton.setOnMenuItemClickListener(new PretEmpruntEnregOnClickListener(this.pretEmprunt.pretOuEmprunt, this.getCurrentFocus()));
		} else {
			enregButton.setOnMenuItemClickListener(new PretEmpruntEnregOnClickListener(this.pretEmprunt, this.getCurrentFocus()));
		}

		// On associe un évènement au bouton d'annulation
		annulerButton = menu.findItem(R.id.menu_annuler);
		annulerButton.setOnMenuItemClickListener(new AnnulerOnClickListener(this.getCurrentFocus()));
		return true;
	}

	/**
	 * Cette méthode permet de gérer les évènements lorsqu'un utilisateur sélectionne une option de menu
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_creer_contact:
			// On affiche l'activity qui permet de créer un contact
			Intent intent = new Intent(Intent.ACTION_INSERT, Contacts.CONTENT_URI);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		extractionData();
	}

	/**
	 * 
	 */
	protected void extractionData() {
		if (null != this.getIntent().getExtras()) {
			int pretOuEmprunt = (Integer) this.getIntent().getExtras().get("pretEmprunt");
			if (pretOuEmprunt == PretEmpruntContrat.PRET)
				this.setTitle(R.string.prets_activity);
			if (pretOuEmprunt == PretEmpruntContrat.EMPRUNT)
				this.setTitle(R.string.emprunts_activity);

			if (null != this.getIntent().getExtras().get("data")) {
				this.pretEmprunt = (PretEmprunt) this.getIntent().getExtras().get("data");
				Log.d("listener", String.valueOf(pretEmprunt.contactId));
				this.pretEmprunt.pretOuEmprunt = pretOuEmprunt;
				if (this.pretEmprunt.contactNom != null)
					contactNameEditText.setText(this.pretEmprunt.contactNom);
				if (this.pretEmprunt.date != null)
					dateEditText.setText(this.pretEmprunt.date);
				if (this.pretEmprunt.objet != null)
					objetEditText.setText(this.pretEmprunt.objet);

				Bitmap bm = null;
				if (null != this.pretEmprunt.image) {
					if (this.pretEmprunt.image.lastIndexOf("/") >= 1) {
						// On récupère l'identifiant de l'image
						int originalImageId = Integer.parseInt(this.pretEmprunt.image.substring(this.pretEmprunt.image.lastIndexOf("/") + 1,
								this.pretEmprunt.image.length()));
						try {
							// On récupère le thumbnail de l'image à partir de l'identifiant
							bm = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(), originalImageId,
									MediaStore.Images.Thumbnails.MICRO_KIND, null);

							imageView.setImageBitmap(bm);
							imageView.setTag(Uri.parse(this.pretEmprunt.image));
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						imageView.setImageResource(R.raw.web_hi_res_512);
					}
				}
			} else {
				this.pretEmprunt = new PretEmprunt();
				this.pretEmprunt.pretOuEmprunt = pretOuEmprunt;
			}
		} else {
			this.pretEmprunt = new PretEmprunt();
		}
	}

}