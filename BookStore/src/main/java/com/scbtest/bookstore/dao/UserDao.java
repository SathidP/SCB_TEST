package com.scbtest.bookstore.dao;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.scbtest.bookstore.model.User;

public interface UserDao extends CrudRepository<User, Integer> {
	
	@Modifying
	@Query(value = "insert into USERS (name,surname,username,password,date_of_birth,password_key) "
					+ "VALUES (:name,:surname,:username,AES_ENCRYPT(:password,:key), str_to_date(:dateOfBirth, '%d/%m/%Y') ,:key)", nativeQuery = true)
    @Transactional
	public int insertUser(@Param("name") String name, @Param("surname") String surname, @Param("username") String username, @Param("password") String password, @Param("key") String key, @Param("dateOfBirth") String dateOfBirth);
	
	@Modifying
	@Query(value = "insert into USERS (name,surname,username,password,password_key) "
					+ "VALUES (:name,:surname,:username,AES_ENCRYPT(:password,:key),:key)", nativeQuery = true)
    @Transactional
	public int insertUser(@Param("name") String name, @Param("surname") String surname, @Param("username") String username, @Param("password") String password, @Param("key") String key);
		
	@Modifying
	@Query(value = "delete from USERS where user_id = :user_id", nativeQuery = true)
    @Transactional
	public int deleteUserById(@Param("user_id") int user_id);
	
	
	@Query(value = "select user_id, name, surname, username,  date_of_birth "
			+ "from USERS where username = :username and  AES_DECRYPT(password, password_key) = :password", nativeQuery = true)
	public List<User> queryUserByUsernamePassword(@Param("username") String username,  @Param("password") String password); 
	
	@Query(value = "select count(user_id)"
			+ "from USERS where username = :username", nativeQuery = true)
	public List<Integer> countUserByUsername(@Param("username") String username); 
	
	
	default int addUser(User user, String password) {
		String key = randomKey();
		if(user.getDate_of_birth() != null ) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return insertUser(user.getName(), user.getSurname(), user.getUsername(), password, key, dateFormat.format(user.getDate_of_birth()));
		} else {
			return  insertUser(user.getName(), user.getSurname(), user.getUsername(), password, key);
		}
	}
	
	
	default String randomKey() {
		Random rand = new Random();
		byte[] chars = new byte[16];
		for(int i=0;i<chars.length;i++) {
		    chars[i] = (byte) rand.nextInt(255);
		    if (!Character.isValidCodePoint(chars[i]))
		        i--;
		}
		return new String(chars, StandardCharsets.US_ASCII);
	}
	
	default User getUserByUsername(String username, String password) {
		List<User> userResult = queryUserByUsernamePassword(username, password);
		if(userResult.size() > 0) {
			return userResult.get(0);
		}else {
			return null;
		}
	}
}
