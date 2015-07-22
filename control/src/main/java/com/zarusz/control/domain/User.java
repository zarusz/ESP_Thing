package com.zarusz.control.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import org.hibernate.annotations.Type;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

/**
 * A user.
 */
@Data
@EqualsAndHashCode(of = { "id" }, callSuper = false)
@Entity
@Table(name = "JHI_USER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Pattern(regexp = "^[a-z0-9]*$")
	@Size(min = 1, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String login;

	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(length = 60)
	private String password;

	@Size(max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;

	@Size(max = 50)
	@Column(name = "last_name", length = 50)
	private String lastName;

	@Email
	@Size(max = 100)
	@Column(length = 100, unique = true)
	private String email;

	@Column(nullable = false)
	private boolean activated = false;

	@Size(min = 2, max = 5)
	@Column(name = "lang_key", length = 5)
	private String langKey;

	@Size(max = 20)
	@Column(name = "activation_key", length = 20)
	@JsonIgnore
	private String activationKey;

	@Size(max = 20)
	@Column(name = "reset_key", length = 20)
	private String resetKey;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "reset_date", nullable = true)
	private DateTime resetDate = null;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "JHI_USER_AUTHORITY", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_name", referencedColumnName = "name") })
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Authority> authorities = new HashSet<>();
}
