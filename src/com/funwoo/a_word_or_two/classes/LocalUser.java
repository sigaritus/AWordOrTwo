package com.funwoo.a_word_or_two.classes;

import java.io.Serializable;
import java.util.ArrayList;

import com.funwoo.a_word_or_two.classes.LBMessage.t_userType;
import com.funwoo.a_word_or_two.userInfo.CollectedLocation;


public class LocalUser implements Serializable{
	
	public enum GenderType
	{
		gt_male,gt_female,gt_unknow;
	}
	
//	public enum PortraitType
//	{
//		pt_unChoose,
//		pt_male_1,pt_male_2,pt_male_3,pt_male_4,pt_male_5,
//		pt_female_1,pt_female_2,pt_female_3,pt_female_4,pt_female_5,
//		pt_unknow_1,pt_unknow_2,pt_unknow_3,pt_unknow_4,pt_unknow_5,
//		pt_custom;
//	}
	
	public String userName;//�û���
	public int userID;//�û�id
	public t_userType userType;//�û����
	public JCYLocation location;//�û���ǰλ��
//	public PortraitType portraitType;//�û�ͷ������
	public int portraitType;
	public int maxPortraitType;
	public JCYLocation startLocation;//�û���ʼ�����ĵ�
	public double startZoom;//�û���ʼ�����ż���
	
	public ArrayList<CollectedLocation> collectedLocations;//�û��ղصص�
	
	public LocalUser()
	{
		maxPortraitType = 55;
		location = new JCYLocation(106.583614, 29.563499);
		startLocation = new JCYLocation(106.583614, 29.563499);
	}
	
	
}
