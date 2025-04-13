package com.tafa.LeftOver.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tafa.LeftOver.dto.userDTO;
import com.tafa.LeftOver.repository.UsersRepository;
import com.tafa.entity.User;




@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
    private EntityManager entityManager;
	
	@Transactional
	public User save(User entity) {
		return userRepository.save(entity);
	}
	
	public User registerUser(User user) {
        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use!");
           
        }
       
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        		Collections.emptyList());
	}
	

	
	public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
	
	public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
	
	public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
	

	public void requestFriend(User user, User friend) {
		user.getSentRequests().add(friend);
		friend.getReceivedRequests().add(user);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(friend);
	}

	
	public void cancelRequest(User user, User friend) {
		user.getSentRequests().remove(friend);
		friend.getReceivedRequests().remove(user);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(friend);
	}

	
	public void acceptRequest(User user, User friend) {
		user.getFriendlist().add(friend);
		System.out.println("Before: " + user.getReceivedRequests().size());
		user.getReceivedRequests().remove(friend);
		System.out.println("After: " + user.getReceivedRequests().size());
		friend.getFriendlist().add(user);
		friend.getSentRequests().remove(user);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(friend);
	}

	
	public void rejectFriendRequest(User user, User friend) {
		user.getReceivedRequests().remove(friend);
		friend.getSentRequests().remove(user);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(friend);
	}
	
	public void delete(User user, User friend) {
		user.getFriendlist().remove(friend);
		friend.getFriendlist().remove(user);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(friend);
	}
	
	
	public Map<String, Object> getUserDetailsById(Long userId) {
	    Map<String, Object> response = new HashMap<>();

	    String sql = "SELECT u.username, u.email, u.mobile, u.address, " +
	                 "encode(u.image, 'base64'), u.friend_count " +
	                 "FROM leftover.users u WHERE u.id = :userId";

	    javax.persistence.Query query = entityManager.createNativeQuery(sql);
	    query.setParameter("userId", userId);

	    try {
	        Object[] user = (Object[]) query.getSingleResult();

	        userDTO userDTO = new userDTO();
	        userDTO.setUsername((String) user[0]);
	        userDTO.setEmail((String) user[1]);
	        userDTO.setMobile((String) user[2]);
	        userDTO.setAddress((String) user[3]);
	        userDTO.setImage((String) user[4]);
	        userDTO.setFriendCount(user[5] != null ? ((Number) user[5]).intValue() : 0);

	        response.put("msg", "User details fetched successfully.");
	        response.put("data", userDTO);
	        response.put("status", 200);

	    } catch (NoResultException e) {
	        response.put("msg", "User not found.");
	        response.put("data", null);
	        response.put("status", 404);
	    } catch (Exception e) {
	        response.put("msg", "Error fetching user.");
	        response.put("data", null);
	        response.put("error", e.getMessage());
	        response.put("status", 500);
	    }

	    return response;
	}

	
}
