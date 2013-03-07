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
