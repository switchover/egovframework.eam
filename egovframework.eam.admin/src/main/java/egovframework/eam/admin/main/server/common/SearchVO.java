package egovframework.eam.admin.main.server.common;

public class SearchVO {
	private String searchCondition;
	private String searchWord;
	private String subCondition;
	
	public String getSearchCondition() {
		return searchCondition == null ? "" : searchCondition;
	}
	
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}
	
	public String getSearchWord() {
		return searchWord == null ? "" : searchWord;
	}
	
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSubCondition() {
		return subCondition == null ? "" : subCondition;
	}

	public void setSubCondition(String subCondition) {
		this.subCondition = subCondition;
	}
	
	
}
