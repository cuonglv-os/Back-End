package com.twinkle.api.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "persistent_login")
@NamedQueries({
	@NamedQuery(
            name = "PersistentLogin.findByUserIdAndStatus",
            query = "FROM PersistentLogin p WHERE p.user.userId = :userId AND p.status = :status"),
	@NamedQuery(
            name = "PersistentLogin.findByToken",
            query = "FROM PersistentLogin p WHERE p.token = :token")
})
public class PersistentLogin extends BaseDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
    private User user;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "LAST_USED", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "STATUS")
	private boolean status;

	@Override
	public Object getIdentifier() {
		return (this.getId() != null && this.getId() > 0) ? this.getId() : null;
	}
	
	@Override
	public void setIdentifier(Object pk) {
		this.setId((Long) pk);
	}
}
