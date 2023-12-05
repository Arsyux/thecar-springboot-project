package com.arsyux.thecar.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileVO {

	private int fileid; // 파일 번호(PK)
	private int postid; // 게시글 번호 (FK)
	private String original_name; // 원본 파일명
	private String save_name; // 저장 파일명
	private long size; // 파일 크기
	
	@Builder
	public FileVO(String original_name, String save_name, long size) {
		this.original_name = original_name;
		this.save_name = save_name;
		this.size = size;
	}
	
	// 파일은 게시글이 INSERT된 후에 처리되어야함.
	public void setPostId(int postid) {
		this.postid = postid;
	}
	
}