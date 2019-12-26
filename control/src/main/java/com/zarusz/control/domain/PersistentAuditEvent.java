package com.zarusz.control.domain;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Persist AuditEvent managed by the Spring Boot actuator
 * 
 * @see org.springframework.boot.actuate.audit.AuditEvent
 */
@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "JHI_PERSISTENT_AUDIT_EVENT")
public class PersistentAuditEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "event_id")
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String principal;

	@Column(name = "event_date")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime auditEventDate;
	@Column(name = "event_type")
	private String auditEventType;

	@ElementCollection
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = "JHI_PERSISTENT_AUDIT_EVT_DATA", joinColumns = @JoinColumn(name = "event_id") )
	private Map<String, String> data = new HashMap<>();
}
