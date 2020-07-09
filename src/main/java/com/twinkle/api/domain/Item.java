package com.twinkle.api.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author cuonglv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item extends BaseDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITEM_ID")
	private Long itemId;
	
	@Column(name = "ITEM_NAME")
	private String itemName;
	
	@Column(name = "ITEM_PRICE")
	private Double itemPrice;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
	private Set<Order> orders = new HashSet<Order>();

	@Override
	public Object getIdentifier() {
		return (this.getItemId() != null && this.getItemId() > 0) ? this.getItemId() : null;
	}

	@Override
	public void setIdentifier(Object pk) {
		this.setItemId((Long) pk);
	}
}
