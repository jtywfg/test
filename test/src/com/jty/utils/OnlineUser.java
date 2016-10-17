/**
 * 
 */
package com.jty.utils;

/**
 * 在线用户信息
 * @author xiongx
 */
public class OnlineUser {
	private Long userId;
	private String userName;
	private String name;
	private Integer adminUserFlag;
	private String roleIds;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAdminUserFlag() {
		return adminUserFlag;
	}
	public void setAdminUserFlag(Integer adminUserFlag) {
		this.adminUserFlag = adminUserFlag;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

}
