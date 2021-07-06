package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.constant.Method;
import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import com.board.util.UiUtils;

@Controller
public class BoardController extends UiUtils {
	
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
	public String registerBoard(BoardDTO params, Model model) {
		try {
			boolean isRegister = boardService.registerBoard(params);
			if(!isRegister) {
				// 등록 실패 메시지 팝업 구현 예정(To-do)
				return showMessageWithRedirect("게시글 등록에 실패하였습니다.",
						"/board/list.do", Method.GET, null, model);
			}
		}catch(DataAccessException e) {
			// 데이터 처리 예외 팝업 구현 예정(To-do)
			return showMessageWithRedirect("데이터 처리 과정에서 문제가 발생하였습니다.",
					"/board/list.do", Method.GET, null, model);
		}catch(Exception e) {
			// 시스템 예외 팝업 구현 예정(To-do)
			return showMessageWithRedirect("시스템 문제가 발생하였습니다.",
					"/board/list.do", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("게시글 등록을 완료하였습니다.",
				"/board/list.do", Method.GET, null, model);
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(Model model) {
		List<BoardDTO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		
		return "board/list";
		
	}
	
	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@RequestParam(value = "idx", required = false)Long idx, Model model){
		if(idx == null) {
			// 올바르지않은 접근 알림표시, 리스트 목록으로 리다이렉트(To-Do)
			return "redirect:/board/list.do";
		}
		BoardDTO board = boardService.getBoardDetail(idx);
		if(board == null || "Y".equals(board.getDeleteYn())) {
			// 게시글이 없거나 삭제된 글이면 리스트 목록으로 리다이렉트
			return "redirect:/board/list.do";
		}
		model.addAttribute("board", board);
		//게시물 조회시에 조회수 증가하도록 추가
		boardService.plusViewCnt(idx);
		return "board/view";
	}
	
	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@RequestParam(value = "idx", required = false)Long idx, Model model) {
		if(idx == null) {
			// 올바르지 않은 접근 에러 메시지 호출 및 리스트로 리다이렉트(To-Do)
			return showMessageWithRedirect("올바르지 않은 접근입니다.",
					"/board/list.do", Method.GET, null, model);
		}
		
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if(!isDeleted) {
				//게시글 삭제에 실패했다는 알림 메시지 호출(To-do)
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다.",
						"/board/list.do", Method.GET, null, model);
			}
		}catch(DataAccessException e) {
			// 데이터베이스 처리 문제 메시지 호출(To-do)
			return showMessageWithRedirect("데이터 처리 과정에서 문제가 발생하였습니다.",
					"/board/list.do", Method.GET, null, model);
		}catch(Exception e) {
			// 시스템 오류 메시지 호출(To-do)
			return showMessageWithRedirect("시스템 문제가 발생하였습니다.",
					"/board/list.do", Method.GET, null, model);
		}
		
		return showMessageWithRedirect("게시글 삭제를 완료하였습니다.",
				"/board/list.do", Method.GET, null, model);
		
	}
	

}
