package com.saltlux.mysite.vo;

public class BoardVo {
	private Long no;
	private String title;
	private String writer;
	private String contents;
	private Long count;
	private String regDate;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", writer=" + writer + ", contents=" + contents + ", count="
				+ count + ", regDate=" + regDate + "]";
	} 
	
}
