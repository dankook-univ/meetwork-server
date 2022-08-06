package com.github.dankook_univ.meetwork.board.exceptions;

public class ExistingBoardNameException extends RuntimeException {

    public ExistingBoardNameException() {
        super("게시판 이름이 이미 존재합니다.");
    }
}
