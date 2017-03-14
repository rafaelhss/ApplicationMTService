package com.elcartero.service;

import com.elcartero.domain.MessageMT;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MessageMT.
 */
public interface MessageMTService {

    /**
     * Save a messageMT.
     *
     * @param messageMT the entity to save
     * @return the persisted entity
     */
    MessageMT save(MessageMT messageMT);

    /**
     *  Get all the messageMTS.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MessageMT> findAll(Pageable pageable);

    /**
     *  Get the "id" messageMT.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MessageMT findOne(Long id);

    /**
     *  Delete the "id" messageMT.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
