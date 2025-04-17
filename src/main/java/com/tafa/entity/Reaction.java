package com.tafa.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "reaction", schema = "leftover")
public class Reaction implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
	@SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;


	@ManyToOne
	@JoinColumn(name = "postId")
	private Post post;
	
	@Column(name = "reactioncount")
	private int reactionCount;
	
	
	
	public Reaction(Long id, User user, Post post, int reactionCount) {
		super();
		this.id = id;
		this.user = user;
		this.post = post;
		this.reactionCount = reactionCount;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Post getPost() {
		return post;
	}



	public void setPost(Post post) {
		this.post = post;
	}



	public int getReactionCount() {
		return reactionCount;
	}



	public void setReactionCount(int reactionCount) {
		this.reactionCount = reactionCount;
	}



	@Override
	public String toString() {
		return "Reaction [id=" + id + ", user=" + user + ", post=" + post + ", reactionCount=" + reactionCount + "]";
	}

	
	

}
