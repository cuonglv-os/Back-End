package com.twinkle.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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
@Table(name = "user")
@NamedQueries({
	@NamedQuery(
            name = "User.findByUsername",
            query = "SELECT u.userName FROM User u WHERE u.userName = :userName"),
	@NamedQuery(
            name = "User.delete",
            query = "DELETE FROM User u WHERE u.userId :userId")
})
public class User extends BaseDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@ManyToMany
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private Set<Role> roles;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Order> orders = new HashSet<Order>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<PersistentLogin> persistentLogins = new HashSet<PersistentLogin>();

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return authorities;
	}

	@Override
	public Object getIdentifier() {
		return (this.getUserId() != null && this.getUserId() > 0) ? this.getUserId() : null;
	}

	@Override
	public void setIdentifier(Object pk) {
		this.setUserId((Long) pk);
	}

	public User(Long userId, String userName, String password) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}
}
