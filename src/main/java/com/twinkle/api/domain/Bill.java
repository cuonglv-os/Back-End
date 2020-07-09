package com.twinkle.api.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "bill")
public class Bill extends BaseDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BILL_ID")
	private Long billId;
	
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "PAYMENT_DATE", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	
	@Column(name = "AMOUNT")
	private Double amount;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bill")
	private Set<Order> orders = new HashSet<Order>();

	@Override
	public Object getIdentifier() {
		return (this.getBillId() != null && this.getBillId() > 0) ? this.getBillId() : null;
	}

	@Override
	public void setIdentifier(Object pk) {
		this.setBillId((Long) pk);
	}
}
