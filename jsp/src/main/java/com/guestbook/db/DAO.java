package com.guestbook.db;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

// DB처리하는 메서드들을 가지고 있는 클래스 
public class DAO {
	// 실제 사용하는 클래스: SqlSession
	private static SqlSession ss;
	
	// 싱글턴 패턴(동기화 처리): 프로그램이 종료될 때 까지 한번에 만들어진 객체를 재사용한다.
	private synchronized static SqlSession getSession() {
		if(ss == null) {
			ss = DBService.getFactory().openSession();
		}
		return ss;
	}
	
	// DB처리하는 메서드들
	// 전체 리스트
	public static List<VO> getList(){
		List<VO> list = getSession().selectList("guestbook.list");
		return list;
	}
	
	// 정보 DB에 저장(insert, update, delete는 결과가 int)
	public static int getInsert(VO vo) {
		int result = getSession().insert("guestbook.insert", vo);
		// insert, update, delete는 커밋을 해야한다.
		ss.commit();
		return result;
	}
	
	// primary key를 이용해서 하나의 상세정보 가져오기
	public static VO getOneList(String idx) {
		VO vo = getSession().selectOne("guestbook.onelist", idx);
		return vo;
	}
}
