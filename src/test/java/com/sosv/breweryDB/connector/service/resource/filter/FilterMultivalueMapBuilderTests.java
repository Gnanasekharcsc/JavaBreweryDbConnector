package com.sosv.breweryDB.connector.service.resource.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.ws.rs.core.MultivaluedMap;

import org.junit.Before;
import org.junit.Test;

import com.sosv.breweryDB.connector.entity.Available;
import com.sosv.breweryDB.connector.entity.Glass;
import com.sosv.breweryDB.connector.service.resource.filter.beer.BeerFilter;
import com.sosv.breweryDB.connector.service.resource.filter.beer.BeersFilter;
import com.sosv.breweryDB.connector.service.resource.filter.beer.IBeersFilter;
import com.sosv.breweryDB.connector.service.resource.filter.brewery.BreweryFilter;
import com.sosv.breweryDB.connector.service.resource.filter.search.SearchFilter;

public class FilterMultivalueMapBuilderTests {

	private FilterMultivalueMapBuilder builder;

	public FilterMultivalueMapBuilderTests() {
		super();
	}

	@Before
	public void setUp() {
		this.builder = new FilterMultivalueMapBuilder();
	}

	@Test 
	public void testBeerFilter(){
		BeerFilter bf = (BeerFilter) BeerFilter.createWithBreweriesFilter(true);
		MultivaluedMap<String, String> result = builder.convert(bf);
		assertTrue(result.containsKey("withBreweries"));
		assertEquals("Y", result.getFirst("withBreweries"));
	}
	
	@Test 
	public void testBreweryFilter(){
		BreweryFilter bf = (BreweryFilter) BreweryFilter.createWithLocationsFilter(true);
		MultivaluedMap<String, String> result = builder.convert(bf);
		assertTrue(result.containsKey("withLocations"));
		assertEquals("Y", result.getFirst("withLocations"));
	}
	
	@Test
	public void testNameFilterAndConvertion() {
		IBeersFilter filter = BeersFilter.createNameFilter("Test");
		MultivaluedMap<String, String> result = builder.convert(filter);
		assertTrue(result.containsKey("name"));
		assertFalse(result.containsKey("abv"));
		assertFalse(result.containsKey("availableId"));
		assertFalse(result.containsKey("glasswareId"));
		assertFalse(result.containsKey("ibu"));
		assertFalse(result.containsKey("ids"));
		assertFalse(result.containsKey("since"));
		assertFalse(result.containsKey("sort"));
		assertFalse(result.containsKey("srmId"));
		assertFalse(result.containsKey("styleId"));
		assertFalse(result.containsKey("year"));
		assertFalse(result.containsKey("withBreweries"));
		assertFalse(result.containsKey("isOrganic"));
	}

	@Test
	public void testNameSortAndConvertion() {
		IBeersFilter filter = BeersFilter.createSortFilter(Sorting.DESC);
		MultivaluedMap<String, String> result = builder.convert(filter);
		assertFalse(result.containsKey("name"));
		assertFalse(result.containsKey("abv"));
		assertFalse(result.containsKey("availableId"));
		assertFalse(result.containsKey("glasswareId"));
		assertFalse(result.containsKey("ibu"));
		assertFalse(result.containsKey("ids"));
		assertFalse(result.containsKey("since"));
		assertTrue(result.containsKey("sort"));
		assertFalse(result.containsKey("srmId"));
		assertFalse(result.containsKey("styleId"));
		assertFalse(result.containsKey("year"));
		assertFalse(result.containsKey("withBreweries"));
		assertFalse(result.containsKey("isOrganic"));
		assertEquals("DESC", result.getFirst("sort"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testIdsException() {
		BeersFilter filter = (BeersFilter) BeersFilter.createSortFilter(Sorting.DESC);
		filter.setIds(Arrays.asList(new String[] { "1", "11", "111", "1111",
				"11111", "111111", "1111111", "2", "22", "222", "2222" }));
		builder.convert(filter);
	}
	
	@Test
	public void testSearchFilter(){
		SearchFilter sf = new SearchFilter();
		sf.setWithAlternateNames(true);
		sf.setWithBreweries(true);
		sf.setWithIngredients(true);
		sf.setWithLocations(true);
		sf.setWithSocialAccounts(true);
		
		MultivaluedMap<String, String> result = builder.convert(sf);
		assertEquals("Y", result.getFirst("withAlternateNames"));
		assertEquals("Y", result.getFirst("withBreweries"));
		assertEquals("Y", result.getFirst("withIngredients"));
		assertEquals("Y", result.getFirst("withLocations"));
		assertEquals("Y", result.getFirst("withSocialAccounts"));
	}

	@Test
	public void testSearchFilterNothing(){
		SearchFilter sf = new SearchFilter();
		
		MultivaluedMap<String, String> result = builder.convert(sf);
		assertEquals(0, result.size());
	}
	
	@Test
	public void testSearchFilterNo(){
		SearchFilter sf = new SearchFilter();
		sf.setWithAlternateNames(false);
		sf.setWithBreweries(false);
		sf.setWithIngredients(false);
		sf.setWithLocations(false);
		sf.setWithSocialAccounts(false);
		
		MultivaluedMap<String, String> result = builder.convert(sf);
		assertEquals("N", result.getFirst("withAlternateNames"));
		assertEquals("N", result.getFirst("withBreweries"));
		assertEquals("N", result.getFirst("withIngredients"));
		assertEquals("N", result.getFirst("withLocations"));
		assertEquals("N", result.getFirst("withSocialAccounts"));
	}
	
	@Test
	public void testIdsAndSorting() {
		BeersFilter filter = (BeersFilter) BeersFilter.createSortFilter(Sorting.DESC);
		filter.setIds(Arrays.asList(new String[] { "1", "11", "111", "1111" }));
		
		Available available = new Available();
		available.setId("XIX");
		filter.setAvailable(available);
		
		Glass glass = new Glass();
		glass.setId(1);
		filter.setGlassware(glass);
		
		filter.setWithBreweries(true);
		filter.setOrganic(false);
		
		MultivaluedMap<String, String> result = builder.convert(filter);
		assertFalse(result.containsKey("name"));
		assertFalse(result.containsKey("abv"));
		assertTrue(result.containsKey("availableId"));
		assertTrue(result.containsKey("glasswareId"));
		assertFalse(result.containsKey("ibu"));
		assertTrue(result.containsKey("ids"));
		assertFalse(result.containsKey("since"));
		assertTrue(result.containsKey("sort"));
		assertFalse(result.containsKey("srmId"));
		assertFalse(result.containsKey("styleId"));
		assertFalse(result.containsKey("year"));
		assertTrue(result.containsKey("withBreweries"));
		assertTrue(result.containsKey("isOrganic"));
		assertEquals("1,11,111,1111", result.getFirst("ids"));
		assertEquals("DESC", result.getFirst("sort"));
		assertEquals("XIX", result.getFirst("availableId"));
		assertEquals("1", result.getFirst("glasswareId"));
		assertEquals("Y", result.getFirst("withBreweries"));
		assertEquals("N", result.getFirst("isOrganic"));
	}
}
