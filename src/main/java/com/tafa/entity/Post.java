package com.tafa.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "post", schema = "leftover")
public class Post implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
	@SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	private String title;
	private String description;
	
    @Column(name = "reactcount")
	private int  reactCount;
	
    @Column(name = "sharecount")
	private int  shareCount;
	
    @Column(name = "createdtime")
	private LocalDateTime currentTime;
	
    
    @Column(name = "image", columnDefinition = "BYTEA") 
	private byte[] image;

	// Constructors, getters, and setters

	public Post() {
		// Default constructor
	}

	public Post(String title, String description, int reactCount, int shareCount, LocalDateTime currentTime, byte[] image, User user) {
		super();
		this.title = title;
		this.description = description;
		this.reactCount = reactCount;
		this.shareCount = shareCount;
		this.currentTime = currentTime;
		this.image = image;
		this.user = user;
	}

	// Getters and setters

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// toString() method for debugging and logging

	@Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", reactCount=" + reactCount +
                ", shareCount=" + shareCount +
                ", currentTime=" + currentTime +
                ", image=" + (image != null ? "byte[" + image.length + "]" : "null") +
                '}';
    }
	public Post orElseThrow(Object object) {
		return null;
	}

	public Integer getReactCount() {
		return reactCount;
	}

	public void setReactCount(Integer reactCount) {
		this.reactCount = reactCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public LocalDateTime  getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(LocalDateTime  currentTime) {
		this.currentTime = currentTime;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
