package com.arsyux.thecar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.arsyux.thecar.domain.PostVO;
import com.arsyux.thecar.domain.PageUtils;
import com.arsyux.thecar.domain.UserVO;

@Mapper
public interface PostMapper {
	
	// ========================================
	// 1. 게시글 개수 조회
	// ========================================
	
	// 전체 게시글 개수 조회
	@Select("SELECT count(*) "
		  + "FROM tb_post")
	public int getPostCount();
	
	// 유저이름 게시글 개수 조회
	@Select("SELECT count(*) "
		  + "FROM tb_post "
		  + "WHERE userid = #{userid}")
	public int getPostCountByUserid(UserVO user);
	
	// 제목 게시글 개수 조회
	@Select("SELECT count(*) "
		  + "FROM tb_post "
		  + "WHERE title LIKE CONCAT('%', #{title}, '%')")
	public int getPostCountByTitle(PostVO post);
	
	// 내용 게시글 개수 조회
	@Select("SELECT count(*) "
		  + "FROM tb_post "
		  + "WHERE title LIKE CONCAT('%', #{content}, '%')")
	public int getPostCountByContent(PostVO post);
	
	// 이름 게시글 개수 조회
	@Select("SELECT count(*) "
		  + "FROM tb_post p, tb_user u "
		  + "WHERE p.userid = u.userid AND u.name = #{name}")
	public int getPostCountByName(PostVO post);
	
	@Select("SELECT count(*) "
			  + "FROM tb_post p, tb_user u "
			  + "WHERE p.userid = u.userid AND u.name LIKE CONCAT('%', #{name}, '%')")
		public int getPostCountByLikeName(PostVO post);
	
	// 제목내용 게시글 개수 조회
	@Select("SELECT count(*) "
		  + "FROM tb_post "
		  + "WHERE title LIKE CONCAT('%', #{title}, '%') OR content LIKE CONCAT('%', #{content}, '%')")
	public int getPostCountByTitleContent(PostVO post);
	
	// ========================================
	// 2. 게시글 조회
	// ========================================
	
	// 전체 게시글 전체 조회
	@Select("SELECT p.postid, p.state, p.title, p.regdate, p.departures_address, p.arrivals_address, u.name name "
		  + "FROM tb_user u, tb_post p "
		  + "WHERE u.userid = p.userid "
		  + "ORDER BY p.postid DESC "
		  + "LIMIT #{start}, #{size}")
	public List<PostVO> getPostList(PageUtils searchPage);
	
	// 제목 게시글 조회
	@Select("SELECT p.postid, p.state, p.title, p.regdate, p.departures_address, p.arrivals_address, u.name name "
		  + "FROM tb_user u, tb_post p "
		  + "WHERE u.userid = p.userid AND p.title LIKE CONCAT('%', #{searchTitle}, '%') "
		  + "ORDER BY p.postid DESC "
		  + "LIMIT #{start}, #{size}")
	public List<PostVO> getPostListByTitle(PageUtils searchPage);
	
	// 내용 게시글 조회
	@Select("SELECT p.postid, p.state, p.title, p.regdate, p.departures_address, p.arrivals_address, u.name name, "
		  + "FROM tb_user u, tb_post p "
		  + "WHERE u.userid = p.userid AND p.content LIKE CONCAT('%', #{searchContent}, '%') "
		  + "ORDER BY p.postid DESC "
		  + "LIMIT #{start}, #{size}")
	public List<PostVO> getPostListByContent(PageUtils searchPage);
	
	// 제목내용 게시글 조회
	@Select("SELECT p.postid, p.state, p.title, p.regdate, p.departures_address, p.arrivals_address, u.name name "
		  + "FROM tb_user u, tb_post p "
		  + "WHERE u.userid = p.userid AND (p.title LIKE CONCAT('%', #{searchTitle}, '%') OR p.content LIKE CONCAT('%', #{searchContent}, '%')) "
		  + "ORDER BY p.postid DESC "
		  + "LIMIT #{start}, #{size}")
	public List<PostVO> getPostListByTitleContent(PageUtils searchPage);
	
