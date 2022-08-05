package com.github.dankook_univ.meetwork.event.exceptions;

public class NotFoundEventException extends IllegalArgumentException {
	public NotFoundEventException() {
		super("프로필을 찾을 수 없습니다.");
	}
}
