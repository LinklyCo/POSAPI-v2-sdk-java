package com.linkly.pos.sdk.models;

import java.util.List;

/**
 * Requires an implementer to provide a method to validate itself
 */
public interface IValidatable {

	/**
	 * Requests are validated before it will be sent to Linkly REST endpoints
	 * 
	 * @return List<String> (Validation results containing list of model errors (if any))
	 */
    List<String> validate();
}