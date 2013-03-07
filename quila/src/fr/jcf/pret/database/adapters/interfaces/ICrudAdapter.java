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

import java.util.List;

public interface ICrudAdapter<T> {
	public abstract long insert(T domaineObject);

	public abstract long update(T domaineObject);

	public abstract long delete(int identifiant);

	public abstract List<T> fetchAll();

	public abstract T fetchById(String identifiant);

}
