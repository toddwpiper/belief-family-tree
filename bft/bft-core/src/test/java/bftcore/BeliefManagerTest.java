package bftcore;

import junit.framework.Assert;

import org.junit.Test;

import bftcore.entity.Belief;
import bftcore.entity.testdata.BeliefTestData;
import bftcore.enumeration.ValidationMessageEnum;
import bftcore.exception.ValidationException;
import bftcore.manager.BeliefFamilyTreeManager;
import bftcore.manager.impl.BeliefFamilyTreeManagerImpl;

public class BeliefManagerTest {

	@Test
	public void testAddBelief() throws Throwable {
		/*
		 * Create BM
		 */
		BeliefFamilyTreeManager bm = new BeliefFamilyTreeManagerImpl();

		/*
		 * Create Test Data
		 */
		Belief religion1 = BeliefTestData.getReligionTestData("RELIGION1");

		/*
		 * Add to BM and verify
		 */
		bm.addBelief(religion1);

		Belief returnedBelief = bm.getBeliefByName(religion1.getName());

		Assert.assertNotNull(returnedBelief);
		Assert.assertEquals(religion1, returnedBelief);
	}

	@Test
	public void testAddParent_Validation() throws Throwable {
		/*
		 * Create BM
		 */
		BeliefFamilyTreeManager bm = new BeliefFamilyTreeManagerImpl();

		/*
		 * Create test Data
		 */
//		Belief religion1 = BeliefTestData.getReligionTestData("RELIGION1");
//
//		Belief religion1A = BeliefTestData.getReligionTestData("RELIGION1A");
//		bm.addBelief(religion1);
//
//		try {
//			bm.addBelief(religion1A);
//			Assert.fail("ValidationException not thrown");
//		} catch (ValidationException e) {
//			Assert.assertEquals(e.getMessage(), ValidationMessageEnum.PARENT_NOT_FOUND.getMessage());
//		}
//
//		bm.addBelief(religion1);
//		bm.addBelief(religion1A);
//
//		/*
//		 * Verify
//		 */
//		Belief returnedBelief = bm.getBeliefByName(religion1A.getName());
//
//		Assert.assertNotNull(returnedBelief);
//		Assert.assertEquals(religion1A, returnedBelief);
//		Assert.assertFalse(bm.getParent returnedBelief.getParents().isEmpty());
//		Assert.assertNotNull(returnedBelief.getParents().get(0));
//		Assert.assertEquals(returnedBelief.getParents().get(0), religion1);
	}
}
