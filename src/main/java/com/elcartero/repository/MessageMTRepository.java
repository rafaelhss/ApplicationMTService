package com.elcartero.repository;

import com.elcartero.domain.MessageMT;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MessageMT entity.
 */
@SuppressWarnings("unused")
public interface MessageMTRepository extends JpaRepository<MessageMT,Long> {

}