	// 작성자 게시글 조회
	@Select("SELECT p.postid, p.state, p.title, p.regdate, p.departures_address, p.arrivals_address, u.name name "
			  + "FROM tb_user u, tb_post p "
			  + "WHERE u.userid = p.userid AND p.name = #{searchName} "
			  + "ORDER BY p.postid DESC "
			  + "LIMIT #{start}, #{size}")
	public List<PostVO> getPostListByName(PageUtils searchPage);
	
	// 작성자 게시글 조회(Like)
	@Select("SELECT p.postid, p.state, p.title, p.regdate, p.departures_address, p.arrivals_address, u.name name "
			  + "FROM tb_user u, tb_post p "
			  + "WHERE u.userid = p.userid AND u.name LIKE CONCAT('%', #{searchName}, '%') "
			  + "ORDER BY p.postid DESC "
			  + "LIMIT #{start}, #{size}")
	public List<PostVO> getPostListByLikeName(PageUtils searchPage);
	
	// postid로 게시글 조회
	@Select("SELECT p.*, u.name name, u.phone phone "
		  + "FROM tb_user u, tb_post p "
		  + "WHERE p.userid = u.userid AND p.postid = #{postid}")
	public PostVO getPostByPostId(int postid);
	
	// 작성자의 가장 최신 게시글 조회
	@Select("SELECT p.* "
		  + "FROM tb_post p, tb_user u "
		  + "WHERE p.userid = #{userid} "
		  + "ORDER BY p.postid desc "
		  + "LIMIT 1")
	public PostVO getLastPostByUserid(UserVO user);
	
	// ========================================
	// 3. 게시글 작성, 삭제, 수정
	// ========================================
	
	// 게시글 작성
	@Insert("INSERT INTO tb_post(title, cartype, content, departures_postcode, departures_address, departures_detailAddress, departures_extraAddress, "
		  + "arrivals_postcode, arrivals_address, arrivals_detailAddress, arrivals_extraAddress, userid) "
		  + "VALUES(#{title}, #{cartype}, #{content}, #{departures_postcode}, #{departures_address}, #{departures_detailAddress}, #{departures_extraAddress}, "
		  + "#{arrivals_postcode}, #{arrivals_address}, #{arrivals_detailAddress}, #{arrivals_extraAddress}, #{userid})")
	public void insertPost(PostVO post);
	
	// 게시글 삭제
	@Delete("DELETE FROM tb_post "
		  + "WHERE postid = #{postid}")
	public void deletePost(PostVO post);
	
	// 게시글 수정
	@Update("UPDATE tb_post "
		  + "SET title = #{title}, "
		  + "cartype = #{cartype}, "
		  + "content = #{content}, "
		  + "departures_postcode = #{departures_postcode}, "
		  + "departures_address = #{departures_address}, "
		  + "departures_detailAddress = #{departures_detailAddress}, "
		  + "departures_extraAddress = #{departures_extraAddress}, "
		  + "arrivals_postcode = #{arrivals_postcode}, "
		  + "arrivals_address = #{arrivals_address}, "
		  + "arrivals_detailAddress = #{arrivals_detailAddress}, "
		  + "arrivals_extraAddress = #{arrivals_extraAddress} "
		  + "WHERE postid = #{postid}")
	public void updatePost(PostVO post);
	
	// 게시글 진행
	@Update("UPDATE tb_post "
		  + "SET state = 'P', "
		  + "price = #{price} "
		  + "WHERE postid = #{postid}")
	public void progressPost(PostVO post);
	
	// 게시글 완료
	@Update("UPDATE tb_post "
		  + "SET state = 'C' "
		  + "WHERE postid = #{postid}")
	public void completePost(PostVO post);
}
