package com.twinkle.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role extends BaseDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Long roleId;
	
	@Column(name = "ROLE_NAME")
	private String roleName;

	@Override
	public Object getIdentifier() {
		return (this.getRoleId() != null && this.getRoleId() > 0) ? this.getRoleId() : null;
	}
	
	@Override
	public void setIdentifier(Object pk) {
		this.setRoleId((Long) pk);
	}
}
