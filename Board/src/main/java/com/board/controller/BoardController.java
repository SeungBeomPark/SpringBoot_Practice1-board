package com.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model) {
		if(idx == null) {
			model.addAttribute("board", new BoardDTO());
		}else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if(board == null) {
				return "redirect:/board/list.do";
			}
			model.addAttribute("board", board);
		}
		
		return "board/write";
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(BoardDTO params) {
		try {
			boolean isRegister = boardService.registerBoard(params);
			if(!isRegister) {
				// 등록 실패 메시지 팝업 구현 예정
			}
		}catch(DataAccessException e) {
			// 데이터 처리 예외 팝업 구현 예정
		}catch(Exception e) {
			// 시스템 예외 팝업 구현 예정
		}
		
		return "redirect:/board/list.do";
	}
	

}
