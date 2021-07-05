package com.board.service;

import java.util.List;

import com.board.domain.BoardDTO;

public interface BoardService {
	
	public boolean registerBoard(BoardDTO params);
	
	public BoardDTO getBoardDetail(Long idx);
	
	public boolean deleteBoard(Long idx);
	
	public List<BoardDTO> getBoardList();
	
	// 게시글 조회수 증가 로직 추가
	public boolean plusViewCnt(Long idx);
	

}
