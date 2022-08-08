package com.github.dankook_univ.meetwork.board.exceptions;

public class NotFoundBoardPermissionException extends RuntimeException {

    public NotFoundBoardPermissionException() {
        super("게시판을 생성 및 수정할 권한이 없습니다.");
    }

}
