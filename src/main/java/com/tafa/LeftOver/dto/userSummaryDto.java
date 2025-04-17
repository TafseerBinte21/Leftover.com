package com.tafa.LeftOver.dto;

import java.util.Arrays;

public class userSummaryDto {
	
	
		private String username;
	    private String image;
	    
		public userSummaryDto(String username,String image) {
			super();
			this.username = username;
			this.image = image;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		@Override
		public String toString() {
			return "userSummaryDto [username=" + username + ", image=" + image + "]";
		}

		
		

}
