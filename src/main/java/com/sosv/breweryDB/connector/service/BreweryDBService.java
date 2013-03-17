/*
Copyright 2013 Sven Sommerfeld (svensommerfeld1982@gmail.com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.sosv.breweryDB.connector.service;

import java.util.Collection;

import com.google.inject.Inject;
import com.sosv.breweryDB.connector.entity.beer.Beer;
import com.sosv.breweryDB.connector.entity.beer.Page;
import com.sosv.breweryDB.connector.service.beer.IBeerService;

/**
 * The service to get the data of the breweryDB service
 * @author Sven
 *
 */
public class BreweryDBService implements IBreweryDBService{

	private IBeerService beerService;

	/**
	 * C'tor
	 * @param beerService The beer service which is used to retrieve the beer data
	 */
	@Inject
	public BreweryDBService(IBeerService beerService) {
		super();
		this.beerService = beerService;
	}

	/*
	 * (non-Javadoc)
	 * @see com.sosv.breweryDB.connector.client.interfaces.beer.IBeerService#getAll()
	 */
	@Override
	public Collection<Beer> getAll() {
		return beerService.getAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.sosv.breweryDB.connector.service.beer.IBeerService#getPagesBeers(java.lang.Number)
	 */
	@Override
	public Page getPagesBeers(Number pageNumber) {
		return beerService.getPagesBeers(pageNumber);
	}

}
