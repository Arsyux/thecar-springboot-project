package com.arsyux.thecar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.arsyux.thecar.domain.Post;
import com.arsyux.thecar.domain.User;

@Mapper
public interface PostMapper {

	// 회원 가입
	// pk는 AUTO_INCREMENT처리
	// cnt는 DEFAULT 0처리
	// regdate는 CURRENT_TIMESTAMP 처리
	@Insert("INSERT INTO POST(TITLE, CONTENT, UID) VALUES(#{title}, #{content}, #{uid})")
	public void insertPost(Post post);
	
	// 메인 화면 게시글 조회
	@Select("SELECT p.id, p.title, u.name name FROM USER u, POST p WHERE u.id = p.uid ORDER BY p.id DESC")
	public List<Post> getMainList();
	
	// 게시글 전체 조회
	//@Select("SELECT * FROM USER ORDER BY USERNAME DESC")
	//public List<User> getUserList();
	
	
}