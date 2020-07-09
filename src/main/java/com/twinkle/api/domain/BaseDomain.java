package com.twinkle.api.domain;

/**
 * @author cuonglv
 *
 */
public abstract class BaseDomain {
	/**
	 * Get gia tri ID
	 */
	public abstract Object getIdentifier();

	/**
	 * Set gia tri ID
	 */
	public abstract void setIdentifier(Object pk);

	public boolean isNew() {
		return (getIdentifier() == null) ? true : false;
	}
}
