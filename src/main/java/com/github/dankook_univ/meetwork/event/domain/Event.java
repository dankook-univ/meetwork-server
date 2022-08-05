package com.github.dankook_univ.meetwork.event.domain;

import com.github.dankook_univ.meetwork.common.domain.Core;
import com.github.dankook_univ.meetwork.event.infra.http.response.EventResponse;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.mysema.commons.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends Core {

	@Column(nullable = false)
	private String name;

	@ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "organizer_id")
	private Profile organizer;

	@Column(unique = true, nullable = false)
	private String code;

	@Column
	private String meetingUrl;

	@Builder
	public Event(String name, Profile organizer, String code, String meetingUrl) {
		Assert.hasText(name, "name must not be empty");
		Assert.hasText(code, "name must not be empty");

		this.name = name;
		this.organizer = organizer;
		this.code = code;
		this.meetingUrl = meetingUrl;
	}

	public EventResponse toResponse() {
		return EventResponse.builder()
				.id(this.getId())
				.createdAt(this.getCreatedAt())
				.updatedAt(this.getUpdatedAt())
				.name(this.name)
				.organizer(this.organizer.toResponse())
				.code(this.code)
				.meetingUrl(this.meetingUrl)
				.build();
	}

	public Event update(String name, String code, String meetingUrl) {
		if (name != null) {
			this.name = name;
		}
		if (code != null) {
			this.code = code;
		}
		if (meetingUrl != null) {
			this.meetingUrl = meetingUrl;
		}

		return this;
	}

	public Event setOrganizer(Profile organizer) {
		this.organizer = organizer;

		return this;
	}
}
