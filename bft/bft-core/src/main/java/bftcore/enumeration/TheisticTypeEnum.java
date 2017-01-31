package bftcore.enumeration;

public enum TheisticTypeEnum {

	NONTHEISM("N", "0", "N", "N", "N", "N"), MONOTHEISM("Y", "1", "N", "N",
			"Y", "NA"), POLYTHEISM("NA", "*", "Y", "N", "Y", "NA"), HENOTHEISM(
			"NA", "*", "N", "N", "Y", "NA"), PANENTHEISM("NA", "*", "N", "N",
			"Y", "N"), DUALISM("NA", "2", "Y", "N", "Y", "NA"), PANTHEISM("Y",
			"*", "N", "N", "Y", "Y"), KATHENOTHEISM("NA", "*", "N", "Y", "N",
			"NA"), MONOLATRISM("NA", "*", "N", "N", "N", "NA");

	private String singleSource;
	private String numberOfGods;
	private String multipleWorship;
	private String temporaryGod;
	private String allGodsEqual;
	private String godEqualToUniverse;

	private TheisticTypeEnum(String singleSource, String numberOfGods,
			String multipleWorship, String temporaryGod,
			String allGodsEqual, String godEqualToUniverse) {
		this.singleSource = singleSource;
		this.numberOfGods = numberOfGods;
		this.multipleWorship = multipleWorship;
		this.temporaryGod = temporaryGod;
		this.allGodsEqual = allGodsEqual;
		this.godEqualToUniverse = godEqualToUniverse;
	}

	public static TheisticTypeEnum calculateType(String singleSource,
			String numberOfGods, String multipleWorship,
			String temporaryGod, String allGodsEqual,
			String godEqualToUniverse) {

		TheisticTypeEnum theisticType = null;

		TheisticTypeEnum[] values = TheisticTypeEnum.values();

		for (int i = 0; i < values.length; i++) {
			TheisticTypeEnum theisticTypeEnum = values[i];

			boolean result = theisticTypeEnum.singleSource.equals(singleSource);
			
			result = result
					&& equalsNullsafe(theisticTypeEnum.numberOfGods, numberOfGods);
			result = result
					&& equalsNullsafe(theisticTypeEnum.allGodsEqual, allGodsEqual);
			result = result
					&& equalsNullsafe(theisticTypeEnum.godEqualToUniverse, godEqualToUniverse);
			result = result
					&& equalsNullsafe(theisticTypeEnum.multipleWorship, multipleWorship);
			result = result
					&& equalsNullsafe(theisticTypeEnum.temporaryGod, temporaryGod);

			if (result) {
				if (theisticType != null) {
					throw new IllegalStateException(
							"Belief qualifies as two Theistic Types ["
									+ theisticType.name() + ", "
									+ theisticTypeEnum.name() + "]");
				}

				theisticType = theisticTypeEnum;
			}
		}
		
		if(theisticType == null)
		{	
			return TheisticTypeEnum.NONTHEISM; 
			
//			throw new IllegalStateException("Could not find Theistic Type"
//					+"[singleSource =" + singleSource 
//					+"][numberOfGods =" + numberOfGods
//					+"][allGodsEqual =" + allGodsEqual
//					+"][godEqualToUniverse =" + godEqualToUniverse
//					+"][multipleWorship =" + multipleWorship
//					+"][temporaryGod =" + temporaryGod +"]");
		}

		return theisticType;

	}

	private static boolean equalsNullsafe(String base,
			String compare) {

		boolean result = true; 
		
		if((base != null && !"NA".equalsIgnoreCase(base)))
		{
			result = base.equalsIgnoreCase(compare);
		}
			
		return result;
	}
	
	

}
