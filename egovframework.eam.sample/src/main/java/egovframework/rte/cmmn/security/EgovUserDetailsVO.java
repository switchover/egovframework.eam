package egovframework.rte.cmmn.security;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EgovUserDetailsVO implements Serializable {
    private String userId;
    private String passWord;
    private String userName;
    
    // TODO USERS 테이블 컬럼 변경
    // 세션에서 관리되는 항목 추가
    // 예) 	private String birthDay;
    /*		public String getBirthDay() {
        		return birthDay;
    		}

    		public void setBirthDay(String birthDay) {
        		this.birthDay = birthDay;
    		}
	*/
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
