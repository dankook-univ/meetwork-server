package com.github.dankook_univ.meetwork.board.exceptions;

public class NotFoundBoardException extends RuntimeException {

    public NotFoundBoardException() {
        super("게시판이 존재하지 않습니다.");
    }
}
