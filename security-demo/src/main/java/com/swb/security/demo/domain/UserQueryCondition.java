/**
 * 
 */
package com.swb.security.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author swb
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserQueryCondition {
	
	private String username;
	
	private int age;
	private int ageTo;
	
	private String xxx;


	
}
