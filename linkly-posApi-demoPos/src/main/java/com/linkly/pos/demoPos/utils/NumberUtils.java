package com.linkly.pos.demoPos.utils;

import com.linkly.pos.sdk.common.StringUtil;

public final class NumberUtils {

	public static int toInt(String value) {
		if(StringUtil.isNullOrWhiteSpace(value)) {
			return 0;
		}
		Double parsedDouble = Double.parseDouble(value);
		Double convertedDouble = parsedDouble * 100;
		return convertedDouble.intValue();
	}
}
