package com.saltlux.mysite.vo;

public class PageVo {
	private Long total; 	// 전체 페이지 수
	private Long cur;  	// 현재 페이지
	private Long start; // 1~5, 6~10, ...와 같이 첫번째 페이지 숫자  
	private Long showNum;
	
	public Long getShowNum() {
		return showNum;
	}
	public void setShowNum(Long showNum) {
		this.showNum = showNum;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getCur() {
		return cur;
	}
	public void setCur(Long cur) {
		this.cur = cur;
	}
	public Long getStart() {
		return start;
	}
	public void setStart(Long start) {
		this.start = start;
	}
	@Override
	public String toString() {
		return "PageVo [total=" + total + ", cur=" + cur + ", start=" + start + ",  showNum=" + showNum	+ "]";
	}
	
	
	
}
