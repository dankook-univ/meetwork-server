package com.github.dankook_univ.meetwork.event.application;

import com.github.dankook_univ.meetwork.event.domain.Event;
import com.github.dankook_univ.meetwork.event.exceptions.NotFoundEventPermissionException;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventCreateRequest;
import com.github.dankook_univ.meetwork.event.infra.http.request.EventUpdateRequest;
import com.github.dankook_univ.meetwork.member.domain.Member;
import com.github.dankook_univ.meetwork.member.infra.persistence.MemberRepositoryImpl;
import com.github.dankook_univ.meetwork.profile.application.ProfileServiceImpl;
import com.github.dankook_univ.meetwork.profile.domain.Profile;
import com.github.dankook_univ.meetwork.profile.exceptions.NotFoundProfileException;
import com.github.dankook_univ.meetwork.profile.infra.http.request.ProfileCreateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EventServiceImplTest {

	@Autowired
	EventServiceImpl eventService;

	@Autowired
	ProfileServiceImpl profileService;

	@Autowired
	MemberRepositoryImpl memberRepository;

	private Member createMember(String name, String email) {
		return memberRepository.save(
				Member.builder()
						.name(name)
						.email(email)
						.build()
		);
	}

	@Test
	@DisplayName("이벤트를 생성할 수 있어요.")
	public void create() {
		Member member = createMember("name", "meetwork@meetwork.kr");

		Event event = eventService.create(
				member.getId().toString(),
				EventCreateRequest.builder()
						.name("event")
						.organizer(
								ProfileCreateRequest.builder()
										.nickname("nickname")
										.bio("bio")
										.build()
						)
						.code("code")
						.build()
		);

		assertThat(event).isNotNull();
		assertThat(event).isInstanceOf(Event.class);

		assertThat(event.getOrganizer()).isNotNull();
		assertThat(event.getOrganizer()).isInstanceOf(Profile.class);
		assertThat(event.getOrganizer().getMember().getId()).isEqualTo(member.getId());
		assertThat(event.getOrganizer().getIsAdmin()).isTrue();
	}

	@Test
	@DisplayName("이벤트에 참여할 수 있어요.")
	public void join() {
		Event event = eventService.create(
				createMember("name", "meetwork@meetwork.kr").getId().toString(),
				EventCreateRequest.builder()
						.name("event")
						.organizer(
								ProfileCreateRequest.builder()
										.nickname("nickname")
										.bio("bio")
										.build()
						)
						.code("code")
						.build()
		);

		Member member = createMember("participant_name", "participant@meetwork.kr");

		Event joinedEvent = eventService.join(
				member.getId().toString(),
				event.getId().toString(),
				ProfileCreateRequest.builder()
						.nickname("participant_nickname")
						.bio("participant")
						.build()
		);

		Profile profile = profileService.get(member.getId().toString(), event.getId().toString());

		assertThat(joinedEvent).isNotNull();
		assertThat(joinedEvent).isInstanceOf(Event.class);
		assertThat(joinedEvent.getId()).isEqualTo(event.getId());

		assertThat(event.getOrganizer().getId()).isNotEqualTo(member.getId());

		assertThat(profile).isNotNull();
		assertThat(profile).isInstanceOf(Profile.class);
		assertThat(profile.getMember().getId()).isEqualTo(member.getId());
		assertThat(profile.getEvent().getId()).isEqualTo(event.getId());
	}

	@Test
	@DisplayName("이벤트를 조회할 수 있어요.")
	public void getEvent() {
		Member member = createMember("name", "meetwork@meetwork.kr");

		Event event = eventService.create(
				member.getId().toString(),
				EventCreateRequest.builder()
						.name("event")
						.organizer(
								ProfileCreateRequest.builder()
										.nickname("nickname")
										.bio("bio")
										.build()
						)
						.code("code")
						.build()
		);

		Event findEvent = eventService.get(member.getId().toString(), event.getId().toString());

		assertThat(findEvent).isNotNull();
		assertThat(findEvent).isInstanceOf(Event.class);

		assertThat(findEvent.getId()).isEqualTo(event.getId());
	}

	@Test
	@DisplayName("이벤트를 수정할 수 있어요.")
	public void update() {
		Member member = createMember("name", "meetwork@meetwork.kr");

		Event event = eventService.create(
				member.getId().toString(),
				EventCreateRequest.builder()
						.name("event")
						.organizer(
								ProfileCreateRequest.builder()
										.nickname("nickname")
										.bio("bio")
										.build()
						)
						.code("code")
						.build()
		);

		Event updatedEvent = eventService.update(
				member.getId().toString(),
				event.getId().toString(),
				EventUpdateRequest.builder()
						.name("new_name")
						.code("new_code")
						.meetingUrl("meeting_url")
						.build()
		);

		assertThat(updatedEvent).isNotNull();
		assertThat(updatedEvent).isInstanceOf(Event.class);

		assertThat(updatedEvent.getName()).isEqualTo("new_name");
		assertThat(updatedEvent.getCode()).isEqualTo("new_code");
		assertThat(updatedEvent.getMeetingUrl()).isEqualTo("meeting_url");
	}

	@Test
	@DisplayName("이벤트를 수정에 실패해요.")
	public void failedUpdate() {
		Event event = eventService.create(
				createMember("name", "meetwork@meetwork.kr").getId().toString(),
				EventCreateRequest.builder()
						.name("event")
						.organizer(
								ProfileCreateRequest.builder()
										.nickname("nickname")
										.bio("bio")
										.build()
						)
						.code("code")
						.build()
		);

		Member member = createMember("participant_name", "participant@meetwork.kr");

		eventService.join(
				member.getId().toString(),
				event.getId().toString(),
				ProfileCreateRequest.builder()
						.nickname("participant_nickname")
						.bio("participant")
						.build()
		);

		Assertions.assertThrows(NotFoundEventPermissionException.class, () -> {
			eventService.update(
					member.getId().toString(),
					event.getId().toString(),
					EventUpdateRequest.builder()
							.name("new_name")
							.code("new_code")
							.meetingUrl("meeting_url")
							.build()
			);
		});
	}

	@Test
	@DisplayName("이벤트를 삭제할 수 있어요.")
	public void delete() {
		Member member = createMember("name", "meetwork@meetwork.kr");

		Event event = eventService.create(
				member.getId().toString(),
				EventCreateRequest.builder()
						.name("event")
						.organizer(
								ProfileCreateRequest.builder()
										.nickname("nickname")
										.bio("bio")
										.build()
						)
						.code("code")
						.build()
		);

		eventService.delete(
				member.getId().toString(),
				event.getId().toString()
		);

		assertThat(event).isNotNull();

		Assertions.assertThrows(NotFoundProfileException.class, () -> {
			eventService.get(member.getId().toString(), event.getId().toString());
		});
	}

	@Test
	@DisplayName("이벤트를 삭제에 실패해요.")
	public void failed_delete() {
		Event event = eventService.create(
				createMember("name", "meetwork@meetwork.kr").getId().toString(),
				EventCreateRequest.builder()
						.name("event")
						.organizer(
								ProfileCreateRequest.builder()
										.nickname("nickname")
										.bio("bio")
										.build()
						)
						.code("code")
						.build()
		);

		Member member = createMember("participant_name", "participant@meetwork.kr");

		eventService.join(
				member.getId().toString(),
				event.getId().toString(),
				ProfileCreateRequest.builder()
						.nickname("participant_nickname")
						.bio("participant")
						.build()
		);

		Assertions.assertThrows(NotFoundEventPermissionException.class, () -> {
			eventService.delete(
					member.getId().toString(),
					event.getId().toString()
			);
		});
	}
}