package com.dayside.dbrs.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dayside.dbrs.common.code.DbrsResultCode;
import com.dayside.dbrs.common.code.UserMsgConst;
import com.dayside.dbrs.member.model.Member;
import com.dayside.dbrs.member.model.MemberJoinRequest;
import com.dayside.dbrs.member.service.MemberManageService;

@RestController
public class MemberManageController {

	@Resource
	private MemberManageService memberManageService;
	
    private static final Logger logger = LoggerFactory.getLogger(MemberManageController.class);
    
    @RequestMapping(value = "/join",method = RequestMethod.POST, produces="application/json;charset=utf-8")
	public Map join(@RequestBody Member member) throws Exception {
		
		logger.debug("join param : {}" , member);
		
		Map<String,Object> resultMap = new HashMap<>();
		
		Map<String,Object> result = memberManageService.saveMemberJoinReq(member);
			
		resultMap.put("result", result);
		
		return resultMap;
	}
	
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/login", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	public Map login(HttpServletRequest req ,@RequestBody Member member) throws Exception {
		
		logger.debug("login param : {}" , member);
		
		Map<String,Object> resultMap = new HashMap<>();
		Map result = memberManageService.selectLoginCheckService(member, req);
		
		if(result != null) {
			
			if(!result.containsKey("retCode")) {
				result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
				result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
				result.put("userMsg", DbrsResultCode.OK.getResultDescStr());
			}
		} else {
			result = new HashMap<>();
			result.put("retCode", DbrsResultCode.MEMBER_INVAILD_LOGIN_REQUEST.getResultCodeStr());
			result.put("retMsg",DbrsResultCode.MEMBER_INVAILD_LOGIN_REQUEST.getResultDescStr());
			result.put("userMsg", UserMsgConst.LOGIN_NOT_MATCHED_REQUEST.getUserMsg());
		}
		
		resultMap.put("result", result);
    	
		return resultMap;
	}  
    
    @RequestMapping(value="/member/searchUser", method = RequestMethod.POST, produces="application/json;charset=utf-8")
	public Map selectSearchUser(HttpServletRequest req) throws Exception {
		
		Map<String,Object> resultMap = new HashMap<>();
		Map<String,Object> result = new HashMap<>();
		
		List<Member> joinReqList = memberManageService.selectSearchUserService();
		
		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
		
		resultMap.put("joinReqList", joinReqList);
		resultMap.put("result", result);
    	
		return resultMap;
	}  
    
    @RequestMapping(value="/member/approvalUser", method = RequestMethod.POST, produces="application/json;charset=utf-8")
  	public Map updateApprovalUser(HttpServletRequest req,@RequestBody MemberJoinRequest memberJoinRequest) throws Exception {
  		
  		logger.debug("memberJoinRequest member : {}" , memberJoinRequest);
  		
  		Map<String,Object> resultMap = new HashMap<>();
  		Map<String,Object> result = new HashMap<>();
  		
		memberManageService.updateApprovalUserService(memberJoinRequest);     
		
		result.put("retCode", DbrsResultCode.OK.getResultCodeStr());
		result.put("retMsg", DbrsResultCode.OK.getResultDescStr());
  		
  		resultMap.put("result", result);
      	
  		return resultMap;
  	}
    
    @RequestMapping(value="/member/updateUserPassword", method = RequestMethod.POST, produces="application/json;charset=utf-8")
  	public Map updateUserPassword(HttpServletRequest req, @RequestBody Member member) throws Exception {
  		
  		logger.debug("/member/updateUserPassword => Member member : {}" , member);
  		
  		Map<String,Object> resultMap = new HashMap<>();
  		Map<String,Object> result = new HashMap<>();
  		
		result = memberManageService.updateUserPassword(member,req);     
		
  		resultMap.put("result", result);
      	
  		return resultMap;
  	}  
}
