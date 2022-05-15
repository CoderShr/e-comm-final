package com.cart.ShoppingService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cart.ShoppingService.Model.Cart;


@Repository
public interface ShoppingCartRepository extends JpaRepository<Cart, Integer> {

    boolean existsByProductProductIdAndUserUserId(Integer productId, Integer userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cart c SET c.quantity = :quantity WHERE c.product_id = :productId " +
            "AND c.user_id = :userId", nativeQuery = true)
    void setQuantityByProductProductIdAndUserUserId(@Param("quantity") int quantity,
                                                    @Param("productId") int productId,
                                                    @Param("userId") int userId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart  WHERE user_id = :userId AND product_id = :productId", nativeQuery = true)
    void deleteCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    @Transactional @Modifying
    @Query(value = "DELETE FROM cart  WHERE user_id = :userId", nativeQuery = true )
    void deleteCartByUserId(@Param("userId") Integer userId);

    List<Cart> findAllByUserUserId(int userId);


}

