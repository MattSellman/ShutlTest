package com.shutl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutl.Application;
import com.shutl.model.Item;
import com.shutl.model.QuoteSecond;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class QuoteControllerFunctionalTestSecond {

	@Autowired
	private WebApplicationContext webApplicationContext;

	ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void testBasicServiceSecondary() throws Exception {
		QuoteSecond quoteOne = new QuoteSecond("SW1A1AA", "EC2A3LT");
		MvcResult result = this.mockMvc.perform(post("/quote").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteOne)))
				.andExpect(status().isOk()).andReturn();

		QuoteSecond quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "SW1A1AA");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getPrice(), new Long(316));
	}

	
	//test variables by distance
	@Test
	public void testVariablePricingByDistance() throws Exception {
		QuoteSecond quoteTwo = new QuoteSecond("SW1A1AA", "EC2A3LT");
		MvcResult result = this.mockMvc.perform(post("/quote").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteTwo)))
				.andExpect(status().isOk()).andReturn();

		QuoteSecond quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "SW1A1AA");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getPrice(), new Long(316));

		quoteTwo = new QuoteSecond("AL15WD", "EC2A3LT");
		result = this.mockMvc.perform(post("/quote").header("X-API-Version", "Second").contentType("application/json")
				.content(objectMapper.writeValueAsString(quoteTwo))).andExpect(status().isOk()).andReturn();

		quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "AL15WD");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getPrice(), new Long(305));
	}

	//price changes
	@Test
	public void testPriceChanges() throws Exception {
		QuoteSecond quoteData = new QuoteSecond("SW1A1AA", "EC2A3LT", "bicycle");
		MvcResult result = this.mockMvc.perform(post("/variable").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteData)))
				.andExpect(status().isOk()).andReturn();

		QuoteSecond quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "SW1A1AA");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getVehicle_id(), "bicycle");
		assertEquals(quote.getPrice(), new Long(347));

		quoteData = new QuoteSecond("AL15WD", "EC2A3LT", "bicycle");
		result = this.mockMvc.perform(post("/variable").header("X-API-Version", "Second").contentType("application/json")
				.content(objectMapper.writeValueAsString(quoteData))).andExpect(status().isOk()).andReturn();

		quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "AL15WD");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getVehicle_id(), "bicycle");
		assertEquals(quote.getPrice(), new Long(335));
	}

	//price limits
	@Test
	public void testPriceLimits() throws Exception {
		QuoteSecond quoteThree = new QuoteSecond("SW1A1AA", "EC2A3LT", "bicycle");
		
		MvcResult result = this.mockMvc.perform(post("/limit").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteThree)))
				.andExpect(status().isOk()).andReturn();
		
		QuoteSecond quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "SW1A1AA");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getVehicle_id(), "bicycle");
		assertEquals(quote.getPrice(), new Long(347));
		
		quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "AL15WD");
		assertEquals(quote.getDeliveryPostcode(), "ZE1F4AF");
		assertEquals(quote.getVehicle_id(), "parcel_van");
		assertEquals(quote.getPrice(), new Long(839));		//max price of 1000 for van
	}
	
	
	//test simple volumes
	@Test
	public void testVolumeSimple() throws JsonProcessingException, Exception {
		List<Item> Items = new ArrayList<Item>();
		Items.add(new Item(20, 60, 40, 50));
		
		QuoteSecond quoteFour = new QuoteSecond("SW1A1AA", "EC2A3LT", Items);
		MvcResult result = this.mockMvc.perform(post("/simplevolumetrics").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteFour)))
				.andExpect(status().isOk()).andReturn();
		
		QuoteSecond quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "SW1A1AA");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getVehicle_id(), "parcel_car");
		assertEquals(quote.getPrice(), new Long(316));
		
		quoteFour = new QuoteSecond("AL15WD", "ZE1F4AF", Items);
		result = this.mockMvc.perform(post("/simplevolumetrics").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteFour)))
				.andExpect(status().isOk()).andReturn();
		
		quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "AL15WD");
		assertEquals(quote.getDeliveryPostcode(), "ZE1F4AF");
		assertEquals(quote.getVehicle_id(), "parcel_car");
		assertEquals(quote.getPrice(), new Long(763));		
	}
	
	
	//testing more complex volumes
	@Test
	public void testVolumeComplex() throws Exception {
		List<Item> Items = new ArrayList<Item>();
		Items.add(new Item(20, 60, 40, 50));
		Items.add(new Item(60, 126, 212, 70));

		QuoteSecond quoteFive = new QuoteSecond("SW1A1AA", "EC2A3LT", Items);
		MvcResult result = this.mockMvc.perform(post("/complexvolumetrics").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteFive)))
				.andExpect(status().isOk()).andReturn();

		QuoteSecond quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "SW1A1AA");
		assertEquals(quote.getDeliveryPostcode(), "EC2A3LT");
		assertEquals(quote.getVehicle_id(), "small_van");
		assertEquals(quote.getPrice(), new Long(316));

		quoteFive = new QuoteSecond("AL15WD", "ZE1F4AF", Items);
		result = this.mockMvc.perform(post("/complexvolumetrics").header("X-API-Version", "Second")
				.contentType("application/json").content(objectMapper.writeValueAsString(quoteFive)))
				.andExpect(status().isOk()).andReturn();

		quote = objectMapper.readValue(result.getResponse().getContentAsString(), QuoteSecond.class);
		assertEquals(quote.getPickupPostcode(), "AL15WD");
		assertEquals(quote.getDeliveryPostcode(), "ZE1F4AF");
		assertEquals(quote.getVehicle_id(), "small_van");
		assertEquals(quote.getPrice(), new Long(763));
	}
}