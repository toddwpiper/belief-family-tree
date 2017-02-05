package com.elementalprime.bft.jpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.elementalprime.bft.jpa.config.JPAConfig;
import com.elementalprime.bft.jpa.config.TestJPAConfig;
import com.elementalprime.bft.jpa.entity.Belief;
import com.elementalprime.bft.jpa.enums.BeliefType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestJPAConfig.class})
@Transactional
public class BeliefRepositoryTest {
	
	@Autowired
	private BeliefRepository beliefRepository;
	
	@PersistenceContext(unitName = JPAConfig.NAME_PERSISTENCE_UNIT)
	private EntityManager em;
	
	@Test
	public void testSave() {
		Belief belief = new Belief();
		belief.setBeliefType(BeliefType.OTHER);
		belief.setName("DAO");
		
		Belief persisted = beliefRepository.saveAndFlush(belief);
		Assert.assertNotNull(persisted.getId());
		em.clear();
		
		Belief found = beliefRepository.findOne(persisted.getId());
		Assert.assertNotNull(found);
	}
}
