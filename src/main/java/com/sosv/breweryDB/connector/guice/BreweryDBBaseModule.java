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
package com.sosv.breweryDB.connector.guice;

import com.google.inject.AbstractModule;
import com.sosv.breweryDB.connector.configuration.IBreweryDBConnectorConfiguration;
import com.sosv.breweryDB.connector.configuration.PropertyBreweryDBConnectorConfiguration;
import com.sosv.breweryDB.connector.service.provider.JerseyClientProvider;
import com.sosv.breweryDB.connector.service.resource.beer.BeerResource;
import com.sosv.breweryDB.connector.service.resource.beer.IBeerResource;
import com.sosv.breweryDB.connector.service.resource.brewery.BreweryResource;
import com.sosv.breweryDB.connector.service.resource.brewery.IBreweryResource;
import com.sosv.breweryDB.connector.service.resource.search.ISearchResource;
import com.sosv.breweryDB.connector.service.resource.search.SearchResource;
import com.sun.jersey.api.client.Client;

/**
 * Guice module to use in the application Binds all necessary services to use
 * them if you install this module.
 * 
 * @author Sven
 * 
 */
public class BreweryDBBaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bindPropertyConfiguration();
		
		// Bind the client provider
		bindClientProvider();

		// Bind resources
		bindResources();
	}


	private void bindPropertyConfiguration() {
		bind(IBreweryDBConnectorConfiguration.class).to(
				PropertyBreweryDBConnectorConfiguration.class);
	}


	/**
	 * Bind the resources
	 */
	protected void bindResources() {
		bind(IBeerResource.class).to(BeerResource.class);
		bind(IBreweryResource.class).to(BreweryResource.class);
		bind(ISearchResource.class).to(SearchResource.class);
	}

	/**
	 * Bind the Client provider. E.g.
	 * bind(Client.class).toProvider(JerseyClientProvider.class)
	 */
	protected void bindClientProvider() {
		bind(Client.class).toProvider(JerseyClientProvider.class);
	}

}
