package fr.jcf.pret.database.adapters.interfaces;

import java.util.List;

public interface ICrudAdapter<T> {
	public abstract long insert(T domaineObject);

	public abstract long update(T domaineObject);

	public abstract long delete(int identifiant);

	public abstract List<T> fetchAll();

	public abstract T fetchById(String identifiant);

}
