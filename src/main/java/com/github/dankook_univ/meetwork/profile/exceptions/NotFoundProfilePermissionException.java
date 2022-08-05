package com.github.dankook_univ.meetwork.profile.exceptions;

public class NotFoundProfilePermissionException extends IllegalArgumentException {
	public NotFoundProfilePermissionException() {
		super("해당 프로필에 대한 권한이 없습니다.");
	}
}
