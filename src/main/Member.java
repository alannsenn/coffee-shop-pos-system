package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Connect;

public class Member {

	private String memberId, memberName, memberGender, memberEmail, memberPhone, memberDob;
	private int loyaltyPoint;
	
	public Member(String memberId, String memberName, String memberGender, String memberEmail, String memberPhone,
			String memberDob, int loyaltyPoint) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberGender = memberGender;
		this.memberEmail = memberEmail;
		this.memberPhone = memberPhone;
		this.memberDob = memberDob;
		this.loyaltyPoint = loyaltyPoint;
	}
	
	public Member(String memberId, String memberName, String memberGender, String memberEmail, String memberPhone,
			String memberDob) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberGender = memberGender;
		this.memberEmail = memberEmail;
		this.memberPhone = memberPhone;
		this.memberDob = memberDob;
	}
	
	public Member(String memberName, String memberGender, String memberEmail, String memberPhone,
			String memberDob) {
		super();
		this.memberName = memberName;
		this.memberGender = memberGender;
		this.memberEmail = memberEmail;
		this.memberPhone = memberPhone;
		this.memberDob = memberDob;
	}

	public int getLoyaltyPoint() {
		return loyaltyPoint;
	}

	public void setLoyaltyPoint(int loyaltyPoint) {
		this.loyaltyPoint = loyaltyPoint;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberDob() {
		return memberDob;
	}

	public void setMemberDob(String memberDob) {
		this.memberDob = memberDob;
	}
	
	public static Member getMemberDetail (String registeredMemberId) {
		
		String getMemberDetailQuery = "SELECT * FROM MsMember WHERE MemberId = '" + registeredMemberId + "'";
		
		ResultSet rs = Connect.getConnection().executeQuery(getMemberDetailQuery);
		String memberId = null, memberName = null, memberGender = null, memberEmail = null, memberPhone = null, memberDob = null;
		int loyaltyPoint = 0;
		
		try {
			while (rs.next()) {
				memberId = rs.getString(1);
				memberName = rs.getString(2);
				memberGender = rs.getString(3);
				memberEmail = rs.getString(4);
				memberPhone = rs.getString(5);
				memberDob = rs.getString(6);
				loyaltyPoint = rs.getInt(7);
			}

		} catch (Exception e) {
			return null;
		}
		if (memberId == null) return null;
		
		Member member = new Member(memberId, memberName, memberGender, memberEmail, memberPhone, memberDob, loyaltyPoint);
		return member;
	}
	
	public static Member createNewMember (Member newMember) {
		
		Member member = new Member(newMember.getMemberId(), newMember.getMemberName(), newMember.getMemberGender(), newMember.getMemberEmail(), newMember.getMemberPhone(), newMember.getMemberDob());
		String insertNewMemberQuery = "INSERT INTO MsMember (MemberId, MemberName, MemberGender, MemberEmail, MemberPhone, MemberDob, LoyaltyPoint) VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = Connect.getConnection().prepareStatement(insertNewMemberQuery);
		
		try {
			preparedStatement.setString(1, member.getMemberId());
			preparedStatement.setString(2, member.getMemberName());
			preparedStatement.setString(3, member.getMemberGender());
			preparedStatement.setString(4, member.getMemberEmail());
			preparedStatement.setString(5, member.getMemberPhone());
			preparedStatement.setString(6, member.getMemberDob());
			preparedStatement.setInt(7, 0);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return member;
	}
	
	public static String getLatestMemberId () {
		
		String getLatestMemberIdQUery = "SELECT MemberId FROM MsMember ORDER BY MemberId DESC LIMIT 1";
		ResultSet res = Connect.getConnection().executeQuery(getLatestMemberIdQUery);
		String latestId = "";
		
		try {
			while (res.next()) {
				latestId = res.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return latestId.substring(3);
	}
	
	public static boolean updateMemberAccount (Member member) {
		
		String updateAccountQuery = "UPDATE MsMember SET MemberName = ?, MemberGender = ?, MemberEmail = ?, MemberPhone = ?, MemberDob = ? WHERE MemberId = ?";
		PreparedStatement preparedStatement = Connect.getConnection().prepareStatement(updateAccountQuery);
		
		try {
			preparedStatement.setString(1, member.getMemberName());
			preparedStatement.setString(2, member.getMemberGender());
			preparedStatement.setString(3, member.getMemberEmail());
			preparedStatement.setString(4, member.getMemberPhone());
			preparedStatement.setString(5, member.getMemberDob());
			preparedStatement.setString(6, member.getMemberId());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
