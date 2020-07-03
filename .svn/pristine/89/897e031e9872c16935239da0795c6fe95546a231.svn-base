package com.dayside.dbrs.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dayside.dbrs.common.code.DbrsResultCode;
import com.dayside.dbrs.common.code.UserMsgConst;
import com.dayside.dbrs.common.model.Token;
import com.dayside.dbrs.common.util.PasswordUtil;
import com.dayside.dbrs.common.util.tokenUtil;
import com.dayside.dbrs.member.mapper.MemberManageMapper;
import com.dayside.dbrs.member.model.Member;
import com.dayside.dbrs.member.model.MemberJoinRequest;

/**
 * 
 * MemberManageService
 *
 */
@Service
public class MemberManageService {
	
	 private static final Logger logger = LoggerFactory.getLogger(MemberManageService.class);
	
	@Resource
	private MemberManageMapper memberManageMapper;
	
	@Transactional
	public Map saveMemberJoinReq(Member member) throws Exception {
		Map<String,Object> result = new HashMap<>();
		
		logger.debug(member.getMbrId());
		
		String chkId = memberManageMapper.selectDuplicateId(member.getMbrId());
		String checkEmpNum = memberManageMapper.selectDuplicateEmpNum(member.getEmpNum());
		
		if(chkId != null) {
			result.put("retCode", DbrsResultCode.MEMBER_INVAILD_JOIN_REQUEST.getResultCodeStr());
			result.put("retMsg", DbrsResultCode.MEMBER_INVAILD_JOIN_REQUEST.getResultDescStr());
			result.put("userMsg", UserMsgConst.JOIN_DUPLICATE_USER_MSG.getUserMsg());
			return result;
			
		} else if(checkEmpNum != null) {
			
			result.put("retCode", DbrsResultCode.MEMBER_INVAILD_JOIN_EMPNUM_REQUEST.getResultCodeStr());
			result.put("retMsg", DbrsResultCode.MEMBER_INVAILD_JOIN_EMPNUM_REQUEST.getResultDescStr());
			result.put("userMsg", UserMsgConst.JOIN_DUPLICATE_ENUM_MSG.getUserMsg());
			return result;
		} else {
			String encPassword = PasswordUtil.passwordEncrypt(member.getMbrId(), member.getPassword());
			member.setPassword(encPassword);
			memberManageMapper.saveMemberJoinReq(member);
		}
		
		
		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
		result.put("usrMsg", DbrsResultCode.OK.getResultDescStr());
		
		return result;
	}
	

	public Map selectLoginCheckService(Member member, HttpServletRequest req) throws Exception {
		
		Map<String,Object> result = new HashMap<>();
		String encPassword = PasswordUtil.passwordEncrypt(member.getMbrId(), member.getPassword());
		member.setPassword(encPassword);
		Token mbrInfo = memberManageMapper.selectLoginCheckService(member);
		
		if(mbrInfo != null) {

			if(mbrInfo.getStatus().equals("APPROVAL")) {
				
				HttpSession sess =  req.getSession();
				sess.setAttribute("mbrInfo", mbrInfo);
				sess.setAttribute("token", tokenUtil.encryptToekn(mbrInfo));
				sess.setMaxInactiveInterval(1200);
				
				result.put("token", tokenUtil.encryptToekn(mbrInfo));
				result.put("adminYn",mbrInfo.getAdminYn());
				
			} else {
				result.put("retCode",DbrsResultCode.MEMBER_NONAPPROVAL_LOGIN_REQUEST.getResultCodeStr());
				result.put("retMsg", DbrsResultCode.MEMBER_NONAPPROVAL_LOGIN_REQUEST.getResultDescStr());
				result.put("userMsg", UserMsgConst.LOGIN_NONAPPROVAL_REQUEST.getUserMsg());
			}
		
		
		} else {
			result = null;
		}
		
		return result;
	}


	public List<Member> selectSearchUserService() throws Exception {
		return memberManageMapper.selectSearchUserService();
	}

	@Transactional
	public void updateApprovalUserService(MemberJoinRequest memberJoinRequest) throws Exception {
		
		memberManageMapper.updateApprovalUserService(memberJoinRequest);
	}
	
	@Transactional
	public Map<String,Object> updateUserPassword(Member member, HttpServletRequest req) throws Exception {
		Map<String,Object> result = new HashMap<>();

		member.setEmpNum((String) req.getAttribute("empNum"));

		String userId = null;
		String encryptedInputPassword = "";
		
		try {
			Member userInfo = memberManageMapper.selectUserInfoFromEmpNum(member.getEmpNum());
			
			if(userInfo == null) {
				result.put("retCode", DbrsResultCode.MEMBER_NO_EXIST.getResultCodeStr());
				result.put("retMsg", DbrsResultCode.MEMBER_NO_EXIST.getResultDescStr());
				result.put("userMsg", UserMsgConst.MODIFY_MEMBER_NO_EXIST.getUserMsg());
				return result;
			}
			
			encryptedInputPassword = PasswordUtil.passwordEncrypt(userInfo.getMbrId(), member.getOldPassword());
			
			if(!encryptedInputPassword.equals(userInfo.getPassword())) {
				result.put("retCode", DbrsResultCode.MEMBER_PASSWORD_NOT_MATCHED.getResultCodeStr());
				result.put("retMsg", DbrsResultCode.MEMBER_PASSWORD_NOT_MATCHED.getResultDescStr());
				result.put("userMsg",  UserMsgConst.MODIFY_MEMBER_PASSWORD_NOT_MATCHED.getUserMsg());
				return result;
			}

			String encNewPassword = PasswordUtil.passwordEncrypt(userInfo.getMbrId(), member.getPassword());
			Member updateUserInfo = new Member();
			updateUserInfo.setMbrId(userInfo.getMbrId());
			updateUserInfo.setPassword(encNewPassword);
			
			int updateCnt = memberManageMapper.updateUserPassword(updateUserInfo);
			
			if(updateCnt < 1) {
				result.put("retCode", DbrsResultCode.MEMBER_NO_EXIST.getResultCodeStr());
				result.put("retMsg", DbrsResultCode.MEMBER_NO_EXIST.getResultDescStr());
				result.put("userMsg", UserMsgConst.MODIFY_MEMBER_NO_EXIST.getUserMsg());
				return result;
			}
		} catch (Exception e) {
			result.put("retCode", DbrsResultCode.MEMBER_INVALID_UPDATE_REQUEST.getResultCodeStr());
			result.put("retMsg", DbrsResultCode.MEMBER_INVALID_UPDATE_REQUEST.getResultDescStr());
			result.put("userMsg", DbrsResultCode.MEMBER_INVALID_UPDATE_REQUEST.getResultDescStr());
			return result;
		}
		
		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
		result.put("usrMsg", DbrsResultCode.OK.getResultDescStr());
		
		return result;
	}
	
}
