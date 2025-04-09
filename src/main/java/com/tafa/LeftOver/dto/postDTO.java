package com.tafa.LeftOver.dto;

public class postDTO {
	private Long id;
    private String title;
    private String description;
    private byte[] image; // You can also return this as Base64 string if needed
    private Long userId;
    private String currentTime;
    private int reactCount;
    private int shareCount;

    // Constructors
    public postDTO() {
    }

    public postDTO(Long id, String title, String description, byte[] image, String currentTime,
                   int reactCount, int shareCount, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.setCurrentTime(currentTime);
        this.reactCount = reactCount;
        this.shareCount = shareCount;
        this.userId = userId;
    }

    // Getters and Setters

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getReactCount() {
        return reactCount;
    }

    public void setReactCount(int reactCount) {
        this.reactCount = reactCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
}
