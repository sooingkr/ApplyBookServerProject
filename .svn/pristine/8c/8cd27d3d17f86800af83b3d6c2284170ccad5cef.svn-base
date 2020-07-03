package com.dayside.dbrs.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dayside.dbrs.common.model.Token;
import com.dayside.dbrs.member.model.Member;
import com.dayside.dbrs.member.model.MemberJoinRequest;

@Mapper
public interface MemberManageMapper {

	public void saveMemberJoinReq(Member param) throws Exception;

	public String selectDuplicateId(String mbrId) throws Exception;

	public Token selectLoginCheckService(Member param) throws Exception;

	public List<Member> selectSearchUserService() throws Exception;

	public void updateApprovalUserService(MemberJoinRequest param) throws Exception;

	public int updateUserPassword(Member member) throws Exception;

	public Member selectUserInfoFromEmpNum(String empNum) throws Exception;

	public String selectDuplicateEmpNum(String empNum)  throws Exception;
}
