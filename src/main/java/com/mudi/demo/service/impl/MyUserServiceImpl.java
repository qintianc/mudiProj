package com.mudi.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mudi.demo.data.MyUserMapper;
import com.mudi.demo.model.MyUser;
import com.mudi.demo.service.MyUserService;

@Service
public class MyUserServiceImpl implements MyUserService{
	
	@Autowired
	private MyUserMapper myUserMapper;

	@Override
	public MyUser getUser(String userName) {
		return myUserMapper.getUser(userName);
	}

}
