package fr.jcf.pret.modeles;

import java.io.Serializable;


public class PretEmprunt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2200686779753104755L;
	public int id;
	public int contactId;
	public String contactNom;
	public String date;
	public String objet;
	public String image;
	public int pretOuEmprunt;
	public int alarm;
	
	@Override
	public String toString() {
		return "PretEmprunt [id=" + id + ", contactId=" + contactId + ", contactNom=" + contactNom + ", date=" + date + ", objet=" + objet
				+ ", image=" + image + ", pretOuEmprunt=" + pretOuEmprunt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contactId;
		result = prime * result + ((contactNom == null) ? 0 : contactNom.hashCode());
		result = prime * result + id;
		result = prime * result + pretOuEmprunt;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PretEmprunt other = (PretEmprunt) obj;
		if (contactId != other.contactId)
			return false;
		if (contactNom == null) {
			if (other.contactNom != null)
				return false;
		} else if (!contactNom.equals(other.contactNom))
			return false;
		if (id != other.id)
			return false;
		if (pretOuEmprunt != other.pretOuEmprunt)
			return false;
		return true;
	}
	
	
	
}
