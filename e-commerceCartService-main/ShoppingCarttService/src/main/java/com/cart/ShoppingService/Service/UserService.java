package com.cart.ShoppingService.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.ShoppingService.Exception.DataNotFoundException;
import com.cart.ShoppingService.Model.User;
import com.cart.ShoppingService.Repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
	private static Logger log = LogManager.getLogger(ShoppingCartServiceImpl.class);
	   
	@Autowired
    private final UserRepository userRepository;;


    public User getUserById(Integer userId) throws DataNotFoundException {
        log.info("Searching user with id: "+userId);
       return userRepository.findById(userId)
               .orElseThrow(() -> {
                   log.error("User with id "+userId+" not found.");
                  return new DataNotFoundException("Requested user does not exist.");
               });

    }
}
