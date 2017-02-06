package com.elementalprime.bft.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.elementalprime.bft.jpa.entity.Belief;

/**
 * Please take a look at the web.xml where JAX-RS is enabled.
 * 
 * Full URI is something like:
 * http://localhost:8080/ipd-extract-rs/rest/verify/count
 * 
 * @author bimlij
 * 
 */
@RestController
@Transactional
@RequestMapping(value = "/belief", produces = MediaType.APPLICATION_JSON_VALUE)
public class BeliefController {

	private static final Logger LOG = LoggerFactory.getLogger(BeliefController.class);

	@RequestMapping(method = RequestMethod.GET)
	public Belief get(@RequestParam(value ="id", required=true) Integer id) {

		Belief belief = new Belief();
		belief.setId(id);
		belief.setName("mv");
		return belief;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Belief create(@RequestParam(name = "belief") Belief belief) {
		belief.setName("create");

		return belief;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public Belief update(@RequestParam(name = "belief") Belief belief) {
		belief.setName("update");

		return belief;
	}
}