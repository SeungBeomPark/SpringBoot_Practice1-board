package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl  implements BoardService{
	@Autowired
	private BoardMapper boardMapper;
	@Override
	public boolean registerBoard(BoardDTO params) {
		int queryResult = 0;
		
		if(params.getIdx() == null) { // 게시판 글의 idx가 존재하지 않는 경우(create)
			queryResult = boardMapper.insertBoard(params);
		}else {// 게시판 글의 idx가 존재하는 경우(update)
			queryResult = boardMapper.updateBoard(params);
		}
		return (queryResult == 1) ? true : false;
	}

	@Override
	public BoardDTO getBoardDetail(Long idx) {		
		return boardMapper.selectBoardDetail(idx);
	}

	@Override
	public boolean deleteBoard(Long idx) {
		int queryResult = 0;
		
		BoardDTO board = boardMapper.selectBoardDetail(idx);
		if(board != null && board.getDeleteYn().equals("N")) {
			queryResult = boardMapper.deleteBoard(idx);
		}
		return (queryResult == 1) ? true : false;
	}

	@Override
	public List<BoardDTO> getBoardList() {
		List<BoardDTO> boardList = Collections.emptyList();
		
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		
		if(boardTotalCount > 0) {
			boardList = boardMapper.selectBoardList();
		}
		return boardList;
	}
	@Override
	public boolean plusViewCnt(Long idx) {
		int queryResult = 0;
		
		queryResult = boardMapper.plusViewCnt(idx);		
		return (queryResult == 1) ? true : false;
	}

}
