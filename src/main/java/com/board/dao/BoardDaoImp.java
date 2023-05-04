package com.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.board.domain.BoardCommand;

@Service("boardDaoImp") //Dao와 controller의 중간
public class BoardDaoImp extends SqlSessionDaoSupport implements BoardDao {
	
		//검색분야에 따른 검색어까지 조회(페이징 처리)
		public List<BoardCommand> list(Map<String, Object> map) {
			// TODO Auto-generated method stub                      //Board.selectList (O)
			List<BoardCommand> list=getSqlSession().selectList("selectList",map);
			System.out.println("ListDaoImpl 테스트중입니다.~");
			return list;
		}
		
		
		public int getRowCount(Map<String, Object> map) {
			// TODO Auto-generated method stub
			System.out.println("getRowCount() 호출됨!!");
			return getSqlSession().selectOne("selectCount",map);
		}
	
		//최대값
		public int getNewSeq() {
			// TODO Auto-generated method stub
			int newseq=(Integer)getSqlSession().selectOne("getNewSeq");
			System.out.println("getNewSeq의 newseq : "+newseq);
			return newseq;
		}
		
		//글쓰기
		public void insert(BoardCommand board) {
			// TODO Auto-generated method stub
			getSqlSession().insert("insertBoard",board);
		}
		
		//상세보기
		public BoardCommand selectBoard(Integer seq) {
			// TODO Auto-generated method stub
			return (BoardCommand)getSqlSession().selectOne("selectBoard", seq);
		}
		
		//조회수 증가
		public void updateHit(Integer seq) {
			// TODO Auto-generated method stub
			getSqlSession().update("updateHit",seq);
		}
		
		//글수정하기
		public void update(BoardCommand board) {
			// TODO Auto-generated method stub
			getSqlSession().update("updateBoard",board);
		}
		
		//글삭제하기
		public void delete(Integer seq) {
			// TODO Auto-generated method stub
			getSqlSession().delete("deleteBoard",seq);
		}
}
