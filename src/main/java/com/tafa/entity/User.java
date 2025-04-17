package com.tafa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "users", schema = "leftover")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
	private Long id;
	
//	@Column(name = "name")
//	private String name;

	private int phone;

	private String email;

	private String password;

	private String address;
	
	private String username;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private String gender;
	
	private String mobile;
	
	
	@Column(name = "image", columnDefinition = "BYTEA") 
	private byte[] image;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dob;
	
	@ManyToMany
	@JoinTable(name = "friendlists", schema = "leftover", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
	private Set<User> friendlists = new HashSet<User>();
	
	
	@ManyToMany
	@JoinTable(name = "friendRequests", schema = "leftover", joinColumns = @JoinColumn(name = "requesterId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	private Set<User> sentRequests = new HashSet<User>();
	
	
	@ManyToMany(targetEntity = User.class, mappedBy = "sentRequests")
	private Set<User> receivedRequests = new HashSet<User>();
	
	@Column(name = "friend_count")
	private Integer  friendCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public Set<User> getFriendlist() {
		return friendlists;
	}
	
	public Set<Long> getfriendIds() {
		Set<Long> friendIds = new HashSet<>();
		for (User friendlist : friendlists) {
			friendIds.add(friendlist.id);
		}
		return friendIds;
	}
	
	public void setFriendlist(Set<User> friendlists) {
		this.friendlists = friendlists;
	}
	public Set<User> getSentRequests() {
		return sentRequests;
	}
	
	
	public Set<Long> getSentRequestIds() {
		Set<Long> sentRequestIds = new HashSet<>();
		for (User sentRequest : sentRequests) {
			sentRequestIds.add(sentRequest.id);
		}
		return sentRequestIds;
	}

	public void setSentRequests(Set<User> sentRequests) {
		this.sentRequests = sentRequests;
	}
	public Set<User> getReceivedRequests() {
		return receivedRequests;
	}
	
	public Set<Long> getReceivedRequestIds() {
		Set<Long> receivedRequestIds = new HashSet<>();
		for (User receivedRequest : receivedRequests) {
			receivedRequestIds.add(receivedRequest.id);
		}
		return receivedRequestIds;
	}
	public void setReceivedRequests(Set<User> receivedRequests) {
		this.receivedRequests = receivedRequests;
	}
	public Integer getFriendCount() {
		return friendCount;
	}
	public void setFriendCount(Integer friendCount) {
		this.friendCount = friendCount;
	}
	

}
