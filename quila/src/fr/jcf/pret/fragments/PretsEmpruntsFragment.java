package fr.jcf.pret.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import fr.jcf.pret.R;
import fr.jcf.pret.activities.PretEmpruntActivity;
import fr.jcf.pret.adapters.PretEmpruntInfoAdapter;
import fr.jcf.pret.contrats.PretEmpruntContrat;
import fr.jcf.pret.database.adapters.PretEmpruntDBAdapter;
import fr.jcf.pret.listeners.PretEmpruntMultiChoiceModeListener;
import fr.jcf.pret.modeles.PretEmprunt;
import fr.jcf.pret.utils.UtilPret;

public class PretsEmpruntsFragment extends DialogFragment {
	private View view;
	public ListView listView;
	private int typePretEmprunt;

	public PretsEmpruntsFragment() {
		super();
	}

	public PretsEmpruntsFragment(int typePretEmprunt) {
		super();
		this.typePretEmprunt = typePretEmprunt;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// On indique au fragment que les options s'affichent dans le menu
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View localView = UtilPret.chargerLayout(inflater, container, R.layout.prets_emprunts_fragment);
		this.view = localView;
		alimenteVue(this.view);
		return localView;
	}

	private void alimenteVue(View view) {
		PretEmpruntDBAdapter empruntDBAdapter = new PretEmpruntDBAdapter(getActivity().getApplicationContext());
		PretEmpruntDBAdapter.open();

		empruntDBAdapter.setPretOuEmprunt(this.typePretEmprunt);
		List<PretEmprunt> emprunts = empruntDBAdapter.fetchAll();
		PretEmpruntDBAdapter.close();

		// On charge la liste des données dans la liste courante du layout
		// "emprunt_fragment"
		listView = (ListView) view.findViewById(R.id.listPrets);

		// On crée un Adapter qui affichera les données dans le layout"list_black_theme"
		// Sous la forme d'un TextView "list_content" qui est un composant qui affiche du texte (possibilité d'édition)
		final PretEmpruntInfoAdapter adapter = new PretEmpruntInfoAdapter(getActivity().getApplicationContext(), emprunts);

		// Assignation d'un adapter à la ListView
		if (null != adapter) {
			listView.setAdapter((ListAdapter) adapter);
			// A chaque modification de l'adapter le listview doit en être informée pour rafraichissement
			adapter.notifyDataSetChanged();
		}

		// Création et association d'un menu contextuel à la liste
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new PretEmpruntMultiChoiceModeListener(getActivity().getApplicationContext(), listView, emprunts));
		
	}

	/**
	 * Permet d'associer un menu au fragement
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_prets_emprunts, menu);
	}

	/**
	 * Permet d'alimenter la vue lorsqu'on lui redonne la main
	 */
	@Override
	public void onResume() {
		super.onResume();
		alimenteVue(view);
	}

	/**
	 * Permet de gérer les options à afficher en fonction du contexte
	 */
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		if (typePretEmprunt == PretEmpruntContrat.PRET) {
			menu.findItem(R.id.creer_nouveau_pret).setVisible(true);
			menu.findItem(R.id.creer_nouvel_emprunt).setVisible(false);
		} else {
			menu.findItem(R.id.creer_nouveau_pret).setVisible(false);
			menu.findItem(R.id.creer_nouvel_emprunt).setVisible(true);
		}
		super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent pretEmpruntActivityIntent = new Intent(this.getActivity(), PretEmpruntActivity.class);
		// Récupération de l'option sélectionnée
		switch (item.getItemId()) {
		case R.id.creer_nouveau_pret:
			// On affiche l'activity qui permet de créer un prêt
			pretEmpruntActivityIntent.putExtra("pretEmprunt", PretEmpruntContrat.PRET);
			startActivity(pretEmpruntActivityIntent);
			return true;
		case R.id.creer_nouvel_emprunt:
			// On affiche l'activity qui permet de créer un emprunt
			pretEmpruntActivityIntent.putExtra("pretEmprunt", PretEmpruntContrat.EMPRUNT);
			startActivity(pretEmpruntActivityIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
